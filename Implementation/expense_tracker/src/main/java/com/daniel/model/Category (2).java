package com.daniel.model;

public class Category{
  private String type;
  private String quantity;
  private String description;

  // empty constructor
  public Category(){}
  
  // constructor with all fields
  public Category(String type, String quantity, String description){
    this.type = type;
    this.quantity = quantity;
    this.description = description;
  }

  // getters
  public String getType(){
    return this.type;
  }

  public String getQuantity(){
    return this.quantity;
  }

  public String getDescription(){
    return this.description;
  }

  // setters with validation
  public void setType(String type){
    if(type == null || type.length() == 0){
      throw new IllegalArgumentException("Type cannot be empty");
    }
    this.type = type; 
  }

  public void setQuantity(String quantity){
    if(quantity == null || quantity.length() == 0){
      throw new IllegalArgumentException("Quantity cannot be empty");
    }
    this.quantity = quantity; 
  }

  public void setDescription(String description){
    if(description == null || description.length() == 0){
      throw new IllegalArgumentException("Description cannot be empty");
    }
    this.description = description; 
  }

  // display category
  public String toString(){
    return "{\n type : " + type + "\n quantity : " + quantity + "\n description : " + description + "\n\t}";
  }
}