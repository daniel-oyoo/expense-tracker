package com.daniel.Repository;

import java.util.List;
import com.daniel.model.Expense;

// interface defining basic operations for any repository
public interface Repository {
    // save an expense to storage
    public String save(Expense expense);
    
    // return all expenses from storage
    public List<Expense> findAll();
}