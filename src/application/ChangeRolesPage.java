package application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userNameRecognizerTestbed.UserNameRecognizer;
import PasswordRecognizer.PasswordRecognizer;

import java.sql.SQLException;

import databasePart1.*;
/**
 * SetupAccountPage class handles the account setup process for new users.
 * Users provide their userName, password, and a valid invitation code to register.
 */
public class ChangeRolesPage {
	
    private final DatabaseHelper databaseHelper;
    private String newRole = "";
    // DatabaseHelper to handle database operations.
    public ChangeRolesPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    /**
     * Displays the Setup Account page in the provided stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage, String username) {
    	Group elements = new Group(); // Contains all scene elements
		Scene scene = new Scene(elements, 800, 400);
        BorderPane border = new BorderPane(); // VBox, black rectangles, and central elements
		VBox layout = new VBox(); // Side text and buttons
        
        // Used for background imagery
        Rectangle top = new Rectangle(); 
        Rectangle side = new Rectangle();
        
        
        // Set the elements to their proper sizes and positions
        top.setX(0); 
        //top.setY(0); 
        top.widthProperty().bind(scene.widthProperty()); 
        top.setHeight(75); 
        border.setTop(top);
        
        //side.setX(600); 
        side.setY(0); 
        side.setWidth(200); 
        side.heightProperty().bind(scene.heightProperty());
        //border.setRight(side);
        
        StartCSE360.iv.setX(10);
        StartCSE360.iv.setY(-3);
        StartCSE360.iv.setFitWidth(75);
        StartCSE360.iv.setFitHeight(75);
        //w5.setX(0);
    	
    	
    	
        Text userText = new Text("Select roles to be\nassigned to\n" + username);
        String role = databaseHelper.getUserRole(username);
        
        //admin button
        CheckBox adminbutton = new CheckBox("Admin");
        if (role.charAt(0) == 1) {
        	adminbutton.setSelected(true);
        }
        
        //student button
        CheckBox studentbutton = new CheckBox("Student");
        if (role.charAt(1) == 1) {
        	studentbutton.setSelected(true);
        }
       
        //reviewer button
        CheckBox reviewerbutton = new CheckBox("Reviewer");
        if (role.charAt(4) == 1) {
        	reviewerbutton.setSelected(true);
        }
        
        //instructor button
        CheckBox instructorbutton = new CheckBox("Instructor");
        if (role.charAt(2) == 1) {
        	instructorbutton.setSelected(true);
        }
        
        //staff button
        CheckBox staffbutton = new CheckBox("Staff");
        if (role.charAt(3) == 1) {
        	staffbutton.setSelected(true);
        }
        
        

        // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Button setupButton = new Button("Submit");
        
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            if (adminbutton.isSelected()) {newRole = newRole + "1";}else {newRole = newRole + "0";}
            if (studentbutton.isSelected()) {newRole = newRole + "1";} else {newRole = newRole + "0";}
            if (instructorbutton.isSelected()) {newRole = newRole + "1";} else {newRole = newRole + "0";}
            if (staffbutton.isSelected()) {newRole = newRole + "1";} else {newRole = newRole + "0";}
            if (reviewerbutton.isSelected()) {newRole = newRole + "1";} else {newRole = newRole + "0";}
            
            databaseHelper.updateRoles(username, newRole);
            new AdminModAccPage().show(databaseHelper, primaryStage);
        });
        
        // Added Labels userError and passError to layout
 		// They will display any errors present in the chosen UserName and Password
         layout = new VBox(10, adminbutton, studentbutton, reviewerbutton, instructorbutton, staffbutton, setupButton, errorLabel);
         layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
         layout.setAlignment(Pos.TOP_CENTER);
         
         //side.widthProperty().bind(layout.widthProperty());
         StackPane sp = new StackPane(side, userText);
         sp.setAlignment(Pos.TOP_CENTER);
         border.setRight(sp);
         border.setCenter(layout);
         elements.getChildren().addAll(border, StartCSE360.iv);
         primaryStage.setScene(scene);
         primaryStage.setTitle("Account Setup");
         primaryStage.show();
    }
}