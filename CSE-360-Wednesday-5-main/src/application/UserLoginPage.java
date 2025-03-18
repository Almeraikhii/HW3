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

import java.sql.SQLException;

import PasswordRecognizer.PasswordRecognizer;
import databasePart1.*;

/**
 * The UserLoginPage class provides a login interface for users to access their accounts.
 * It validates the user's credentials and navigates to the appropriate page upon successful login.
 */
public class UserLoginPage {
	
    private final DatabaseHelper databaseHelper;

    public UserLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
    	// Input field for the user's userName, password
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
        
        
        Text userText = new Text("Welcome!\n\nEnter your username\nand password here.");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
	    //userText.setX(30);
	    //userText.setY(190);
	    //border.setCenter(userText);
        
    	
    	// Input fields for userName and password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter userName");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);

        Button loginButton = new Button("Setup");
        
        // New labels, used to display errors in UserNames and Passwords
        Label userError = new Label();
        Label passError = new Label();
        
        
        
        loginButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
           
            
            
            try {
            	User user=new User(userName, password, "", "");
            	StartCSE360.currentUser.SetUserName(userName);
            	if (databaseHelper.login(user)) {
            		String role = databaseHelper.getUserRole(userName);
                	StartCSE360.currentUser.SetRole(role);
                	
                	if(role.equals("10000")) {
                		new AdminHomePage(databaseHelper).show(primaryStage);
        	    	}
                	else if(role.equals("01000")) {
        	    		new RoleStudentPage(databaseHelper).show(primaryStage);
        	    	}
                	else if(role.equals("00001")) {
        	    		new RoleReviewerPage(databaseHelper).show(primaryStage);
        	    	}
                	else if(role.equals("00100")) {
        	    		new RoleInstructorPage(databaseHelper).show(primaryStage);
        	    	}
                	else if(role.equals("00010")) {
        	    		new RoleStaffPage(databaseHelper).show(primaryStage);
        	    	}
                	else {
                		WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper);
            			welcomeLoginPage.show(primaryStage,user);
            		}
            	}
            	else if (databaseHelper.OTPlogin(userName, password)) {
            		databaseHelper.deleteOTP(userName, password);
            		new ChangePasswordPage(databaseHelper).show(primaryStage, userName);
            	}
            	else {
            		userError.setText("User account doesn't exist");
                    userError.setTextFill(Color.GREEN);
            		userError.setAlignment(Pos.BASELINE_LEFT);
            	}
            	
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            } 
        });

     // Added Labels userError and passError to layout
     		// They will display any errors present in the chosen UserName and Password
             layout = new VBox(10, userNameField, passwordField, loginButton, userError, passError);
             layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
             layout.setAlignment(Pos.TOP_CENTER);
             
             //side.widthProperty().bind(layout.widthProperty());
             StackPane sp = new StackPane(side, userText);
             sp.setAlignment(Pos.TOP_CENTER);
             border.setRight(sp);
             border.setCenter(layout);
             elements.getChildren().addAll(border, StartCSE360.iv);
             primaryStage.setScene(scene);
             primaryStage.setTitle("User Login");
             primaryStage.show();
    }
}
