package com.daniel.inputValidation;

import java.time.LocalDate;
import java.util.Scanner;

public class InputValidation {
  
  // get valid integer between min and max
  public static int getValidInt(Scanner scanner, String prompt, int min, int max){
    while(true){
      System.out.print(prompt);
      try{
        int value = Integer.parseInt(scanner.nextLine().trim());
        if(value >= min && value <= max){
          return value;
        }
        System.out.println("Enter number between " + min + " and " + max);
      } catch(NumberFormatException e){
        System.out.println("Invalid input. Enter a valid number.");
      }
    }
  }
  
  // get valid positive double
  public static double getValidDouble(Scanner scanner, String prompt){
    while(true){
      System.out.print(prompt);
      try{
        double value = Double.parseDouble(scanner.nextLine().trim());
        if(value > 0){
          return value;
        }
        System.out.println("Amount must be greater than zero");
      } catch(NumberFormatException e){
        System.out.println("Invalid input. Enter a valid amount.");
      }
    }
  }
  
  // get non-empty string
  public static String getNonEmptyString(Scanner scanner, String prompt){
    while(true){
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      if(!input.isEmpty()){
        return input;
      }
      System.out.println("Field cannot be empty");
    }
  }
  
  // get valid date or default to today
  public static LocalDate getValidDate(Scanner scanner, String prompt){
    while(true){
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      if(input.isEmpty()){
        return LocalDate.now();
      }
      try{
        return LocalDate.parse(input);
      } catch(Exception e){
        System.out.println("Invalid date. Use YYYY-MM-DD");
      }
    }
  }
}