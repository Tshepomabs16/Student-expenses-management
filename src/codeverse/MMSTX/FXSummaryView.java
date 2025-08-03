/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeverse.MMSTX;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
/**
 *
 * @author ADMIN
 */
public class FXSummaryView extends Application{
    // This will be set before the JavaFX window opens
    public static StudentExpenseTracker selectedTracker;

    // This method is automatically called when the JavaFX window starts
    public void start(Stage stage) {
        VBox root = new VBox(10); // VBox layout with spacing of 10
        root.setPadding(new Insets(20)); // Padding around edges

        // Create a label for the student's name
        Label nameLabel = new Label("Student: " + selectedTracker.studentName);
        nameLabel.setFont(new Font("Arial", 20)); // Set font size and style

        // Create a label for the total expenses
        Label totalLabel = new Label("Total Expenses This Month: R" + selectedTracker.getTotalForMonth());
        totalLabel.setFont(new Font("Arial", 18));

        // Add both labels to the layout
        root.getChildren().addAll(nameLabel, totalLabel);

        // Create and show the scene
        Scene scene = new Scene(root, 350, 150);
        stage.setScene(scene);
        stage.setTitle("JavaFX Expense Summary");
        stage.show();
    }
}
