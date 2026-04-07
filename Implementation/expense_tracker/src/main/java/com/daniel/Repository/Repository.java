package com.daniel.Repository;

import java.util.List;

import com.daniel.model.Expense;

public interface Repository {
    public String save(Expense expense);
    //return all available expenses
    public List<Expense>findAll();
}
