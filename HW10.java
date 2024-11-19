/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Danny
 */
public class HW10 extends Application {
    
    public static final double cost = 0.75;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vending Machine");
        
        CheckBox colaBox = new CheckBox("Cola");
        CheckBox lemonBox = new CheckBox("Lemon-lime soda");
        CheckBox sodaBox = new CheckBox("Grade soda");
        CheckBox rootBeerBox = new CheckBox("Root beer");
        CheckBox waterBox = new CheckBox("Bottled water");
        
        Label costLabel = new Label("Each Drink Costs $0.75");
        
        TextField colaField = new TextField();
        TextField lemonField = new TextField();
        TextField sodaField = new TextField();
        TextField rootBeerField = new TextField();
        TextField waterField = new TextField();
        
        GridPane pane = new GridPane();
        
        Button button = new Button("Submit");
        
        pane.add(colaBox, 0, 0);
        pane.add(lemonBox, 0, 1);
        pane.add(sodaBox, 0, 2);
        pane.add(rootBeerBox, 0, 3);
        pane.add(waterBox, 0, 4);
        
        pane.add(colaField, 1, 0);
        pane.add(lemonField, 1, 1);
        pane.add(sodaField, 1, 2);
        pane.add(rootBeerField, 1, 3);
        pane.add(waterField, 1, 4);
        
        pane.add(costLabel, 1, 5);
        
        pane.add(button, 0, 6);
        
        button.setOnAction(e ->
        {
            double colaSales = 0;
            double lemonSales = 0;
            double sodaSales = 0;
            double rootBeerSales = 0;
            double waterSales = 0;
            
            if(colaBox.isSelected())
            {
                colaSales = returnQuantity(colaField.getText(), "Cola");
            }
            
            if(lemonBox.isSelected())
            {
                lemonSales = returnQuantity(lemonField.getText(), "Lemon");
            }
            
            if(sodaBox.isSelected())
            {
                sodaSales = returnQuantity(sodaField.getText(), "Soda");
            }
            
            if(rootBeerBox.isSelected())
            {
                rootBeerSales = returnQuantity(rootBeerField.getText(), "Root Beer");
            }
            
            if(waterBox.isSelected())
            {
                waterSales = returnQuantity(waterField.getText(), "Water");
            }
            
            
            System.out.println("Cola: " + colaSales);
            System.out.println("Lemon: " + lemonSales);
            System.out.println("Soda: " + sodaSales);
            System.out.println("Root Beer: " + rootBeerSales);
            System.out.println("Water: " + waterSales);
            
            insertData(colaSales, lemonSales, sodaSales, rootBeerSales, waterSales);
        
        }
        );
        
        Scene scene = new Scene(pane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static double returnQuantity(String field, String object)
    {
        double q = Double.parseDouble(field);
                
        
                if(quantity(q))
                {
                    double sales = cost * q;
                    
                    return sales;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, object + " Quantity cannot be under 1 or over 3");
                    
                    return 0;
                }
    }
    
    public static boolean quantity(double q)
    {
        if(q < 1 || q > 3)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public static Connection connect()
    {
        String db = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW10/vendingmachine.db";
        
        Connection conn = null;
        
        try
        {
         conn = DriverManager.getConnection(db);
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    private void insertData(double cola, double lemon, double soda, double rootBeer, double water)
    {
        String url = "INSERT INTO vending_machine (cola, lemon, soda, rootBeer, water) VALUES (?, ?, ?, ?, ?)";
        
        try(Connection conn = this.connect();
                
                PreparedStatement pstmt = conn.prepareStatement(url))
        {
            pstmt.setDouble(1, cola);
            pstmt.setDouble(2, lemon);
            pstmt.setDouble(3, soda);
            pstmt.setDouble(4, rootBeer);
            pstmt.setDouble(5, water);
            
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Inserted Successfully");
            
        } catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
