/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw9;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Danny
 */
public class DatabaseListApp extends Application {
    
    private static final String DB_URL = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW9/Coffee.db";
    
    @Override
    public void start(Stage primaryStage) {
        //create list box 
        ListView<String> listView = new ListView<>();
        
        //connect to the database
        try
        {
         Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("Select * from Coffee");
         
         while(rs.next())
         {
            listView.getItems().add(rs.getString("Description"));
            listView.getItems().add(rs.getString("ProdNum"));
            listView.getItems().add(rs.getString("Price"));
         }
         
         rs.close();
         stmt.close();
         conn.close();
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        //create layout container
        VBox layout = new VBox(20);
        layout.getChildren().add(listView);
        
        //create scene
        Scene scene = new Scene(layout, 300, 200);
        
        //set the scene on the stage
        primaryStage.setScene(scene);
        
        insertData();
        
        //set the stage title and show it
        primaryStage.setTitle("Database List App");
        primaryStage.show();
    }
    
    private void insertData()
    {
        String db = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW9/Coffee.db";
        
        String insertSQL = "INSERT INTO Coffee (Description, ProdNum, Price) VALUES (?, ?, ?) ";

        try(Connection conn = DriverManager.getConnection(db);
                PreparedStatement pstmt = conn.prepareStatement(insertSQL))       
        {
            pstmt.setString(1, "Mocha Frapp");
            pstmt.setString(2, "C120");
            pstmt.setDouble(3, 12.6);
            
            //execute update
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully");
        } catch(SQLException e)
        {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
