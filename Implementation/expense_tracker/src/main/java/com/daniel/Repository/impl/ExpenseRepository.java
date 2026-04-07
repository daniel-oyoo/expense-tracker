package com.daniel.Repository.impl;
import java.io.*;
import java.util.*;

import com.daniel.Repository.Repository;
import com.daniel.model.Expense;

public class ExpenseRepository implements Repository{

   // static FileRepository  repo = new FileRepository();
    //FileRepository  repo = new FileRepository();
    //store and retrieve here data 
    //id here

    //how do we store data
    //1. a universl list
    //2. a file storage system

    //our intial id
    int code=1;

    //static List<Expense>expenseList=new ArrayList<>();
    //static List<Expense>expenseList=repo.findAll();//how to integrate files with this
    static List<Expense>expenseList=new FileRepository().findAll();

    public String save(Expense expense){
        //set id
        expense.setId(code++);
        //save to repo
        expenseList.add(expense);
        return "Expense with id " + expense.getId() + " saved ";
    }

    //find all/view
    public List<Expense>findAll(){
        //ret copy to avoid direct modifcation
         return new ArrayList<>(expenseList);
        //return repo.findAll();
    }

    //helper method find by id take id return expense
    public Expense findById(int id){
        //we can even chunk this
        List<Expense>searchList=findAll();
       //List<Expense>searchList=repo.findAll();
        //to hold our data
        Expense expense=null;
        for(int i = 0 ;i<searchList.size();i++){
            if(expenseList.get(i).getId()==id)
                expense=expenseList.get(i);
        }
        return expense;
    }

    //update
    public String update(Expense expense){
        //save to repo
        //repo.writeToHumanReadbale(expense);
        expenseList.add(expense);
        return "Expense with id " + expense.getId() + " updated ";
    } 
    
    //delete
    public String delete(int id){
        //copy to work with
        List<Expense>deleteList=findAll();
        for(int i = 0;i<deleteList.size();i++){
            if(deleteList.get(i).getId()==id)deleteList.remove(i);
        }
        return "Expense with id " + id + " deleted successfully";
    }

}