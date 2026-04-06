import java.util.*;
import java.io.*;
import java.time.LocalDate;
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
    return "{\n id : " + id + "\n amount : " + amount +"\n Date : " + LocalDate.now() +"\n category : " + category.toString() + "\n}";
  }


}
class Category{
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

class ExpenseController{
    public ExpenseService service;
    //contact with our interface main and all other classes
    public ExpenseController(ExpenseService service){
        this.service=service;
    }

    //display welcome message
public void run(){
        //displayWelcomeMessage
        System.out.println("\nWelcome to our savings and expense tracker\n");
        //set it up
            //for input
    Scanner scanner = new Scanner(System.in);
//loop until user quits
while(true){
        //System.out.println("\n What would you Like to do(1-5) ? ");
        System.out.println("\n1. ADD EXPENSE\n2. VIEW EXPENSE\n3. UPDATE EXPENSE\n4. DELETE EXPENSE\n5. EXIT APPLICATION\n");
        System.out.print("What would you Like to do(1-5) : ");
        int choices=scanner.nextInt();
        //consume new line
        scanner.nextLine();
        switch(choices){
            case 1:
                getInput(choices,scanner);
            break;
            case 2:
                getInput(choices,scanner);
            break;
            case 3:
               getInput(choices,scanner);
            break;
            case 4:
                getInput(choices,scanner);
            break;
            case 5 :
                System.out.println("Goodbye.....");
                System.exit(0);
            break;
            default:
                System.out.println("Invalid choice \nEnter number between 1 - 5");
                //choices=scanner.nextInt();
                //consume new line
                //scanner.nextLine();
            break;
        }
    
     //scanner.close();
    }//end while

    //scanner.close();
}

    //prompt user for input
    public void getInput(int choice,Scanner scanner){
        if(choice==1){
            System.out.print("\nEnter amount :");
            double amount=scanner.nextDouble();
            //clear new line
            scanner.nextLine();
            System.out.print("Enter type :");
            String type=scanner.nextLine();
            System.out.print("Enter quantity :");
            String quantity=scanner.nextLine();
            System.out.println("Enter description below ");
            String desc=scanner.nextLine();

            String result =service.addExpense(amount,type,quantity,desc);
            System.out.println(result);

        }
        if(choice==2){
            System.out.println("\n"+service.viewExpense());//bugg \n
        }
        if(choice==3){
            System.out.print("\nEnter id :");
            int id=scanner.nextInt();
            System.out.print("Enter amount :");
            double amount=scanner.nextDouble();
            //clear new line
            scanner.nextLine();
            System.out.print("Enter type :");
            String type=scanner.nextLine();
            System.out.print("Enter quantity :");
            String quantity=scanner.nextLine();
            System.out.println("Enter description below ");
            String desc=scanner.nextLine();

            String result=service.updateExpense(id,amount,type,quantity,desc);
            System.out.println("\n"+result);
        }
        if(choice==4){
            System.out.print("\nEnter id :");
            int id=scanner.nextInt();

            String result =service.deleteExpense(id);
            System.out.println(result);
        }
        

    }
}

class ExpenseService{
  ExpenseRepository repository;
  FileRepository repo=new FileRepository();
  //FileRepository repo;
    //constructor to fix all this
    public ExpenseService(ExpenseRepository repository
        //,FileRepository repo
    ){
        this.repository=repository;
        //this.repo=repo;
    }
    //all crud here//all clculations
    //create expense
    public String addExpense(double amount,String type,String quantity,String descrption){
      
        //create expense
        Expense expense=new Expense(amount,new Category(type,quantity,descrption));
        //save to file
        repo.save(expense);
        repo.writeToHumanReadbale(expense);
        //save to storage
        return repository.save(expense);

    }

    //view expense
    public List<Expense> viewExpense(){
        //repo.findAll();
        //return repository.findAll().isEmpty()?repo.findAll():repository.findAll();
        //return repo.findAll().isEmpty()?repository.findAll():repo.findAll();
       return repository.findAll();
       //if(repo.findAll()==null&&repository.findAll().isEmpty())return repository.findAll();
       //else return repo.findAll();
       //doesnt work on empty text fikes
        //return repo.findAll();
    }

