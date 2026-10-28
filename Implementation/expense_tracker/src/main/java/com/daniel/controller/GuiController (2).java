package com.daniel.controller;

import java.util.*;

import com.daniel.Repository.impl.ExpenseRepository;
import com.daniel.model.Expense;
import com.daniel.service.ExpenseService;

// handles GUI interactions separate from console controller
public class GuiController {
    public ExpenseService service;

    // constructor with service injection
    public GuiController(ExpenseService service){
        this.service = service;
    }

    // add expense from GUI
    public String addExpense(double amount, String type, String quantity, String description){
        return service.addExpense(amount, type, quantity, description);
    }
    
    // view all expenses
    public List<Expense> viewExpenses(){
        return service.viewExpense();
    }
    
    // update expense
    public String updateExpense(int id, double amount, String type, String quantity, String description){
        return service.updateExpense(id, amount, type, quantity, description);
    }
    
    // delete expense
    public String deleteExpense(int id){
        return service.deleteExpense(id);
    }
    
    // get today total
    public double getTodayTotal(){
        return service.getTodayTotal();
    }
    
    // get weekly total
    public double getWeeklyTotal(){
        return service.getWeeklyTotal();
    }
    
    // get monthly total
    public double getMonthlyTotal(){
        return service.getMonthlyTotal();
    }
    
    // get yearly total
    public double getYearlyTotal(){
        return service.getYearlyTotal();
    }
    
    // get all time total
    public double getAllTimeTotal(){
        return service.getAllTimeTotal();
    }
    
    // exit application
    public void exit(){
        System.exit(0);
    }

    // factory method to create controller
    public static GuiController createController(){
        return new GuiController(createService());
    }

    // create service
    public static ExpenseService createService(){
        return new ExpenseService(new ExpenseRepository());
    }
}