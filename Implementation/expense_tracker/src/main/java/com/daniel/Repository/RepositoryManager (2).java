package com.daniel.Repository;

import java.util.*;

import com.daniel.Repository.impl.FileRepository;
import com.daniel.model.Expense;

// acts as a buffer between service and repo
// manages ID generation to ensure no conflicts
public class RepositoryManager{
    // get the last ID from file to continue sequence
    // handle case when file is empty
    private int getIdFromFile(){
        try{
            List<Expense> existing = new FileRepository().findAll();
            if(existing.isEmpty()){
                return 0;
            }
            return existing.get(existing.size() - 1).getId();
        } catch(Exception e){
            return 0;
        }
    }
    
    // assign new ID before saving
    public String asignId(Expense expense){
        int currentMax = getIdFromFile();
        expense.setId(currentMax + 1);
        return new FileRepository().save(expense);
    }
}