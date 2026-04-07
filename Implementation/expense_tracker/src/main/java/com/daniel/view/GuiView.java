package com.daniel.view;

import javax.swing.*;
import com.daniel.controller.GuiController;
import com.daniel.model.Expense;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// modern expense tracker GUI with full CRUD operations
public class GuiView extends JFrame implements ActionListener {
    
    // controller for business logic
    private GuiController guiController;
    
    // main panels
    private JPanel inputPanel;
    private JPanel displayPanel;
    private JPanel buttonPanel;
    private JTabbedPane tabbedPane;
    
    // input fields
    private JTextField idField;
    private JTextField amountField;
    private JTextField typeField;
    private JTextField quantityField;
    private JTextArea descriptionArea;
    
    // buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewAllButton;
    private JButton clearButton;
    private JButton exitButton;
    
    // display area
    private JTextArea displayArea;
    private JScrollPane scrollPane;
    
    // totals labels
    private JLabel todayTotalLabel;
    private JLabel weekTotalLabel;
    private JLabel monthTotalLabel;
    private JLabel yearTotalLabel;
    private JLabel allTimeTotalLabel;
    
    // constructor
    public GuiView(GuiController guiController) {
        this.guiController = guiController;
        initializeUI();
        refreshTotals();
    }
    
