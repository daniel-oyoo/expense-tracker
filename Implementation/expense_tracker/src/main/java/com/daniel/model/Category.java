package com.daniel.model;

public class Category{
    //category details here
    private String type;
    private String quantity;
    private String description;

    public Category(){}
    public Category(String type,String quantity,String description){
        this.type=type;
        this.quantity=quantity;
        this.description=description;
    }

    //getters and setters
    public String getType(){
        return this.type;
    }

    public String getQuantity(){
        return this.quantity;
    }

    public String getDescription(){
        return this.description;
    }

    //setters
    public void setType(String type){
        if(type==null||type.length()==0)throw new IllegalArgumentException("This value cannot be empty");
        this.type=type; 
    }

    public void setQuantity(String quantity){
        if(quantity==null||quantity.length()==0)throw new IllegalArgumentException("This value cannot be empty");
        this.quantity=quantity; 
    }

        public void setDescription(String description){
        if(description==null||description.length()==0)throw new IllegalArgumentException("This value cannot be empty");
        this.description=description; 
    }

    //debuuging and diplays
    public String toString(){
        return "{\n type : " + type + "\n quantity : " + quantity + "\n descrption : " + description + "\n\t}";
    }

}