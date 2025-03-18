package application;


import java.util.Optional;

import databasePart1.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import databasePart1.*;

/**
 * InvitePage class represents the page where an admin can generate an invitation code.
 * The invitation code is displayed upon clicking a button.
 */

public class AdminModAccPage {
	/**
	 *  Displays an alert asking to confirm deletion of user
	 *  
	 *  @param username username of account to be deleted
	 *  
	 *  Sources: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	 */
	private boolean deleteConfirmed(String username) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setTitle("Delete Account");
		alert.setHeaderText("Are you sure you want to delete user " + username + "?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			return true;
		}
		
		return false;
	}
	/**
     * Displays the Invite Page in the provided primary stage.
     * 
     * @param databaseHelper An instance of DatabaseHelper to handle database operations.
     * @param primaryStage   The primary stage where the scene will be displayed.
     */
	public void show(DatabaseHelper databaseHelper,Stage primaryStage) {
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
        
        Text userText = new Text("\nHere, you can modify\nand delete accounts.\n");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
	    //userText.setX(30);
	    //userText.setY(190);
	    //border.setCenter(userText);
	    
	    // Label to display the title of the page
	    Label userLabel = new Label("Enter username of the account you wish to modify:");
	    userLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
	    
	    // Label to display errors or statuses
	    Label errorLabel = new Label("");
	    errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	    
	    // textfield to enter the username the admin wants to modify
	    TextField userNameField = new TextField();
	    userNameField.setPromptText("Enter userName here");
     	userNameField.setMaxWidth(250);
     	
     	Button deleteButton = new Button("Delete Account");
	    deleteButton.setFont(StartCSE360.vrs);

     	deleteButton.setOnAction(a -> {
     		String user = userNameField.getText();
	     	if (databaseHelper.doesUserExist(user)){
     			if (deleteConfirmed(user)) {
	     			if (databaseHelper.deleteUser(user)) {
	     				errorLabel.setText("User " + user + " successfully deleted.");
	     			}
	     		}
     		}
	     	else {
	     		errorLabel.setText("User " + user + " does not exist.");
	     	}
     	});
     	
     	Button modRolesButton = new Button("Modify Roles");
	    modRolesButton.setFont(StartCSE360.vrs);

     	modRolesButton.setOnAction(a -> {
     		
     		String user = userNameField.getText();
	     	if (databaseHelper.doesUserExist(user)){	
	     		new ChangeRolesPage(databaseHelper).show(primaryStage, user);
	     	}
	     	else {
	     		errorLabel.setText("User " + user + " does not exist.");
	     	}
     	});
     	
     	Button generateOTPButton = new Button("Generate one-time password");
	    generateOTPButton.setFont(StartCSE360.vrs);

     	
     	Label OTPLabel = new Label("");
     	OTPLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
     	
     	generateOTPButton.setOnAction(a -> {
     		String user = userNameField.getText();
	     	if (databaseHelper.doesUserExist(user)){	
	     		String OTP = databaseHelper.generateOTPCode(user);
	     		OTPLabel.setText(OTP);
	     	}
	     	else {
	     		errorLabel.setText("User " + user + " does not exist.");
	     	}
     	});
     	
     	Button backButton = new Button("Go back to Admin Page");
	    backButton.setFont(StartCSE360.vrs);
     	backButton.setOnAction(a -> {
     		new AdminHomePage(databaseHelper).show(primaryStage);
     	});
     	
     	layout = new VBox(20, userLabel,userNameField, errorLabel, deleteButton, modRolesButton, generateOTPButton,OTPLabel, backButton );
        layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
        layout.setAlignment(Pos.TOP_CENTER);
     	
     	StackPane sp = new StackPane(side, userText);
        sp.setAlignment(Pos.TOP_CENTER);
        border.setRight(sp);
        border.setCenter(layout);
        elements.getChildren().addAll(border, StartCSE360.iv);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Account Modification");
        primaryStage.show();
    }
	
	
}