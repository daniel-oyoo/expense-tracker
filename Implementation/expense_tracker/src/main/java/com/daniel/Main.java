package com.daniel;

import javax.swing.SwingUtilities;
import com.daniel.controller.ExpenseController;
import com.daniel.controller.GuiController;
import com.daniel.view.GuiView;

public class Main {
    public static void main(String[] args) {
        
        // ask user which version to run
        System.out.println("Select mode:");
        System.out.println("1. Console Application");
        System.out.println("2. GUI Application");
        System.out.print("Enter choice (1 or 2): ");
        
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int choice = scanner.nextInt();
        //scanner.close();
        
        if(choice == 1) {
            // run console version
            ExpenseController controller = ExpenseController.createExpenseController();
            controller.run();
        } else {
            // run GUI version
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GuiController guiController = GuiController.createController();
                    new GuiView(guiController);
                }
            });
        }
    }
}