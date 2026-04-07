package com.daniel.model;
import java.time.LocalDate;
import java.util.*;

public class Expense{
  private int id;
  private double amount;
  private Category category;

  //constrcutor
  public Expense(){}
   /*
  //without id
  public Expense(double amount ,Category category){
    this.amount=amount;
    this.category=new Category();
  }


  
  //with id from repo
  public Expense(int id,double amount ,Category category){
    this.id=id;
    this.amount=amount;
    this.category=new Category();
  }
*/

    //without id
   public Expense(double amount ,Category category){
    this.amount=amount;
    this.category=category;
   }
//with id from repo
  public Expense(int id,double amount ,Category category){
    this.id=id;
    this.amount=amount;
    this.category=category;
  }

  //getters and setters
  //getters
  public void setId(int id){
    //increment for each
    this.id=id;
  }

  public void  setAmount(double amount){
    if(amount<0)amount=0;
    this.amount=amount;
  }

  public void setCategory(Category category){
    if(category==null)throw new IllegalArgumentException("Category must have values !!");
    this.category=category;
  }

  //getters

  public int getId(){
    return this.id;
  }

  public double getAmount(){
    return this.amount;
  }

  public Category getCategory(){
    return this.category;
  }

  //debugging and printing
  public String toString(){
    return "\n{\n id : " + id + "\n amount : " + amount +"\n Date : " + LocalDate.now() +"\n category : " + category.toString() + "\n}\n";
  }


}