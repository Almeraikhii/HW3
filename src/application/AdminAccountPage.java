package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

public class AdminAccountPage {
	
    public void show(DatabaseHelper databaseHelper,Stage primaryStage) {
    	VBox layout = new VBox();
	    layout.setStyle("-fx-alignment: left; -fx-padding: 20;");
	    
	    // Label to display the title of the page
	    Label userLabel = new Label("Enter userName of the account you wish to modify:");
	    userLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
	    
	    TextField userNameField = new TextField();
	    userNameField.setPromptText("Enter userName here");
     	userNameField.setMaxWidth(250);
     	
     	Button deleteButton = new Button("Delete Account");
     	
     	Button modRolesButton = new Button("Modify Roles");
     	
     	Button generateOTPButton = new Button("Generate one-time password");
     	
     	Button backButton = new Button("Go back to Admin Page");
     	layout.getChildren().addAll(userLabel, userNameField, deleteButton, modRolesButton, generateOTPButton, backButton);
	    Scene inviteScene = new Scene(layout, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(inviteScene);
	    primaryStage.setTitle("Invite Page");
    }
    
    
}
