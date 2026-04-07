package com.daniel.Repository.impl;

import java.io.*;
import java.util.*;
import com.daniel.Repository.Repository;
import com.daniel.model.Expense;

// in-memory repository that syncs with file storage
public class ExpenseRepository implements Repository{

    // static list holds all expenses in memory
    static List<Expense> expenseList = new ArrayList<>();
    
    // constructor loads from file when repository is created
    public ExpenseRepository(){
        expenseList = new FileRepository().findAll();
    }

    // save expense to memory and file
    public String save(Expense expense){
        expenseList.add(expense);
        new FileRepository().save(expense);
        new FileRepository().writeToHumanReadable(expense);
        return "Expense with id " + expense.getId() + " saved";
    }

    // return all expenses from memory
    public List<Expense> findAll(){
        return new ArrayList<>(expenseList);
    }

    // find expense by id
    public Expense findById(int id){
        for(Expense expense : expenseList){
            if(expense.getId() == id){
                return expense;
            }
        }
        return null;
    }

    // update existing expense
    public String update(Expense expense){
        // find and replace in list
        for(int i = 0; i < expenseList.size(); i++){
            if(expenseList.get(i).getId() == expense.getId()){
                expenseList.set(i, expense);
                break;
            }
        }
        // rewrite entire file with updated list
        rewriteEntireFile();
        return "Expense with id " + expense.getId() + " updated";
    }
    
    // delete expense by id
    public String delete(int id){
        // find and remove from list
        Expense toRemove = null;
        for(Expense expense : expenseList){
            if(expense.getId() == id){
                toRemove = expense;
                break;
            }
        }
        if(toRemove != null){
            expenseList.remove(toRemove);
            // rewrite entire file without deleted expense
            rewriteEntireFile();
            return "Expense with id " + id + " deleted successfully";
        }
        return "Expense with id " + id + " not found";
    }
    
    // helper method to rewrite entire file from memory list
    private void rewriteEntireFile(){
        FileRepository fileRepo = new FileRepository();
        // clear and rewrite file
        fileRepo.clearFile();
        for(Expense expense : expenseList){
            fileRepo.save(expense);
            fileRepo.writeToHumanReadable(expense);
        }
    }
}