package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import databasePart1.*;

/**
 * The SetupLoginSelectionPage class allows users to choose between setting up a new account
 * or logging into an existing account. It provides two buttons for navigation to the respective pages.
 */
public class SetupLoginSelectionPage {
	
    private final DatabaseHelper databaseHelper;

    public SetupLoginSelectionPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
        
    	// Load logo and font from filesystem, throw exception if one fails
        Image w5 = StartCSE360.w5;;
        ImageView iv = new ImageView(w5);

		Font vr = StartCSE360.vr;
		Font vrs = StartCSE360.vrs;
		
    	// Establish containers for various UI elements
		BorderPane border = new BorderPane();
		VBox rightSide = new VBox();
		
    	
    	// Buttons to select Login / Setup options that redirect to respective pages
        Button setupButton = new Button("Create Account");
        Button loginButton = new Button("Sign In");
        
        setupButton.setOnAction(a -> {
            new SetupAccountPage(databaseHelper).show(primaryStage);
        });
        loginButton.setOnAction(a -> {
        	new UserLoginPage(databaseHelper).show(primaryStage);
        });
        
        Group elements = new Group(); // Contains all scene elements
        Scene scene = new Scene(elements, 800, 400);
        
        // Set the position, width, color, and font of each button appropriately
        //setupButton.setLayoutX(scene.getWidth()-190);
        //setupButton.setLayoutY(150);
        setupButton.setPrefWidth(180);
        setupButton.setFont(vrs);
        //setupButton.setStyle("-fx-background-color: #FFFFFF");


        //loginButton.setLayoutX(scene.getWidth()-190);
        //loginButton.setLayoutY(250);
        loginButton.setPrefWidth(180);
        loginButton.setFont(vrs);
        //loginButton.setStyle("-fx-background-color: #FFFFFF");

        
       
        
        
        
        
        // Used for black parts of background
        Rectangle top = new Rectangle(); 
        Rectangle side = new Rectangle();
        
        // Text to be viewed in whitespace
        Text welcome = new Text();
        welcome.setFont(vr);
        welcome.setText("Welcome to W5!\n\n");
        welcome.setX(scene.getWidth()/2 - 300);
        welcome.setY(scene.getHeight()/4);
        border.setCenter(welcome);
        
        // Text to display user's name and role. since they're not signed in, this will refer to them as "User"
        Text status = new Text("Hello, User!\nCreate an account\nor sign in.");
        status.setFont(vrs);
        //status.setX(scene.getWidth()-190);
        //status.setY(20);
        status.setFill(Color.WHITE);
        
        
        
        rightSide.getChildren().addAll(status, setupButton, loginButton);
        rightSide.setPrefWidth(200);
        rightSide.setSpacing(50);
        rightSide.setAlignment(Pos.TOP_CENTER);
        
        
        
        // Set the rectangles to their proper sizes and positions
        top.setX(0); 
        //top.setY(0); 
        top.widthProperty().bind(scene.widthProperty());
        top.setHeight(75); 
		border.setTop(top);
        
       // side.setX(scene.getWidth()-200);
        side.setY(0); 
        side.setWidth(200); 
        side.heightProperty().bind(scene.heightProperty());
        StackPane sp = new StackPane(side, rightSide);
        rightSide.setLayoutY(10);
        border.setRight(sp);
        
        
        // Set the W5 logo to the correct position and size
        iv.setX(10);
        iv.setY(-3);
        iv.setFitWidth(75);
        iv.setFitHeight(75);
        
        // Add everything to group, prepare to present to user
        elements.getChildren().addAll(border, iv);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Account Setup");
        primaryStage.show();
    }
}
