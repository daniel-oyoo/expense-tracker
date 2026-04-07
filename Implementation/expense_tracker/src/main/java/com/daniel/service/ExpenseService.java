package com.daniel.service;

import java.util.*;
import java.time.LocalDate;
import com.daniel.model.Category;
import com.daniel.model.Expense;
import com.daniel.Repository.RepositoryManager;
import com.daniel.Repository.impl.ExpenseRepository;
import com.daniel.Repository.impl.FileRepository;

// service layer that handles business logic and coordinates between components
public class ExpenseService{

  public ExpenseRepository repository;
  public FileRepository repo = new FileRepository();
  
  // constructor with repository dependency injection
  public ExpenseService(ExpenseRepository repository){
    this.repository = repository;
  }
  
  // empty constructor for flexibility
  public ExpenseService() {
    // used when repository is created elsewhere
  }
  
  // create and save a new expense
  public String addExpense(double amount, String type, String quantity, String description){
    // create expense with current date automatically
    Expense expense = new Expense(amount, new Category(type, quantity, description));
    // RepositoryManager handles ID generation and saving
    return new RepositoryManager().asignId(expense);
  }

  // return all expenses for viewing
  public List<Expense> viewExpense(){
    return repository.findAll();
  }

  // update existing expense by id
  public String updateExpense(int id, double amount, String type, String quantity, String description){
    // find existing expense
    Expense expense = repository.findById(id);
    
    // validate expense exists
    if(expense == null){
      throw new IllegalArgumentException("Expense with id " + id + " does not exist");
    }
    
    // create updated category
    Category category = new Category(type, quantity, description);
    
    // update expense fields
    expense.setAmount(amount);
    expense.setCategory(category);
    // date remains unchanged - keeps original creation date

    // save updates to repository
    return repository.update(expense);
  }
  
  // delete expense by id
  public String deleteExpense(int id){
    // find expense to verify it exists
    Expense expense = repository.findById(id);
    
    // validate expense exists
    if(expense == null){
      throw new IllegalArgumentException("Expense with id " + id + " does not exist");
    }
    
    // delete from repository
    return repository.delete(id);
  }
  
  // calculate total for today
  public double getTodayTotal(){
    LocalDate today = LocalDate.now();
    double total = 0;
    List<Expense> allExpenses = repository.findAll();
    for(Expense expense : allExpenses){
      if(expense.getDate().equals(today)){
        total = total + expense.getAmount();
      }
    }
    return total;
  }
  
  // calculate total for current week
  public double getWeeklyTotal(){
    LocalDate today = LocalDate.now();
    LocalDate weekStart = today.minusDays(today.getDayOfWeek().getValue() - 1);
    double total = 0;
    List<Expense> allExpenses = repository.findAll();
    for(Expense expense : allExpenses){
      LocalDate expenseDate = expense.getDate();
      if(!expenseDate.isBefore(weekStart) && !expenseDate.isAfter(today)){
        total = total + expense.getAmount();
      }
    }
    return total;
  }
  
  // calculate total for current month
  public double getMonthlyTotal(){
    LocalDate today = LocalDate.now();
    double total = 0;
    List<Expense> allExpenses = repository.findAll();
    for(Expense expense : allExpenses){
      LocalDate expenseDate = expense.getDate();
      if(expenseDate.getYear() == today.getYear() && expenseDate.getMonth() == today.getMonth()){
        total = total + expense.getAmount();
      }
    }
    return total;
  }
  
  // calculate total for current year
  public double getYearlyTotal(){
    LocalDate today = LocalDate.now();
    double total = 0;
    List<Expense> allExpenses = repository.findAll();
    for(Expense expense : allExpenses){
      if(expense.getDate().getYear() == today.getYear()){
        total = total + expense.getAmount();
      }
    }
    return total;
  }
  
  // calculate all time total
  public double getAllTimeTotal(){
    double total = 0;
    List<Expense> allExpenses = repository.findAll();
    for(Expense expense : allExpenses){
      total = total + expense.getAmount();
    }
    return total;
  }
}