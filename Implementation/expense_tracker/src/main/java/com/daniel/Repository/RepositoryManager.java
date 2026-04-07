//act as a buffer between service an repo --manages i generation
package com.daniel.Repository;

import com.daniel.Repository.impl.FileRepository;
import com.daniel.model.Expense;

public class RepositoryManager{
    //int id=(new FileRepository().findAll().size())+1;
    //int id=(new FileRepository().findAll().size());
    //get id of last object
    int id=new FileRepository().findAll().getLast().getId();
    //before asigning id,load list and see how long it is
    public String asignId(Expense expense){
      //int id=(new FileRepository().findAll().size())+1;
      expense.setId(++id);
     return new FileRepository().save(expense);
    }
}