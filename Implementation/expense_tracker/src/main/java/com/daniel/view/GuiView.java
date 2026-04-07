package com.daniel.view;
import javax.swing.*;

import com.daniel.controller.GuiController;

import java.awt.*;
import java.awt.event.*;
public class GuiView extends JFrame implements ActionListener{
    //controller
    GuiController guiController;
    //labels
    JLabel l1,l2,l3,l4;
    //textfield
    JTextField t1,t2,t3;
    //textarea
    JTextArea t4;
    //button
    JButton btn1,btn2;

    double amount;
    String type,quantity,description;


    public GuiView(GuiController guiController){
        this.guiController=guiController;
     //title
     setTitle("Expense Tracker");
     //
        setSize(600,400);
        setLocationRelativeTo(null);
        //setLayout(new GridLayout(0,2));
        //setBounds(50,50,400,400);
        setLayout(new GridLayout(0,2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //intialize components
        l1=new JLabel("Amount");
        l2=new JLabel("Type");
        l3=new JLabel("Quantity");
        l4=new JLabel("Description");

        t1=new JTextField();
        t2=new JTextField();
        t3=new JTextField();

        t4=new JTextArea();

        btn1=new JButton("SAVE");
        btn2=new JButton("EXIT");

        //add to componets

        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(l3);
        add(t3);
        add(l4);
        add(t4);
        add(btn1);
        add(btn2);

        //add action listener
        btn1.addActionListener(this);
        btn2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        if(e.getSource()==btn2)System.exit(0);
        amount=Double.parseDouble(t1.getText());
        type=t2.getText();
        quantity=t3.getText();
        description=t4.getText();

        //call gui controller
        //new GuiController().addExpense();
        JOptionPane.showMessageDialog(null, guiController.
                    addExpense(amount,type,quantity,description));
    }
}
