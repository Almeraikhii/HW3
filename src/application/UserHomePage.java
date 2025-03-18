package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This page displays a simple welcome message for the user.
 */

public class UserHomePage {

    public void show(Stage primaryStage) {
    	// Used for black parts of background
        Rectangle top = new Rectangle(); 
        Rectangle side = new Rectangle();
    	
    	
    	// Load logo and font from filesystem, throw exception if one fails
        ImageView iv = new ImageView(StartCSE360.w5);

		
		//Font vr = new Font(20);
		Font vrs = StartCSE360.vrs;
		
		
		// Set the rectangles to their proper sizes and positions
        top.setX(0); 
        top.setY(0); 
        top.setWidth(800); 
        top.setHeight(75); 
        
        side.setX(600); 
        side.setY(0); 
        side.setWidth(200); 
        side.setHeight(600);
        
     // Set the W5 logo to the correct position and size
        iv.setX(10);
        iv.setY(-3);
        iv.setFitWidth(75);
        iv.setFitHeight(75);
        
        // label to display the welcome message for the admin
        String username = StartCSE360.currentUser.GetUserName();
        String role = StartCSE360.currentUser.GetRole();
        String label = "Hello, " + username + "!\nYou're in " + role + "\nmode right now.";
	    Text adminLabel = new Text(label);
	    adminLabel.setFont(vrs);
	    adminLabel.setX(615);
        adminLabel.setY(20);
        adminLabel.setFill(Color.WHITE);
	    
	    
	    
	    Group elements = new Group(top, side, iv, adminLabel);
	    Scene userScene = new Scene(elements, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(userScene);
	    primaryStage.setTitle("User Page");
    	
    }
}