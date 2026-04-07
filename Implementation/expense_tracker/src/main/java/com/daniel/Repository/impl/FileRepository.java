package com.daniel.Repository.impl;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.daniel.Repository.Repository;
import com.daniel.model.Category;
import com.daniel.model.Expense;

public class FileRepository implements Repository{
    //static ExpenseRepository expeRepo =new ExpenseRepository();
    //static List<Expense>fileExpeList=expeRepo.findAll();
   static List<Expense>fileExpeList=new ArrayList<>();
    private static final String fileName="C:\\Users\\markd\\Desktop\\Budget_Tracker\\Data\\expense.txt";
    private static final String humanReadable="C:\\Users\\markd\\Desktop\\Budget_Tracker\\Data\\expenseForHumans.txt";

    //set and create id
    int code=1;

    //now these do it usinf files--writing
    //create file return file reader object for buffered reader
    public File createFile(){
        File file=new File(fileName);
        return file;
    }

    //find all
    //public List<Expense>findAllFromFile(){}

   @Override
   public String save(Expense expense) {
    expense.setId(code++);
    // TODO Auto-generated method stub
   // throw new UnsupportedOperationException("Unimplemented method 'save'");
   //try with resources
      try(BufferedWriter writer = new BufferedWriter
            (new FileWriter(fileName
                //createFile()
                ,true)))
            {
                //String results="ID|"+expense.getId()+"Amount|"+expense.getAmount()+"Type|"+expense.getCategory().getType()+"Quantity|"+expense.getCategory().getQuantity()+"Description|"+expense.getCategory().getDescription();
                ///String results=String.format("ID|Amount|Type|Quantity|Description|Date|\n%d|%.2f|%s|%s|%s|%s",expense.getId(),expense.getAmount(),expense.getCategory().getType(),expense.getCategory().getQuantity(),expense.getCategory().getDescription(),LocalDate.now());
                String results=String.format("\n%d|%.2f|%s|%s|%s|%s",expense.getId(),expense.getAmount(),
                                                             expense.getCategory().getType(),expense.getCategory().getQuantity(),
                                                             expense.getCategory().getDescription(),LocalDate.now());
                //writer.writeLine(expense);
                //writer.newLine();//platform indpendednt newline
                //writer.writeLine(expense);
                //writer.newLine();//platform indpendednt newline
                writer.write(results);
                //writer.newLine();
                //writer.write(expense.toString());
            }catch(IOException e){
                System.out.println("Error : " + e.getMessage());
            }
            //finally{
               // return "Expense with id " + expense.getId() + " saved to file";
            //}

         return "Expense with id " + expense.getId() + " saved to file";
      }

   @Override
   public List<Expense> findAll() {
    // TODO Auto-generated method stub
    //throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    //open file and return everything as a list
    //if(!fileName.isEmpty()){//doesnt work with empty files
    try(BufferedReader reader = new BufferedReader
        (new FileReader(createFile())))
        {
            //read
            String lines=reader.readLine();
            //reader.skip(0);
            //reader.readLine().
            while(lines!=null){
                String [] parts=lines.split("\\|");
                //if(lines.equalsIgnoreCase("ID|Amount|Type|Quantity|Description|Date|"))//String discard=lines;//
                //lines=reader.readLine();//continue;//{}
                //if(lines==""||lines.charAt(0)=='I')continue;
                //if(parts[0].equalsIgnoreCase("id")||parts[0].equals(""))continue;
                //if(parts[0].equalsIgnoreCase("id"))reader.skip(42);//continue;
                int id=Integer.parseInt(parts[0]);
                double amount=Double.parseDouble(parts[1]);
                String type=parts[2];
                String quantity=parts[3];
                String descrption=parts[4];
                
                //Category category=new Category(type,quantity,descrption);
                //Category category = new Category(type,quantity,descrption);
                //Expense expense = new Expense(id,amount,category);
                Expense expense = new Expense(id,amount,new Category(type,quantity,descrption));

                fileExpeList.add(expense);
                //read nxt line
                lines=reader.readLine();
 
            }
        }catch(IOException e){
        System.out.println("Error :" + e.getMessage());
    }
 // }//end if
       return fileExpeList;
   }

   //write to humanreadable
   public void writeToHumanReadbale(Expense expense){
    //expense.setId(code++);
    // TODO Auto-generated method stub
   // throw new UnsupportedOperationException("Unimplemented method 'save'");
   //try with resources
      try(BufferedWriter writer = new BufferedWriter
            (new FileWriter(humanReadable
                //createFile()
                ,true)))
            {
                //String results="ID|"+expense.getId()+"Amount|"+expense.getAmount()+"Type|"+expense.getCategory().getType()+"Quantity|"+expense.getCategory().getQuantity()+"Description|"+expense.getCategory().getDescription();
                ///String results=String.format("ID|Amount|Type|Quantity|Description|Date|\n%d|%.2f|%s|%s|%s|%s",expense.getId(),expense.getAmount(),expense.getCategory().getType(),expense.getCategory().getQuantity(),expense.getCategory().getDescription(),LocalDate.now());
                //String results=String.format("%d|%.2f|%s|%s|%s|%s",expense.getId(),expense.getAmount(),
                //                                             expense.getCategory().getType(),expense.getCategory().getQuantity(),
                //                                             expense.getCategory().getDescription(),LocalDate.now());
                //writer.writeLine(expense);
                //writer.newLine();//platform indpendednt newline
                //writer.writeLine(expense);
                //writer.newLine();//platform indpendednt newline
                //writer.write(results);
                writer.write(expense.toString());
                writer.newLine();
            }catch(IOException e){
                System.out.println("Error : " + e.getMessage());
            }
            //finally{
               // return "Expense with id " + expense.getId() + " saved to file";
            //}

         //return "Expense with id " + expense.getId() + " saved to file";

   }

   
}