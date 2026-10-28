package com.daniel.model;
import java.time.LocalDate;

public class Expense{
  private int id;
  private double amount;
  private Category category;
  private LocalDate date;  // stores when expense was created

  // empty constructor
  public Expense(){}
  
  // constructor for new expenses - no id yet
  public Expense(double amount, Category category){
    this.amount = amount;
    this.category = category;
    this.date = LocalDate.now();  // set date to today automatically
  }
  
  // constructor for loading existing expenses from file
  public Expense(int id, double amount, Category category, LocalDate date){
    this.id = id;
    this.amount = amount;
    this.category = category;
    this.date = date;  // use the date from file
  }

  // setters
  public void setId(int id){
    this.id = id;
  }

  public void setAmount(double amount){
    if(amount < 0) amount = 0;
    this.amount = amount;
  }

  public void setCategory(Category category){
    if(category == null) throw new IllegalArgumentException("Category cannot be null");
    this.category = category;
  }
  
  public void setDate(LocalDate date){
    this.date = date;
  }

  // getters
  public int getId(){
    return this.id;
  }

  public double getAmount(){
    return this.amount;
  }

  public Category getCategory(){
    return this.category;
  }
  
  public LocalDate getDate(){
    return this.date;
  }

  // display expense
  public String toString(){
    return "\n{\n id : " + id + "\n amount : " + amount + "\n Date : " + date + "\n category : " + category.toString() + "\n}\n";
  }
}