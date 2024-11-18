/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classexercise10;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import javax.swing.JOptionPane;


public class PatientForm extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("HERNANDEZ HOSPITAL");
        
        //Formatting the Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        
        Label patientIDLabel = new Label ("Patient ID");
        Label patientNameLabel = new Label ("Patient Name");
        Label patientAgeLabel = new Label ("Patient Age");
        Label StateLabel = new Label ("State");
        Label genderLabel = new Label ("Gender");
        Label medicationsLabel = new Label ("Medications");
        
        TextField patientIDTextField =new TextField();
        TextField patientNameTextField =new TextField();
        TextField patientAgeTextField =new TextField();
        
        //String array
        ComboBox<String>stateComboBox = new ComboBox<>();
        stateComboBox.getItems().addAll("CA","FL","TX");
        
        //Grouping items to work dependently in a group
        ToggleGroup genderToggleGroup = new ToggleGroup();
        RadioButton maleRadioButton = new RadioButton("Male");
        RadioButton femaleRadioButton = new RadioButton("Female");
        //adds the radiobutton to the group
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        
        CheckBox medication1CheckBox = new CheckBox("Tylenol");
        CheckBox medication2CheckBox = new CheckBox("Advil");
        CheckBox medication3CheckBox = new CheckBox("Blood Thinner");
        
        Button submitButton = new Button("Submit");
        
        Image image = new Image("file:Keiser.png");
        ImageView imageView = new ImageView(image);
        
        gridPane.add(imageView, 2, 0);
        
        gridPane.add(patientIDLabel, 0, 0);
        gridPane.add(patientIDTextField, 1, 0);
        
        gridPane.add(patientNameLabel, 0, 1);
        gridPane.add(patientNameTextField,1, 1);
        
        gridPane.add(patientAgeLabel, 0, 2);
        gridPane.add(patientAgeTextField, 1, 2);
        
        gridPane.add(StateLabel, 0, 3);
        gridPane.add(stateComboBox, 1, 3);
        
        gridPane.add(genderLabel, 0, 4);
        gridPane.add(maleRadioButton, 2, 4);
        gridPane.add(femaleRadioButton, 1, 4);
        
        gridPane.add(medicationsLabel, 0, 5);
        gridPane.add(medication1CheckBox, 1, 5);
        gridPane.add(medication2CheckBox, 2, 5);
        gridPane.add(medication3CheckBox, 3, 5);
        
        gridPane.add(submitButton, 0, 6);
        
        Scene scene = new Scene (gridPane, 600, 300);
        primaryStage.setScene(scene);
        
    
        submitButton.setOnAction(event ->  {
            
            String patientid = patientIDTextField.getText();
            String patientName = patientNameTextField.getText();
            String patientAge = patientAgeTextField.getText();
            String state = stateComboBox.getValue();
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String medications = "";
            
            if(medication1CheckBox.isSelected()) medications +="Tylenol,";
            if(medication2CheckBox.isSelected()) medications +="Advil,";
            if(medication3CheckBox.isSelected()) medications +="Blood thinner";
            
            //call a method with its parameters
            writeDataToFile(patientid, patientName,patientAge,state,
            gender, medications);
            
   
        
        });//closes the action
        
        //This enables the form to be visible
        primaryStage.show();
        
    }
    
    private void writeDataToFile(String a, String patientName,
            String patientAge, String state, String gender, 
            String medications)
{
    try (BufferedWriter writer = new BufferedWriter
            (new FileWriter("C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Desktop/patient_data.txt", true))) {
        writer.write("Patient ID: " + a + "\n");
        writer.write("Patient Name: " + patientName + "\n");
        writer.write("Patient Age: " + patientAge + "\n");
        writer.write("State: " + state + "\n");
        writer.write("Gender: " + gender + "\n");
        writer.write("Medications: " + medications + "\n");
        writer.newLine();
        
        JOptionPane.showMessageDialog(null, "Patient Data Recorded");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, e.toString());
    }
    
    // Database insertion part
    Statement stmt;

    try {
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        System.out.println("Driver Connected");

        // Corrected SQLite connection URL
        String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/ClassExercise10/patients.db";
        Connection connection = DriverManager.getConnection(url);

        System.out.println("DB connected");
        stmt = connection.createStatement();

        String query = "INSERT INTO Patients (PatientID, name, age, state, gender, medications) VALUES (" 
            + Integer.parseInt(a) + " ,'" 
            + patientName + "'," 
            + Integer.parseInt(patientAge) + ",'" 
            + state + "','" 
            + gender + "','" 
            + medications + "')";

        stmt.execute(query);
        System.out.println("Record inserted into DB");
    } catch (Exception ex) {
        System.out.println(ex.toString());
    }
}

    
    public static void main(String[] args) {
        launch(args);
    }
    
}