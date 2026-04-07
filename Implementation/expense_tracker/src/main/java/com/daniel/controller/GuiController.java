package com.daniel.controller;

import com.daniel.service.ExpenseService;

public class GuiController {
     public ExpenseService service;

     public GuiController(ExpenseService service){
        this.service=service;
     }

     //add expense
     public String addExpense(double amount ,String type,String quantity,String description){
        //service.addExpense(amount,type,quantity,description);
        //return "Expense with saved";
        return  service.addExpense(amount,type,quantity,description);
    }
    //exit
    public void exit(){
        System.exit(0);
    }

    //create objects
    public static GuiController createController(){
        return new GuiController(creatService());
    }

    public static ExpenseService creatService(){
        //return ExpenseController.createService();
        return new ExpenseService();
    }
}
