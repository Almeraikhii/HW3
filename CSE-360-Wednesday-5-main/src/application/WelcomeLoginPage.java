package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;


/**
 * The WelcomeLoginPage class displays a welcome screen for authenticated users.
 * It allows users to navigate to their respective pages based on their role or quit the application.
 */
public class WelcomeLoginPage {
	
	private final DatabaseHelper databaseHelper;

    public WelcomeLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    public void show( Stage primaryStage, User user) {
    	
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
        
        
        Text userText = new Text("\nWelcome!!\n");
	    userText.setFont(StartCSE360.vrs);
	    userText.setFill(Color.WHITE);
	    //userText.setX(30);
	    //userText.setY(190);
	    //border.setCenter(userText);
    	
	    layout = new VBox(20, userText);
        layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
        layout.setAlignment(Pos.TOP_CENTER);
    	
	    // Button to navigate to the user's respective page based on their role
	    String role = databaseHelper.getUserRole(user.getUserName());
	    
	    System.out.println(role);
	    
	    if (role.charAt(0) == '1') {
	    	Button adminButton = new Button("Continue to admin page");
	    	adminButton.setOnAction(a -> {
	    		new AdminHomePage(databaseHelper).show(primaryStage);
	    	});
	    	layout.getChildren().add(adminButton);
	    }
	    if (role.charAt(1) == '1') {
	    	Button studentButton = new Button("Continue to student page");
	    	studentButton.setOnAction(a -> {
	    		new RoleStudentPage(databaseHelper).show(primaryStage);
	    	});
	    	layout.getChildren().add(studentButton);
	    }
	    if (role.charAt(2) == '1') {
	    	Button instructorButton = new Button("Continue to instructor page");
	    	instructorButton.setOnAction(a -> {
	    		new RoleInstructorPage(databaseHelper).show(primaryStage);
	    	});
	    	layout.getChildren().add(instructorButton);
	    }
	    if (role.charAt(3) == '1') {
	    	Button staffButton = new Button("Continue to staff page");
	    	staffButton.setOnAction(a -> {
	    		new RoleStaffPage(databaseHelper).show(primaryStage);
	    	});
	    	layout.getChildren().add(staffButton);
	    }
	    if (role.charAt(4) == '1') {
	    	Button reviewerButton = new Button("Continue to reviewer page");
	    	reviewerButton.setOnAction(a -> {
	    		new RoleReviewerPage(databaseHelper).show(primaryStage);
	    	});
	    	layout.getChildren().add(reviewerButton);
	    }
	    
	    // Button to quit the application
	    Button quitButton = new Button("Quit");
	    quitButton.setFont(StartCSE360.vrs);
	    quitButton.setOnAction(a -> {
	    	databaseHelper.closeConnection();
	    	Platform.exit(); // Exit the JavaFX application
	    });
     	
     	StackPane sp = new StackPane(side, userText);
        sp.setAlignment(Pos.TOP_CENTER);
        border.setRight(sp);
        border.setCenter(layout);
        elements.getChildren().addAll(border, StartCSE360.iv);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome Page");
        primaryStage.show();
    }
}