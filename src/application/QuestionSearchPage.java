package application;

import java.sql.SQLException;
import java.util.ArrayList;

import QuestionAndAnswer.Question;
import QuestionAndAnswer.QuestionList;
import databasePart1.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuestionSearchPage {
private final DatabaseHelper databaseHelper;
private ListView<String> questionListView = new ListView<>();
	
	public QuestionSearchPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
	public void show (Stage primaryStage) {
		QuestionList q1 = new QuestionList(databaseHelper);
		q1.getAllQuestions();
		
		questionListView.getItems().clear();
		BorderPane main = new BorderPane(); //contains the list of questions and question data
		VBox mainView = new VBox(); //where the question data is displayed
		VBox searchForm = new VBox(); //screen where user can search by keywords
		HBox userOptions = new HBox(); //contains buttons edit and delete buttons
    	
    	String username = StartCSE360.currentUser.GetUserName();
        String role = StartCSE360.currentUser.GetRole();
    	
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
        
		Font vr = new Font(20);
		Font vrs = StartCSE360.vrs;
		
        
		TextField SearchField = new TextField();
		SearchField.setPromptText("Enter keyword(s) separated by a space");
		SearchField.setMaxWidth(500);
		
		Label errorLabel = new Label("");
		
		Button submitButton = new Button("Submit");
		submitButton.setFont(StartCSE360.vrs);
		submitButton.setOnAction(a -> {
			if (SearchField.getLength() > 3 && SearchField.getLength() <= 100) {
				//public ArrayList<Question> searching_questions(String userSearch)
				ArrayList<Question> keywordSet = q1.searching_questions(SearchField.getText());
				updateList(keywordSet);
				
				
			}
			else if (SearchField.getLength() < 4) {
				errorLabel.setText("Keyword Field must be at least 4 characters.");
			}
			else {
				errorLabel.setText("Keyword Field cannot be over 100 characters.");
			}
			
			main.setCenter(mainView);
		});
		searchForm.getChildren().addAll(SearchField, errorLabel, submitButton);
	
		
		
		
		
        questionListView.setOnMouseClicked(a -> {
			mainView.getChildren().clear();
			userOptions.getChildren().clear();
			
			Question selectedQ = databaseHelper.getQByTitle(questionListView.getSelectionModel().getSelectedItem());
			
			Label titleLabel = new Label(selectedQ.getTitle() + " #" + selectedQ.getID());
			Label userNameLabel = new Label("");
			if (selectedQ.getAnonymity()) {
				userNameLabel.setText("Anonymous");
			}
			else {
				userNameLabel.setText(selectedQ.getUserName());
			}
			Label contentsLabel = new Label(selectedQ.getContents());
			
			mainView.getChildren().addAll(titleLabel,  userNameLabel, contentsLabel);
			if (username.equals(selectedQ.getUserName())) {
				Button editButton = new Button("Edit");
				
				Button deleteButton = new Button("Delete");

				userOptions.getChildren().addAll(editButton, deleteButton);
				
				mainView.getChildren().add(userOptions);
			}
			main.setCenter(mainView);
		});
		
		main.setLeft(questionListView);
	    main.setCenter(mainView);
		
	    Button search = new Button("Start");
        search.setFont(StartCSE360.vrs);
        search.setOnAction(a -> {
        	SearchField.clear();
        	main.setCenter(searchForm);
        });
	    
		Button back = new Button("Go Back");
        back.setFont(StartCSE360.vrs);
        back.setOnAction(a -> {
            new RoleStudentPage(databaseHelper).show(primaryStage);
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(StartCSE360.vrs);
        logoutButton.setOnAction(a -> {
            new UserLoginPage(databaseHelper).show(primaryStage);
        });
        
        String l = "Hello, " + username + "!\nHere you can \nsearch for questions\n by keywords.\n";
	    Text label = new Text(l);
	    label.setFont(vrs);
        label.setFill(Color.WHITE);
        

	    rightSide.getChildren().addAll(label, search, back, logoutButton);
	    rightSide.setAlignment(Pos.TOP_CENTER);
	    StackPane sp = new StackPane(side, rightSide);
	    sp.setAlignment(Pos.TOP_RIGHT);
	    border.setTop(top);
	    border.setRight(sp);
	    border.setLeft(main);
	    
	    
	    elements.getChildren().addAll(border, iv);
	    // Set the scene to primary stage
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Search Page");
	    primaryStage.show();
	}
	
	public void updateList(ArrayList<Question> qList) {
    	questionListView.getItems().clear();
		
		if (qList.size() > 0) {
			for (int i = 0; i < qList.size(); i++) {
				questionListView.getItems().add(qList.get(i).getTitle());
			}
		}
		else {
			questionListView.getItems().add("No questions fit your search.");
			questionListView.getItems().add("Hit start to search again.");
		}
		
		
    }
}
