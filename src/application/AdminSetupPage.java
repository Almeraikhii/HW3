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
 * The SetupAdmin class handles the setup process for creating an administrator account.
 * This is intended to be used by the first user to initialize the system with admin credentials.
 */
public class AdminSetupPage {
	
    private final DatabaseHelper databaseHelper;

    public AdminSetupPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
    	
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
        
        
        Text userText = new Text("Enter your username\nand password here.\n\nMake sure you can\nremember them both!\n");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
	    //userText.setX(30);
	    //userText.setY(190);
	    //border.setCenter(userText);
        
    	
    	// Input fields for userName and password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter Admin userName");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);

        Button setupButton = new Button("Setup");
        
        // New labels, used to display errors in UserNames and Passwords
        Label userError = new Label();
        Label passError = new Label();
        
        
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            
            
            
            
            // Check UserName and Password to confirm that they are valid
            String userNameError = UserNameRecognizer.checkForValidUserName(userName);
            String passwordError = PasswordRecognizer.checkForValidPassword(password);
                        
            if (userNameError.equals("UserName is valid\n") && passwordError.equals("Password is valid\n")) {
          	// Only register the user if they have a valid UserName and Password
            try {
            	// Create a new User object with admin role and register in the database
            	
            	User user = new User(userName, password, "10000" , "");
            	
            	// Update data structure
            	StartCSE360.currentUser.SetUserName(userName);
            	StartCSE360.currentUser.SetRole("10000");

            	
                databaseHelper.register(user);
                System.out.println("Administrator setup completed.");
                
                // Navigate to the Welcome Login Page
                new UserLoginPage(databaseHelper).show(primaryStage);
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
        	// Display error found in UserName
        	if (!(userNameError.equals("UserName is valid\n"))) {
        		userError.setText(userNameError);
        		userError.setTextFill(Color.RED);
        		userError.setAlignment(Pos.BASELINE_LEFT);
        		
        	// Confirm that the UserName is valid (Password errors will be displayed below this)
        	} else {
        		userError.setText(userNameError);
        		userError.setTextFill(Color.GREEN);
        		userError.setAlignment(Pos.BASELINE_LEFT);
        	}
        	
        	// Display errors found in Password
        	if (!(passwordError.equals("Password is valid\n"))) {
        		passError.setText(passwordError);
        		passError.setTextFill(Color.RED);
        		passError.setAlignment(Pos.BASELINE_LEFT);
        		
        	// Confirm that the Password is valid (UserName errors will be displayed above this) 
        	} else {
        		passError.setText(passwordError);
        		passError.setTextFill(Color.GREEN);
        		passError.setAlignment(Pos.BASELINE_LEFT);
        	}
        	
        }
        });

        
		// Added Labels userError and passError to layout
		// They will display any errors present in the chosen UserName and Password
        layout = new VBox(10, userNameField, passwordField, setupButton, userError, passError);
        layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
        layout.setAlignment(Pos.TOP_CENTER);
        
        //side.widthProperty().bind(layout.widthProperty());
        StackPane sp = new StackPane(side, userText);
        sp.setAlignment(Pos.TOP_CENTER);
        border.setRight(sp);
        border.setCenter(layout);
        elements.getChildren().addAll(border, StartCSE360.iv);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Administrator Setup");
        primaryStage.show();
    }
}
