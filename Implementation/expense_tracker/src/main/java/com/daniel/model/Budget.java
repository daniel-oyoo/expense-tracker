package com.daniel.model;

public class Budget {
  private double monthlyLimit;
  private double weeklyLimit;
  private double currentSpending;
  
  // constructor sets limits to zero
  public Budget(){
    this.monthlyLimit = 0;
    this.weeklyLimit = 0;
    this.currentSpending = 0;
  }
  
  // set monthly spending limit
  public void setMonthlyLimit(double limit){
    this.monthlyLimit = limit;
  }
  
  // set weekly spending limit
  public void setWeeklyLimit(double limit){
    this.weeklyLimit = limit;
  }
  
  // get monthly limit
  public double getMonthlyLimit(){
    return monthlyLimit;
  }
  
  // get weekly limit
  public double getWeeklyLimit(){
    return weeklyLimit;
  }
  
  // calculate how much is left for month
  public double getRemainingMonthly(double spent){
    return monthlyLimit - spent;
  }
  
  // calculate how much is left for week
  public double getRemainingWeekly(double spent){
    return weeklyLimit - spent;
  }
}