    //update expense
    public String updateExpense(int id,double amount,String type,String quantity,String descrption){
        Expense expense=null;
        Category category=new Category(type,quantity,descrption);
        //find it in repo
        expense=repository.findById(id);
        //validate
        if(expense==null)throw new IllegalArgumentException("Expense with id " + id + " does not exist!!");
        //update it
        expense.setAmount(amount);
        expense.setCategory(category);

        //save to file
        repo.save(expense);

        //save to db
       return  repository.update(expense);
    }
    //delete expense
    public String deleteExpense(int id){
        Expense expense=null;
        //find it in repo
        expense=repository.findById(id);
        //validate
        if(expense==null)throw new IllegalArgumentException("Expense with id " + id + " does not exist!!");
        return repository.delete(id);
    }
}

interface Repository{
    public String save(Expense expense);
    //return all available expenses
    public List<Expense>findAll();
}

class ExpenseRepository implements Repository{

    static FileRepository  repo = new FileRepository();
    //FileRepository  repo = new FileRepository();
    //store and retrieve here data 
    //id here

    //how do we store data
    //1. a universl list
    //2. a file storage system

    //our intial id
    int code=1;

    //static List<Expense>expenseList=new ArrayList<>();
    static List<Expense>expenseList=repo.findAll();//how to integrate files with this

    public String save(Expense expense){
        //set id
        expense.setId(code++);
        //save to repo
        expenseList.add(expense);
        return "Expense with id " + expense.getId() + " saved ";
    }

    //find all/view
    public List<Expense>findAll(){
        //ret copy to avoid direct modifcation
         return new ArrayList<>(expenseList);
        //return repo.findAll();
    }

    //helper method find by id take id return expense
    public Expense findById(int id){
        //we can even chunk this
        //List<Expense>searchList=findAll();
        List<Expense>searchList=repo.findAll();
        //to hold our data
        Expense expense=null;
        for(int i = 0 ;i<searchList.size();i++){
            if(expenseList.get(i).getId()==id)
                expense=expenseList.get(i);
        }
        return expense;
    }

    //update
    public String update(Expense expense){
        //save to repo
        expenseList.add(expense);
        return "Expense with id " + expense.getId() + " updated ";
    } 
    
    //delete
    public String delete(int id){
        //copy to work with
        List<Expense>deleteList=findAll();
        for(int i = 0;i<deleteList.size();i++){
            if(deleteList.get(i).getId()==id)deleteList.remove(i);
        }
        return "Expense with id " + id + " deleted successfully";
    }

}

class FileRepository implements Repository{
    static ExpenseRepository expeRepo =new ExpenseRepository();
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
                String results=String.format("%d|%.2f|%s|%s|%s|%s",expense.getId(),expense.getAmount(),
                                                             expense.getCategory().getType(),expense.getCategory().getQuantity(),
                                                             expense.getCategory().getDescription(),LocalDate.now());
                //writer.writeLine(expense);
                //writer.newLine();//platform indpendednt newline
                //writer.writeLine(expense);
                //writer.newLine();//platform indpendednt newline
                writer.write(results);
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
                
                Category category=new Category(type,quantity,descrption);
                Expense expense = new Expense(id,amount,category);

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
            }catch(IOException e){
                System.out.println("Error : " + e.getMessage());
            }
            //finally{
               // return "Expense with id " + expense.getId() + " saved to file";
            //}

         //return "Expense with id " + expense.getId() + " saved to file";

   }

   
}



class Main{
    public static void main(String [] args){
   ExpenseController controller = new ExpenseController(new ExpenseService(new ExpenseRepository()));
   controller.run();

    }
}

/* 
class Test{
    public static void test(){
            //for input
    Scanner scanner = new Scanner(System.in);


        //System.out.println("\n What would you Like to do(1-5) ? ");
        System.out.println("\n1. ADD EXPENSE\n2. VIEW EXPENSE\n3. UPDATE EXPENSE\n4. DELETE EXPENSE\n5. EXIT APPLICATION\n");
        System.out.print("What would you Like to do(1-5) : ");
        int choices=scanner.nextInt();
        //consume new line
        scanner.nextLine();
        switch(choices){
            default:
                System.out.println("Invalid choice \nEnter number between 1 - 5");
                //choices=scanner.nextInt();
                //consume new line
                //scanner.nextLine();
        }
     scanner.close();
        Category c = new Category();
     c.type="food";
     c.quantity="4 litres";
     c.description="This is test data";

     System.out.println(c);


     Expense e = new Expense();
     e.id=56;
     e.amount=789.90;
     e.category=c;

     System.out.println(e);

    }
}


*/