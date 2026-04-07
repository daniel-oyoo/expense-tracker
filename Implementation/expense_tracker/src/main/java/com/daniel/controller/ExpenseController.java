package com.daniel.controller;
import java.util.*;

import com.daniel.Repository.impl.ExpenseRepository;
import com.daniel.service.ExpenseService;


public class ExpenseController{
    public ExpenseService service;
    //contact with our interface main and all other classes
    public ExpenseController(ExpenseService service){
        this.service=service;
    }

    //display welcome message
public void run(){
        //displayWelcomeMessage
        System.out.println("\nWelcome to our savings and expense tracker\n");
        //set it up
            //for input
    Scanner scanner = new Scanner(System.in);
//loop until user quits
while(true){
        //System.out.println("\n What would you Like to do(1-5) ? ");
        System.out.println("\n1. ADD EXPENSE\n2. VIEW EXPENSE\n3. UPDATE EXPENSE\n4. DELETE EXPENSE\n5. EXIT APPLICATION\n");
        System.out.print("What would you Like to do(1-5) : ");
        int choices=scanner.nextInt();
        //consume new line
        scanner.nextLine();
        switch(choices){
            case 1:
                getInput(choices,scanner);
            break;
            case 2:
                getInput(choices,scanner);
            break;
            case 3:
               getInput(choices,scanner);
            break;
            case 4:
                getInput(choices,scanner);
            break;
            case 5 :
                System.out.println("Goodbye.....");
                System.exit(0);
            break;
            default:
                System.out.println("Invalid choice \nEnter number between 1 - 5");
                //choices=scanner.nextInt();
                //consume new line
                //scanner.nextLine();
            break;
        }
    
     //scanner.close();
    }//end while

    //scanner.close();
}

    //prompt user for input
    public void getInput(int choice,Scanner scanner){
        if(choice==1){
            System.out.print("\nEnter amount :");
            double amount=scanner.nextDouble();
            //clear new line
            scanner.nextLine();
            System.out.print("Enter type :");
            String type=scanner.nextLine();
            System.out.print("Enter quantity :");
            String quantity=scanner.nextLine();
            System.out.println("Enter description below ");
            String desc=scanner.nextLine();

            String result =service.addExpense(amount,type,quantity,desc);
            System.out.println(result);

        }
        if(choice==2){
            System.out.println("\n"+service.viewExpense());//bugg \n
        }
        if(choice==3){
            System.out.print("\nEnter id :");
            int id=scanner.nextInt();
            System.out.print("Enter amount :");
            double amount=scanner.nextDouble();
            //clear new line
            scanner.nextLine();
            System.out.print("Enter type :");
            String type=scanner.nextLine();
            System.out.print("Enter quantity :");
            String quantity=scanner.nextLine();
            System.out.println("Enter description below ");
            String desc=scanner.nextLine();

            String result=service.updateExpense(id,amount,type,quantity,desc);
            System.out.println("\n"+result);
        }
        if(choice==4){
            System.out.print("\nEnter id :");
            int id=scanner.nextInt();

            String result =service.deleteExpense(id);
            System.out.println(result);
        }
        

    }
    //try and distribute risk;
    public static ExpenseController createExpenseController(){
        return new ExpenseController(createService());
    }
    //methods to set up objects
    public static ExpenseService createService(){
        return new ExpenseService(createRepository());
    }

    public static ExpenseRepository createRepository(){
        return new ExpenseRepository();
    }
}