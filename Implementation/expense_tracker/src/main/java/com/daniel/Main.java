package com.daniel;

import javax.swing.SwingUtilities;

import com.daniel.controller.ExpenseController;
import com.daniel.controller.GuiController;
import com.daniel.service.ExpenseService;
import com.daniel.view.GuiView;

public class Main {
    public static void main(String[] args) {
    //distribute object creation to minimize risk
   //ExpenseController controller = new ExpenseController(new ExpenseService(new ExpenseRepository()));
    ExpenseController controller=ExpenseController.createExpenseController();
    controller.run();
   //controller.run();

   //ExpenseController controller=ExpenseController.createExpenseController
    //                                              (ExpenseController.
   //                                                  createService(ExpenseController.createRepository()));
   //controller.run();
   //SwingUtilities.invokeLater(()->{
        // new GuiView(new GuiController(new ExpenseService()));
        GuiController.createController();
        
   //});
   }
}