    // setup the entire user interface
    private void initializeUI() {
        setTitle("Expense Tracker - Modern Dashboard");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // create tabbed pane for different views
        tabbedPane = new JTabbedPane();
        
        // add all tabs
        tabbedPane.addTab("Add / Update / Delete", createInputPanel());
        tabbedPane.addTab("View All Expenses", createViewPanel());
        tabbedPane.addTab("Totals Dashboard", createTotalsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    // panel for adding, updating, deleting expenses
    private JPanel createInputPanel() {
        inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        
        formPanel.add(new JLabel("Expense ID (for update/delete):"));
        idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);
        
        formPanel.add(new JLabel("Category Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);
        
        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);
        
        formPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        formPanel.add(descScroll);
        
        // button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        addButton = new JButton("Add Expense");
        updateButton = new JButton("Update Expense");
        deleteButton = new JButton("Delete Expense");
        clearButton = new JButton("Clear Fields");
        exitButton = new JButton("Exit");
        
        // style buttons
        styleButton(addButton, new Color(76, 175, 80));
        styleButton(updateButton, new Color(33, 150, 243));
        styleButton(deleteButton, new Color(244, 67, 54));
        styleButton(clearButton, new Color(255, 152, 0));
        styleButton(exitButton, new Color(158, 158, 158));
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        
        // add action listeners
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        return inputPanel;
    }
    
    // panel for viewing all expenses
    private JPanel createViewPanel() {
        displayPanel = new JPanel(new BorderLayout(10, 10));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane = new JScrollPane(displayArea);
        
        viewAllButton = new JButton("Refresh Expenses List");
        styleButton(viewAllButton, new Color(33, 150, 243));
        viewAllButton.addActionListener(this);
        
        displayPanel.add(viewAllButton, BorderLayout.NORTH);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        
        return displayPanel;
    }
    
    // panel for displaying totals
    private JPanel createTotalsPanel() {
        JPanel totalsPanel = new JPanel(new GridBagLayout());
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // create styled labels for totals
        Font totalFont = new Font("Arial", Font.BOLD, 18);
        
        todayTotalLabel = createTotalLabel("Today's Total: ksh0.00", totalFont, new Color(76, 175, 80));
        weekTotalLabel = createTotalLabel("This Week: ksh0.00", totalFont, new Color(33, 150, 243));
        monthTotalLabel = createTotalLabel("This Month: ksh0.00", totalFont, new Color(255, 152, 0));
        yearTotalLabel = createTotalLabel("This Year: ksh0.00", totalFont, new Color(156, 39, 176));
        allTimeTotalLabel = createTotalLabel("All Time Total: ksh0.00", totalFont, new Color(244, 67, 54));
        
        JButton refreshTotalsButton = new JButton("Refresh Totals");
        styleButton(refreshTotalsButton, new Color(33, 150, 243));
        refreshTotalsButton.addActionListener(e -> refreshTotals());
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        totalsPanel.add(todayTotalLabel, gbc);
        gbc.gridy = 1;
        totalsPanel.add(weekTotalLabel, gbc);
        gbc.gridy = 2;
        totalsPanel.add(monthTotalLabel, gbc);
        gbc.gridy = 3;
        totalsPanel.add(yearTotalLabel, gbc);
        gbc.gridy = 4;
        totalsPanel.add(allTimeTotalLabel, gbc);
        gbc.gridy = 5;
        totalsPanel.add(refreshTotalsButton, gbc);
        
        return totalsPanel;
    }
    
    // helper to create styled total labels
    private JLabel createTotalLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    // style buttons with consistent look
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
    
    // refresh all totals from database
    private void refreshTotals() {
        try {
            todayTotalLabel.setText(String.format("Today's Total: ksh%.2f", guiController.getTodayTotal()));
            weekTotalLabel.setText(String.format("This Week: ksh%.2f", guiController.getWeeklyTotal()));
            monthTotalLabel.setText(String.format("This Month: ksh%.2f", guiController.getMonthlyTotal()));
            yearTotalLabel.setText(String.format("This Year: ksh%.2f", guiController.getYearlyTotal()));
            allTimeTotalLabel.setText(String.format("All Time Total: ksh%.2f", guiController.getAllTimeTotal()));
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Error refreshing totals: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // refresh the view all expenses display
    private void refreshDisplay() {
        try {
            List<Expense> expenses = guiController.viewExpenses();
            if(expenses == null || expenses.isEmpty()) {
                displayArea.setText("No expenses found. Add some expenses first.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-5s %-12s %-20s %-15s %-30s %-12s\n", "ID", "Amount", "Category", "Quantity", "Description", "Date"));
                sb.append("----------------------------------------------------------------------------------------------------------\n");
                for(Expense expense : expenses) {
                    sb.append(String.format("%-5d ksh%-11.2f %-20s %-15s %-30s %-12s\n",
                        expense.getId(),
                        expense.getAmount(),
                        truncate(expense.getCategory().getType(), 20),
                        truncate(expense.getCategory().getQuantity(), 15),
                        truncate(expense.getCategory().getDescription(), 30),
                        expense.getDate()));
                }
                sb.append("----------------------------------------------------------------------------------------------------------\n");
                double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
                sb.append(String.format("TOTAL: ksh%.2f\n", total));
                displayArea.setText(sb.toString());
            }
        } catch(Exception ex) {
            displayArea.setText("Error loading expenses: " + ex.getMessage());
        }
    }
    
    // helper to truncate long strings
    private String truncate(String str, int maxLength) {
        if(str == null) return "";
        if(str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    // clear all input fields
    private void clearFields() {
        idField.setText("");
        amountField.setText("");
        typeField.setText("");
        quantityField.setText("");
        descriptionArea.setText("");
    }
    
    // validate that a field is not empty
    private boolean validateField(String value, String fieldName) {
        if(value == null || value.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, fieldName + " cannot be empty", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    // validate amount is positive number
    private boolean validateAmount(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            if(amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    // validate ID for update/delete
    private boolean validateId(String idStr, String operation) {
        try {
            Integer.parseInt(idStr);
            return true;
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valid ID is required to " + operation, "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    // handle all button actions
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton) {
            System.exit(0);
        }
        
        if(e.getSource() == clearButton) {
            clearFields();
        }
        
        if(e.getSource() == viewAllButton) {
            refreshDisplay();
        }
        
        if(e.getSource() == addButton) {
            String amountStr = amountField.getText();
            String type = typeField.getText();
            String quantity = quantityField.getText();
            String description = descriptionArea.getText();
            
            if(!validateAmount(amountStr)) return;
            if(!validateField(type, "Category Type")) return;
            if(!validateField(quantity, "Quantity")) return;
            if(!validateField(description, "Description")) return;
            
            double amount = Double.parseDouble(amountStr);
            String result = guiController.addExpense(amount, type, quantity, description);
            JOptionPane.showMessageDialog(this, result, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            refreshTotals();
            refreshDisplay();
        }
        
        if(e.getSource() == updateButton) {
            String idStr = idField.getText();
            String amountStr = amountField.getText();
            String type = typeField.getText();
            String quantity = quantityField.getText();
            String description = descriptionArea.getText();
            
            if(!validateId(idStr, "update")) return;
            if(!validateAmount(amountStr)) return;
            if(!validateField(type, "Category Type")) return;
            if(!validateField(quantity, "Quantity")) return;
            if(!validateField(description, "Description")) return;
            
            int id = Integer.parseInt(idStr);
            double amount = Double.parseDouble(amountStr);
            String result = guiController.updateExpense(id, amount, type, quantity, description);
            JOptionPane.showMessageDialog(this, result, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            refreshTotals();
            refreshDisplay();
        }
        
        if(e.getSource() == deleteButton) {
            String idStr = idField.getText();
            
            if(!validateId(idStr, "delete")) return;
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete expense " + idStr + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(idStr);
                String result = guiController.deleteExpense(id);
                JOptionPane.showMessageDialog(this, result, "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                refreshTotals();
                refreshDisplay();
            }
        }
    }
}