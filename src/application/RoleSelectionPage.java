package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import databasePart1.*;

/**
 * The WelcomeLoginPage class displays a welcome screen for authenticated users.
 * It allows users to navigate to their respective pages based on their role or quit the application.
 */
public class RoleSelectionPage {
	
	private final DatabaseHelper databaseHelper;

    public RoleSelectionPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    public void show( Stage primaryStage, User user) {
    	
    	VBox layout = new VBox(5);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    Label welcomeLabel = new Label("Welcome!!");
	    welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    
	    
	    Button studentButton = new Button("student");
        Button reviewerButton = new Button("reviewer");
        Button instructorButton = new Button("instructor");
        Button staffButton = new Button("staff");
        Button adminButton = new Button("admin");
        
        studentButton.setOnAction(e -> {
            new RoleStudentPage(databaseHelper).show(primaryStage);
        });
        reviewerButton.setOnAction(e -> {
            new RoleReviewerPage(databaseHelper).show(primaryStage);
        });
        instructorButton.setOnAction(e -> {
            new RoleInstructorPage(databaseHelper).show(primaryStage);
        });
        staffButton.setOnAction(e -> {
            new RoleStaffPage(databaseHelper).show(primaryStage);
        });
        adminButton.setOnAction(e -> {
            new AdminHomePage(databaseHelper).show(primaryStage);
        });
	    
	   
	    // Button to quit the application
	    Button quitButton = new Button("Quit");
	    quitButton.setOnAction(a -> {
	    	databaseHelper.closeConnection();
	    	Platform.exit(); // Exit the JavaFX application
	    });
	    
	    // "Invite" button for admin to generate invitation codes
	    if ("admin".equals(user.getRole())) {
            Button inviteButton = new Button("Invite");
            inviteButton.setOnAction(a -> {
                new InvitationPage().show(databaseHelper, primaryStage);
            });
            layout.getChildren().add(inviteButton);
        }

	    layout.getChildren().addAll(welcomeLabel,studentButton, reviewerButton, instructorButton, adminButton, staffButton,quitButton);
	    Scene welcomeScene = new Scene(layout, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(welcomeScene);
	    primaryStage.setTitle("Role Selection");
    }
}