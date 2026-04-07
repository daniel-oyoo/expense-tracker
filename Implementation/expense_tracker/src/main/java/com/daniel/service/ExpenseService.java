package com.daniel.service;
import java.util.*;

import com.daniel.model.Category;
import com.daniel.model.Expense;
import com.daniel.Repository.impl.*;

//tf is this so called type parameter
//public class ExpenseService<ExpenseRepository>{
public class ExpenseService{

  public ExpenseRepository repository;
  //uncontrolled changes 
  public FileRepository repo=new FileRepository();
  //FileRepository repo;
    //constructor to fix all this
    public ExpenseService(ExpenseRepository repository
        //,FileRepository repo
    ){
        this.repository=repository;
        //this.repo=repo;
    }
    public ExpenseService() {
        //TODO Auto-generated constructor stub
    }
    //all crud here//all clculations
    //create expense
    public String addExpense(double amount,String type,String quantity,String descrption){
      
        //create expense
        Expense expense=new Expense(amount,new Category(type,quantity,descrption));
        //save to file
        repo.save(expense);
        repo.writeToHumanReadbale(expense);
        //save to storage
       // return repository.save(expense);
       return repo.save(expense);

    }

    //view expense
    public List<Expense> viewExpense(){
        //repo.findAll();
        //return repository.findAll().isEmpty()?repo.findAll():repository.findAll();
        //return repo.findAll().isEmpty()?repository.findAll():repo.findAll();
        return repository.findAll();
       //if(repo.findAll()==null&&repository.findAll().isEmpty())return repository.findAll();
       //else return repo.findAll();
       //doesnt work on empty text fikes
        //return repo.findAll();
    }

    //update expense
    public String updateExpense(int id,double amount,String type,String quantity,String descrption){
        Expense expense=null;
        Category category=new Category(type,quantity,descrption);
        //find it in repo
        expense=repository.findById(id);
        //Expense expense=repository.findById(id);
        //validate
        if(expense==null)throw new IllegalArgumentException("Expense with id " + id + " does not exist!!");
        //update it
        expense.setAmount(amount);
        expense.setCategory(category);

        //save to file
        repo.save(expense);
        repo.writeToHumanReadbale(expense);

        //save to db
       return  repository.update(expense);
    }
    //delete expense
    public String deleteExpense(int id){
        Expense expense=null;
        //find it in repo
        expense=repository.findById(id);
       /// Expense expense=repository.findById(id);
        //validate
        if(expense==null)throw new IllegalArgumentException("Expense with id " + id + " does not exist!!");
        return repository.delete(id);
    }
}