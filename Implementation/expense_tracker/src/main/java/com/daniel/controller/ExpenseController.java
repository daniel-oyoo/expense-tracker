package com.daniel.controller;

import java.util.*;
import com.daniel.Repository.impl.ExpenseRepository;
import com.daniel.model.Expense;
import com.daniel.service.ExpenseService;

// handles user input and menu navigation
public class ExpenseController{
    public ExpenseService service;
    
    // constructor with service injection
    public ExpenseController(ExpenseService service){
        this.service = service;
    }

    public ExpenseController() {
        // empty constructor for flexibility
    }

    // display welcome message and run main loop
    public void run(){
        System.out.println("\nWelcome to our savings and expense tracker\n");
        Scanner scanner = new Scanner(System.in);

            //int choices = scanner.nextInt();
            //scanner.nextLine();
        
        while(true){
            // display menu options
            System.out.println("\n1. ADD EXPENSE");
            System.out.println("2. VIEW EXPENSE");
            System.out.println("3. UPDATE EXPENSE");
            System.out.println("4. DELETE EXPENSE");
            System.out.println("5. VIEW TOTALS");
            System.out.println("6. EXIT APPLICATION\n");
            System.out.print("What would you like to do (1-6) : ");
            
            int choices = scanner.nextInt();
            scanner.nextLine();  // consume new line
            
            switch(choices){
                case 1:
                    getInput(choices, scanner);
                    break;
                case 2:
                    getInput(choices, scanner);
                    break;
                case 3:
                    getInput(choices, scanner);
                    break;
                case 4:
                    getInput(choices, scanner);
                    break;
                case 5:
                    displayTotals();
                    break;
                case 6:
                    System.out.println("Goodbye.....");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Enter number between 1 and 6");
                    break;
            }
        }
    }

    // prompt user for input based on choice
    public void getInput(int choice, Scanner scanner){
        if(choice == 1){
            // add expense
            System.out.print("\nEnter amount : ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // clear new line
            System.out.print("Enter type : ");
            String type = scanner.nextLine();
            System.out.print("Enter quantity : ");
            String quantity = scanner.nextLine();
            System.out.println("Enter description below : ");
            String desc = scanner.nextLine();

            String result = service.addExpense(amount, type, quantity, desc);
            System.out.println(result);
        }
        
        if(choice == 2){
            // view expenses
            List<Expense> expenses = service.viewExpense();
            if(expenses.isEmpty()){
                System.out.println("\nNo expenses found.");
            } else {
                System.out.println("\n" + expenses);
            }
        }
        
        if(choice == 3){
            // update expense
            System.out.print("\nEnter id : ");
            int id = scanner.nextInt();
            System.out.print("Enter amount : ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // clear new line
            System.out.print("Enter type : ");
            String type = scanner.nextLine();
            System.out.print("Enter quantity : ");
            String quantity = scanner.nextLine();
            System.out.println("Enter description below : ");
            String desc = scanner.nextLine();

            String result = service.updateExpense(id, amount, type, quantity, desc);
            System.out.println("\n" + result);
        }
        
        if(choice == 4){
            // delete expense
            System.out.print("\nEnter id : ");
            int id = scanner.nextInt();
            String result = service.deleteExpense(id);
            System.out.println(result);
        }
    }
    
    // display all totals
    public void displayTotals(){
        System.out.println("\n--- EXPENSE TOTALS ---");
        System.out.println("Today: ksh" + service.getTodayTotal());
        System.out.println("This Week: ksh" + service.getWeeklyTotal());
        System.out.println("This Month: ksh" + service.getMonthlyTotal());
        System.out.println("This Year: ksh" + service.getYearlyTotal());
        System.out.println("All Time: ksh" + service.getAllTimeTotal());
        System.out.println("----------------------\n");
    }
     
    // factory method to create controller with all dependencies
    public static ExpenseController createExpenseController(){
        return new ExpenseController(createService());
    }
    
    // create service with repository
    public static ExpenseService createService(){
        return new ExpenseService(createRepository());
    }

    // create repository
    public static ExpenseRepository createRepository(){
        return new ExpenseRepository();
    }
}