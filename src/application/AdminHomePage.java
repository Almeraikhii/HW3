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


import databasePart1.*;

/**
 * AdminPage class represents the user interface for the admin user.
 * This page displays a simple welcome message for the admin.
 */

public class AdminHomePage {
	/**
     * Displays the admin page in the provided primary stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
private final DatabaseHelper databaseHelper;
	
	public AdminHomePage(DatabaseHelper databaseHelper) {
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
        
        String username = StartCSE360.currentUser.GetUserName();
        //String role = StartCSE360.currentUser.GetRole();
        
        
        Text userText = new Text("\nHello, " + username +  "!\nYou're in admin\nmode right now.\n");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
	    //userText.setX(30);
	    //userText.setY(190);
	    //border.setCenter(userText);
        
        // label to display the welcome message for the admin
	   
	    Button inviteButton = new Button("Invite a User");
	    inviteButton.setFont(StartCSE360.vrs);
        inviteButton.setOnAction(a -> {
            new InvitationPage().show(databaseHelper, primaryStage);
        });
        
        Button accountButton = new Button("Modify an Account");
	    accountButton.setFont(StartCSE360.vrs);
        accountButton.setOnAction(a -> {
            new AdminModAccPage().show(databaseHelper, primaryStage);
        });
	    
        Button listButton = new Button("List all users");
        listButton.setFont(StartCSE360.vrs);
        listButton.setOnAction(a -> {
            new ListUserPage(databaseHelper).show(primaryStage);
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(StartCSE360.vrs);
        logoutButton.setOnAction(a -> {
            new UserLoginPage(databaseHelper).show(primaryStage);
        });
        
        layout = new VBox(20, inviteButton, accountButton, listButton, logoutButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
        layout.setAlignment(Pos.TOP_CENTER);
     	
        VBox rightSide = new VBox(userText, logoutButton);
        rightSide.setAlignment(Pos.TOP_CENTER);
     	StackPane sp = new StackPane(side, rightSide);
        sp.setAlignment(Pos.TOP_CENTER);
        border.setRight(sp);
        border.setCenter(layout);
        elements.getChildren().addAll(border, StartCSE360.iv);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Home Page");
        primaryStage.show();
    	
    }
}