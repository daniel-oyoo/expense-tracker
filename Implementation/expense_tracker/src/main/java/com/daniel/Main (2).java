package com.daniel;

import javax.swing.SwingUtilities;
import com.daniel.controller.ExpenseController;
import com.daniel.controller.GuiController;
import com.daniel.service.AuthService;
import com.daniel.view.GuiView;
import java.util.Scanner;

public class Main {
    private static String globalApiKey = null;
    private static int currentUserId = -1;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== EXPENSE TRACKER ===");
        System.out.println("1. Login with API Key");
        System.out.println("2. Register New User");
        System.out.print("Choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if(choice == 1) {
            System.out.print("Enter your API Key: ");
            String apiKey = scanner.nextLine();
            Integer userId = AuthService.validateApiKey(apiKey);
            
            if(userId == null) {
                System.out.println("Invalid API Key. Exiting.");
                scanner.close();
                return;
            }
            
            globalApiKey = apiKey;
            currentUserId = userId;
            System.out.println("Login successful! Welcome user " + userId);
            
        } else if(choice == 2) {
            // simple registration - just assign next ID
            currentUserId = AuthService.getActiveSessionCount() + 1;
            globalApiKey = AuthService.generateApiKey(currentUserId);
            System.out.println("Registration successful!");
            System.out.println("YOUR API KEY - SAVE THIS: " + globalApiKey);
            System.out.println("You will need this key every time you run the app.");
        }
        // 6VTWb1U9_Eu4Kp8wuVgOIzctfdwgPmm4E8La_M9PiLhEeZ4W9hjOzeDmFNsZFbz8rQHJAOdqj4MLVc14i2KK7Q
        System.out.println("\nSelect mode:");
        System.out.println("1. Console Application");
        System.out.println("2. GUI Application");
        System.out.print("Enter choice (1 or 2): ");
        
        int mode = scanner.nextInt();
        //scanner.close();
        
        if(mode == 1) {
            ExpenseController controller = ExpenseController.createExpenseController();
            //controller.runWithAuth(globalApiKey, currentUserId);
            controller.run();
        } else {
            SwingUtilities.invokeLater(() -> {
                //GuiController guiController = GuiController.createControllerWithAuth(globalApiKey, currentUserId);
                GuiController guiController = GuiController.createController();
                new GuiView(guiController);
            });
        }
    }
}