package application;

import java.awt.Label;
import java.sql.SQLException;

import databasePart1.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FirstPage class represents the initial screen for the first user.
 * It prompts the user to set up administrator access and navigate to the setup screen.
 */
public class ListUserPage {
	
	// Reference to the DatabaseHelper for database interactions
	private final DatabaseHelper databaseHelper;
	public ListUserPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

	/**
     * Displays the first page in the provided primary stage. 
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage) {
    	
    	// Load logo and font from filesystem, throw exception if one fails
    	
        
        Group elements = new Group(); // Contains all scene elements
		Scene scene = new Scene(elements, 800, 400);
        BorderPane border = new BorderPane(); // VBox, black rectangles, and central elements
		VBox rightSide = new VBox(); // Side text and buttons
        
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
        border.setRight(side);
        
        StartCSE360.iv.setX(10);
        StartCSE360.iv.setY(-3);
        StartCSE360.iv.setFitWidth(75);
        StartCSE360.iv.setFitHeight(75);
        //w5.setX(0);
        
    	// Text to be seen in whitespace
//	    Text userText = new Text("Hello, you are the first person\nhere. Please select continue\nto setup admin access.\n");
//	    userText.setFont(StartCSE360.vr);
//	    //userText.setX(30);
//	    //userText.setY(190);
//	    border.setCenter(userText);
	    
	    Text listingLabel = new Text("List of All Users:");
	    
	    Text listOfUsers = new Text("");
		try {
			listOfUsers = new Text(databaseHelper.listUsers());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    VBox layout = new VBox(10, listingLabel, listOfUsers);
        layout.setStyle("-fx-padding: 20; -fx-alignment: right;");
        layout.setAlignment(Pos.TOP_CENTER);
        border.setCenter(layout);
	    // Button to navigate to the SetupAdmin page
	    Button backButton = new Button("Back");
	    //continueButton.setLayoutX(610);
        //continueButton.setLayoutY(200);
	    rightSide.getChildren().add(backButton);
	    rightSide.setAlignment(Pos.BASELINE_CENTER);
	    backButton.setPrefWidth(180);
	    backButton.setFont(StartCSE360.vrs);
        StackPane sp = new StackPane(side, rightSide);
        border.setRight(sp);
	    
	    backButton.setOnAction(a -> {
	        new AdminHomePage(databaseHelper).show(primaryStage);
	    });

	    // Set up scene with all background and interactive elements
	    elements.getChildren().addAll(border, StartCSE360.iv);
        
        
 		primaryStage.setScene(scene);
	    primaryStage.setTitle("First Page");
    	primaryStage.show();
    }
}