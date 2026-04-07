package com.daniel;

import com.daniel.controller.ExpenseController;

public class Main {
    public static void main(String[] args) {
    //distribute object creation to minimize risk
   //ExpenseController controller = new ExpenseController(new ExpenseService(new ExpenseRepository()));
   ExpenseController controller=ExpenseController.createExpenseController();
   controller.run();
   //controller.run();
    }
}