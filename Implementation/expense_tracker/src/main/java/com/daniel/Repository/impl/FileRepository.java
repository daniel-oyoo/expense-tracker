package com.daniel.Repository.impl;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import com.daniel.Repository.Repository;
import com.daniel.model.Category;
import com.daniel.model.Expense;

// handles all direct file operations
public class FileRepository implements Repository{
    
    static List<Expense> fileExpeList = new ArrayList<>();
    private static final String fileName = "C:\\Users\\markd\\Desktop\\Budget_Tracker\\Data\\expense.txt";
    private static final String humanReadable = "C:\\Users\\markd\\Desktop\\Budget_Tracker\\Data\\expenseForHumans.txt";

    // create file object
    public File createFile(){
        return new File(fileName);
    }
    
    // clear file contents
    public void clearFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))){
            writer.write("");
        } catch(IOException e){
            System.out.println("Error clearing file: " + e.getMessage());
        }
    }

    // save expense to file - appends to end
    public String save(Expense expense){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            String results = String.format("%d|%.2f|%s|%s|%s|%s",
                expense.getId(),
                expense.getAmount(),
                expense.getCategory().getType(),
                expense.getCategory().getQuantity(),
                expense.getCategory().getDescription(),
                expense.getDate());  // uses stored date from expense object
            writer.write(results);
            writer.newLine();
        } catch(IOException e){
            System.out.println("Error saving: " + e.getMessage());
        }
        return "Expense saved to file";
    }

    // read all expenses from file
    public List<Expense> findAll() {
        fileExpeList.clear();
        File file = createFile();
        if(!file.exists()){
            return fileExpeList;
        }
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while(line != null){
                if(!line.trim().isEmpty()){
                    String[] parts = line.split("\\|");
                    if(parts.length >= 6){
                        try{
                            int id = Integer.parseInt(parts[0].trim());
                            double amount = Double.parseDouble(parts[1].trim());
                            String type = parts[2].trim();
                            String quantity = parts[3].trim();
                            String description = parts[4].trim();
                            LocalDate date = LocalDate.parse(parts[5].trim());
                            
                            Category category = new Category(type, quantity, description);
                            Expense expense = new Expense(id, amount, category, date);
                            fileExpeList.add(expense);
                        } catch(Exception e){
                            System.out.println("Skipping bad line: " + line);
                        }
                    }
                }
                line = reader.readLine();
            }
        } catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
        return fileExpeList;
    }

    // write expense to human readable format
    public void writeToHumanReadable(Expense expense){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(humanReadable, true))){
            writer.write(expense.toString());
            writer.newLine();
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}