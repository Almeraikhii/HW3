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

import EmailRecognizer.EmailRecognizer;
import databasePart1.*;
/**
 * SetupAccountPage class handles the account setup process for new users.
 * Users provide their userName, password, and a valid invitation code to register.
 */
public class SetupAccountPage {
	
    private final DatabaseHelper databaseHelper;
    private String selectedRole="";
    // DatabaseHelper to handle database operations.
    public SetupAccountPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    /**
     * Displays the Setup Account page in the provided stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
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
    	
    	
    	
        Text userText = new Text("Enter your information\nhere.\n\nPlease remember to\nselect a role!\n");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
    	
    	// Input fields for userName, password, and invitation code
        TextField userNameField = new TextField();
        
        userNameField.setPromptText("Enter userName");
        userNameField.setMaxWidth(250);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        TextField inviteCodeField = new TextField();
        inviteCodeField.setPromptText("Enter InvitationCode");
        inviteCodeField.setMaxWidth(250);
        
        TextField EmailField = new TextField();
        EmailField.setPromptText("Enter Your Email");
        EmailField.setMaxWidth(250);
        
        //initialize new radio button group
        ToggleGroup roleGroup = new ToggleGroup();
        
        //student button
        RadioButton studentbutton = new RadioButton("Student");
        studentbutton.setToggleGroup(roleGroup);
       
        //reviewer button
        RadioButton reviewerbutton = new RadioButton("Reviewer");
        reviewerbutton.setToggleGroup(roleGroup);
        
        //instructor button
        RadioButton instructorbutton = new RadioButton("Instructor");
        instructorbutton.setToggleGroup(roleGroup);
        
        //staff button
        RadioButton staffbutton = new RadioButton("Staff");
        staffbutton.setToggleGroup(roleGroup);
        
        studentbutton.setOnAction(e -> selectedRole = "01000");
        reviewerbutton.setOnAction(e -> selectedRole = "00001");
        instructorbutton.setOnAction(e -> selectedRole = "00100");
        staffbutton.setOnAction(e -> selectedRole = "00010");

        // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String code = inviteCodeField.getText();
            String email = EmailField.getText();
            
            try {
            	// Check if the user already exists
            	if(!databaseHelper.doesUserExist(userName)) {
            		
            		// Validate the username
            		String userErrMessage = UserNameRecognizer.checkForValidUserName(userName); 
            		if (userErrMessage.equals("UserName is valid\n")) {
            			// Validate the password
            			String passErrMessage = PasswordRecognizer.checkForValidPassword(password);
            			if (passErrMessage.equals("Password is valid\n")) {
            				//Validate the email
            				String emailErrMessage = EmailRecognizer.checkForValidEmail(email);
            				if (emailErrMessage.equals("E-mail is valid.\n")) {
			            		if(databaseHelper.validateInvitationCode(code)) {
			            			if (selectedRole == null || email.isEmpty()) {
			                            errorLabel.setText("Please enter a valid email and select a role.");
			                            }
			            			
			            			// Create a new user and register them in the database
					            	User user=new User(userName, password, selectedRole, email);
					            	StartCSE360.currentUser.SetUserName(userName);
					            	StartCSE360.currentUser.SetRole(selectedRole);
					                databaseHelper.register(user);
					                
					             // Navigate to the Welcome Login Page
					                new UserLoginPage(databaseHelper).show(primaryStage);
			            		}
			            		else {
			            			errorLabel.setText("Please enter a valid invitation code");
			            		}
            				}
            				else {
            					errorLabel.setText(emailErrMessage);
            				}
            			}
            			else {
            				errorLabel.setText("Password is invalid. \n" + passErrMessage);
            			}
            		}
            		else {
            			errorLabel.setText("Username is invalid. \n" + userErrMessage);
            		}
            	}
            	else {
            		errorLabel.setText("This userName is taken. Please use another to setup an account");
            	}
            	
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // Added Labels userError and passError to layout
 		// They will display any errors present in the chosen UserName and Password
         layout = new VBox(10, EmailField, userNameField, passwordField, inviteCodeField, studentbutton, reviewerbutton, instructorbutton, staffbutton, setupButton, errorLabel);
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