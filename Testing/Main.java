// File: Main.java
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        tracker.start();
    }
}

class ExpenseTracker {
    private static final String DATA_FILE = "expenses.csv";
    private List<Expense> expenses;
    private Scanner scanner;
    private int nextId;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadFromFile();
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = getIntInput("What would you like to do (1-7): ", 1, 7);
            
            switch (choice) {
                case 1: addExpense(); break;
                case 2: viewExpenses(); break;
                case 3: updateExpense(); break;
                case 4: deleteExpense(); break;
                case 5: showTotals(); break;
                case 6: filterByDate(); break;
                case 7: 
                    saveToFile();
                    System.out.println("Goodbye! Data saved.");
                    return;
            }
        }
    }

    private void printMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         EXPENSE TRACKER - " + LocalDate.now());
        System.out.println("=".repeat(50));
        System.out.println("1. ADD EXPENSE");
        System.out.println("2. VIEW ALL EXPENSES");
        System.out.println("3. UPDATE EXPENSE");
        System.out.println("4. DELETE EXPENSE");
        System.out.println("5. VIEW TOTALS (Daily/Weekly/Monthly/Yearly)");
        System.out.println("6. FILTER EXPENSES BY DATE");
        System.out.println("7. EXIT");
        System.out.println("-".repeat(50));
    }

    private void addExpense() {
        System.out.println("\n--- ADD NEW EXPENSE ---");
        
        double amount = getDoubleInput("Enter amount: ", 0.01, 1_000_000);
        
        System.out.print("Enter category (food/transport/entertainment/bills/other): ");
        String category = scanner.nextLine().trim().toLowerCase();
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) description = "No description";
        
        LocalDate date = getDateInput("Enter date (YYYY-MM-DD) or press Enter for today: ");
        
        Expense expense = new Expense(nextId++, amount, category, description, date);
        expenses.add(expense);
        
        System.out.println(" Expense added successfully! (ID: " + expense.getId() + ")");
        saveToFile();
    }

    private void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("\n No expenses found. Add some first!");
            return;
        }
        
        System.out.println("\n--- ALL EXPENSES ---");
        System.out.println("-".repeat(80));
        System.out.printf("%-5s %-12s %-15s %-25s %-12s\n", "ID", "Amount", "Category", "Description", "Date");
        System.out.println("-".repeat(80));
        
        for (Expense e : expenses) {
            System.out.printf("%-5d $%-11.2f %-15s %-25s %-12s\n", 
                e.getId(), e.getAmount(), e.getCategory(), 
                truncate(e.getDescription(), 23), e.getDate());
        }
        System.out.println("-".repeat(80));
        
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("💰 TOTAL: $%.2f\n", total);
    }

    private void updateExpense() {
        if (expenses.isEmpty()) {
            System.out.println("\n📭 No expenses to update.");
            return;
        }
        
        viewExpenses();
        int id = getIntInput("\nEnter expense ID to update: ", 0, nextId - 1);
        
        Expense expense = findById(id);
        if (expense == null) {
            System.out.println("❌ Expense with ID " + id + " not found!");
            return;
        }
        
        System.out.println("\n--- UPDATING EXPENSE ID: " + id + " ---");
        System.out.println("(Press Enter to keep current value)");
        
        double newAmount = getDoubleInputOptional("Amount [" + expense.getAmount() + "]: ", expense.getAmount());
        expense.setAmount(newAmount);
        
        System.out.print("Category [" + expense.getCategory() + "]: ");
        String newCategory = scanner.nextLine().trim();
        if (!newCategory.isEmpty()) expense.setCategory(newCategory);
        
        System.out.print("Description [" + expense.getDescription() + "]: ");
        String newDesc = scanner.nextLine().trim();
        if (!newDesc.isEmpty()) expense.setDescription(newDesc);
        
        LocalDate newDate = getDateInputOptional("Date [" + expense.getDate() + "] (YYYY-MM-DD): ", expense.getDate());
        expense.setDate(newDate);
        
        System.out.println("✅ Expense updated successfully!");
        saveToFile();
    }

    private void deleteExpense() {
        if (expenses.isEmpty()) {
            System.out.println("\n📭 No expenses to delete.");
            return;
        }
        
        viewExpenses();
        int id = getIntInput("\nEnter expense ID to delete: ", 0, nextId - 1);
        
        Expense expense = findById(id);
        if (expense == null) {
            System.out.println("❌ Expense with ID " + id + " not found!");
            return;
        }
        
        System.out.println("\n⚠️  Delete this expense?");
        System.out.println("   ID: " + expense.getId() + " | Amount: $" + expense.getAmount() + 
                          " | Date: " + expense.getDate());
        System.out.print("   Type 'YES' to confirm: ");
        String confirm = scanner.nextLine().trim();
        
        if (confirm.equalsIgnoreCase("YES")) {
            expenses.remove(expense);
            System.out.println("✅ Expense deleted successfully!");
            saveToFile();
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }

    private void showTotals() {
        if (expenses.isEmpty()) {
            System.out.println("\n📭 No expenses to calculate totals.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              EXPENSE TOTALS");
        System.out.println("=".repeat(60));
        
        // Today's total
        LocalDate today = LocalDate.now();
        double todayTotal = expenses.stream()
            .filter(e -> e.getDate().equals(today))
            .mapToDouble(Expense::getAmount)
            .sum();
        System.out.printf("\n📅 TODAY (%s): $%.2f\n", today, todayTotal);
        
        // Weekly total (current week)
        double weekTotal = expenses.stream()
            .filter(e -> isSameWeek(e.getDate(), today))
            .mapToDouble(Expense::getAmount)
            .sum();
        System.out.printf("📆 THIS WEEK: $%.2f\n", weekTotal);
        
        // Monthly total
        double monthTotal = expenses.stream()
            .filter(e -> e.getDate().getYear() == today.getYear() && 
                        e.getDate().getMonth() == today.getMonth())
            .mapToDouble(Expense::getAmount)
            .sum();
        System.out.printf("📊 THIS MONTH: $%.2f\n", monthTotal);
        
        // Yearly total
        double yearTotal = expenses.stream()
            .filter(e -> e.getDate().getYear() == today.getYear())
            .mapToDouble(Expense::getAmount)
            .sum();
        System.out.printf("🎯 THIS YEAR: $%.2f\n", yearTotal);
        
        // All time total
        double allTimeTotal = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("🏆 ALL TIME: $%.2f\n", allTimeTotal);
        
        // Category breakdown for current month
        System.out.println("\n--- CATEGORY BREAKDOWN (This Month) ---");
        Map<String, Double> categoryTotals = expenses.stream()
            .filter(e -> e.getDate().getYear() == today.getYear() && 
                        e.getDate().getMonth() == today.getMonth())
            .collect(Collectors.groupingBy(Expense::getCategory, 
                     Collectors.summingDouble(Expense::getAmount)));
        
        if (categoryTotals.isEmpty()) {
            System.out.println("  No expenses this month");
        } else {
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                System.out.printf("  %-15s: $%.2f\n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println("=".repeat(60));
    }

    private void filterByDate() {
        if (expenses.isEmpty()) {
            System.out.println("\n📭 No expenses to filter.");
            return;
        }
        
        System.out.println("\n--- FILTER BY DATE ---");
        System.out.println("1. Today");
        System.out.println("2. This Week");
        System.out.println("3. This Month");
        System.out.println("4. This Year");
        System.out.println("5. Custom Date Range");
        
        int choice = getIntInput("Choose filter: ", 1, 5);
        LocalDate today = LocalDate.now();
        List<Expense> filtered = new ArrayList<>();
        
        switch (choice) {
            case 1:
                filtered = expenses.stream()
                    .filter(e -> e.getDate().equals(today))
                    .collect(Collectors.toList());
                System.out.println("\n--- EXPENSES FOR " + today + " ---");
                break;
            case 2:
                filtered = expenses.stream()
                    .filter(e -> isSameWeek(e.getDate(), today))
                    .collect(Collectors.toList());
                System.out.println("\n--- EXPENSES FOR WEEK OF " + getStartOfWeek(today) + " ---");
                break;
            case 3:
                filtered = expenses.stream()
                    .filter(e -> e.getDate().getYear() == today.getYear() && 
                                e.getDate().getMonth() == today.getMonth())
                    .collect(Collectors.toList());
                System.out.println("\n--- EXPENSES FOR " + today.getMonth() + " " + today.getYear() + " ---");
                break;
            case 4:
                filtered = expenses.stream()
                    .filter(e -> e.getDate().getYear() == today.getYear())
                    .collect(Collectors.toList());
                System.out.println("\n--- EXPENSES FOR " + today.getYear() + " ---");
                break;
            case 5:
                LocalDate start = getDateInput("Enter start date (YYYY-MM-DD): ");
                LocalDate end = getDateInput("Enter end date (YYYY-MM-DD): ");
                filtered = expenses.stream()
                    .filter(e -> !e.getDate().isBefore(start) && !e.getDate().isAfter(end))
                    .collect(Collectors.toList());
                System.out.println("\n--- EXPENSES FROM " + start + " TO " + end + " ---");
                break;
        }
        
        if (filtered.isEmpty()) {
            System.out.println("📭 No expenses found for this period.");
            return;
        }
        
        System.out.println("-".repeat(70));
        System.out.printf("%-5s %-12s %-15s %-25s %-12s\n", "ID", "Amount", "Category", "Description", "Date");
        System.out.println("-".repeat(70));
        
        for (Expense e : filtered) {
            System.out.printf("%-5d $%-11.2f %-15s %-25s %-12s\n", 
                e.getId(), e.getAmount(), e.getCategory(), 
                truncate(e.getDescription(), 23), e.getDate());
        }
        System.out.println("-".repeat(70));
        
        double total = filtered.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("💰 PERIOD TOTAL: $%.2f\n", total);
    }

    // ========== FILE OPERATIONS ==========
    
    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            nextId = 1;
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            expenses = (List<Expense>) ois.readObject();
            nextId = expenses.stream().mapToInt(Expense::getId).max().orElse(0) + 1;
            System.out.println("✅ Loaded " + expenses.size() + " expenses from file.");
        } catch (FileNotFoundException e) {
            nextId = 1;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Warning: Could not load file. Starting fresh.");
            System.out.println("   Error: " + e.getMessage());
            expenses = new ArrayList<>();
            nextId = 1;
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // ========== HELPER METHODS ==========
    
    private Expense findById(int id) {
        return expenses.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
    
    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
                System.out.println("❌ Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
                System.out.println("❌ Amount must be between $" + min + " and $" + max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid amount.");
            }
        }
    }
    
    private double getDoubleInputOptional(String prompt, double defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Keeping original value: $" + defaultValue);
            return defaultValue;
        }
    }
    
    private LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return LocalDate.now();
            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (Exception e) {
                System.out.println("❌ Invalid date. Use format: YYYY-MM-DD");
            }
        }
    }
    
    private LocalDate getDateInputOptional(String prompt, LocalDate defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defaultValue;
        try {
            return LocalDate.parse(input, DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Invalid date. Keeping original: " + defaultValue);
            return defaultValue;
        }
    }
    
    private boolean isSameWeek(LocalDate date1, LocalDate date2) {
        LocalDate start1 = date1.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate start2 = date2.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        return start1.equals(start2);
    }
    
    private LocalDate getStartOfWeek(LocalDate date) {
        return date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
    }
    
    private String truncate(String s, int maxLength) {
        if (s == null) return "";
        if (s.length() <= maxLength) return s;
        return s.substring(0, maxLength - 3) + "...";
    }
}

// File: Expense.java
class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private double amount;
    private String category;
    private String description;
    private LocalDate date;
    
    public Expense(int id, double amount, String category, String description, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }
    
    // Getters
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    
    // Setters
    public void setAmount(double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDate date) { this.date = date; }
}