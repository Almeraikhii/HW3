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
public class ChangePasswordPage {
	
    private final DatabaseHelper databaseHelper;
    private String selectedRole=null;
    // DatabaseHelper to handle database operations.
    public ChangePasswordPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    /**
     * Displays the Setup Account page in the provided stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage, String user) {
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
    	
    	
    	
        Text userText = new Text("Enter your new\npassword!\n");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
    	
    	// Input fields for userName, password, and invitation code
        
        PasswordField passwordField1 = new PasswordField();
        passwordField1.setPromptText("Enter New Password");
        passwordField1.setMaxWidth(250);
        
        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Confirm New Password");
        passwordField2.setMaxWidth(250);
        
       

        // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String password1 = passwordField1.getText();
            String password2 = passwordField2.getText();
            
            if (password1.equals(password2)) {
				String passwordError = PasswordRecognizer.checkForValidPassword(password1);
				
				if (passwordError.equals("Password is valid\n")) {
					if(databaseHelper.changePassword(user, password1)) {
						errorLabel.setTextFill(Color.GREEN);
						errorLabel.setText("Password successfully changed.");
					}
				}
				else {
					errorLabel.setText("Invalid password!\n" + passwordError);
				}
			}
			else {
				errorLabel.setText("Passwords do not match!");
			}
        });
        
        // Added Labels userError and passError to layout
 		// They will display any errors present in the chosen UserName and Password
         layout = new VBox(10, passwordField1, passwordField2, setupButton, errorLabel);
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