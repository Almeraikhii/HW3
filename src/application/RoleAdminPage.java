package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This page displays a simple welcome message for the user.
 */

public class RoleAdminPage {

    public void show(Stage primaryStage) {
    	
    	Group elements = new Group(); // Contains all scene elements
		Scene scene = new Scene(elements, 800, 400);
        BorderPane border = new BorderPane(); // VBox, black rectangles, and central elements
		VBox rightSide = new VBox(); // Side text and buttons
    	
    	// Used for black parts of background
        Rectangle top = new Rectangle(); 
        Rectangle side = new Rectangle();
        
        top.setX(0); 
        //top.setY(0); 
        top.widthProperty().bind(scene.widthProperty()); 
        top.setHeight(75); 
        
        side.setY(0); 
        side.setWidth(200); 
        side.heightProperty().bind(scene.heightProperty());
    	
    	
    	// Load logo and font from StartCSE360
        ImageView iv = new ImageView(StartCSE360.w5);

        iv.setX(10);
        iv.setY(-3);
        iv.setFitWidth(75);
        iv.setFitHeight(75);
        
		//Font vr = new Font(20);
		Font vrs = StartCSE360.vrs;
    	
	    
	    // label to display the welcome message for the user
        String username = StartCSE360.currentUser.GetUserName();
        String role = StartCSE360.currentUser.GetRole();
        String l = "Hello, " + username + "!\nYou're in admin \nmode right now.";
	    Text label = new Text(l);
	    label.setFont(vrs);
        label.setFill(Color.WHITE);
	    

	    rightSide.getChildren().addAll(label);
	    rightSide.setAlignment(Pos.TOP_CENTER);
	    StackPane sp = new StackPane(side, rightSide);
	    sp.setAlignment(Pos.TOP_RIGHT);
	    border.setTop(top);
	    border.setRight(sp);
	    
	    elements.getChildren().addAll(border, iv);
	    // Set the scene to primary stage
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Admin Page");
	    primaryStage.show();
    	
    }
}