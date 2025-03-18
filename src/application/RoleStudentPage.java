package application;

import java.sql.SQLException;
import java.util.ArrayList;

import QuestionAndAnswer.*;
import QuestionAndAnswer.SimilarQuestionSearch;
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

/**
 * This page displays a simple welcome message for the user.
 */

public class RoleStudentPage {
	private final DatabaseHelper databaseHelper;
	private ListView<String> questionListView = new ListView<>();

    public RoleStudentPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    public void show(Stage primaryStage) {
    	BorderPane main = new BorderPane(); //contains the list of questions and question data
		VBox mainView = new VBox(); //where the question data is displayed
		VBox questionForm = new VBox(); //screen where user can post questions
		VBox confirmForm = new VBox(); //screen where user sees a similar question and confirms that they want to post theirs
		VBox editForm = new VBox(); //screen where user can edit posts
		VBox answerForm = new VBox(); //screen where user can answer questions
		VBox privateAnswers = new VBox(); // List of private answers (viewable only to question poster)
		VBox resolveMenu = new VBox();
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
        
		//Font vr = new Font(20);
		Font vrs = StartCSE360.vrs;
    	
		//Setting up question form
		TextField TitleField = new TextField();
		TitleField.setPromptText("Enter Title");
		TitleField.setMaxWidth(250);
		
		TextField QuestionField = new TextField();
		QuestionField.setPromptText("Enter Question");
		QuestionField.setMaxWidth(250);
		QuestionField.setMaxHeight(50);
		
		CheckBox postAnon = new CheckBox("Post Anonymously?");
	    
		Label errorLabel = new Label("");
		
		Button submitButton = new Button("Submit");
		submitButton.setFont(StartCSE360.vrs);
		
		Button yesButton = new Button("Yes, post");
		submitButton.setFont(StartCSE360.vrs);
		
		Button noButton = new Button("No");
		submitButton.setFont(StartCSE360.vrs);
		
		
		
		
		
		submitButton.setOnAction(a -> {
			if (TitleField.getLength() > 0 && TitleField.getLength() < 100 && !databaseHelper.doesQuestionExist(TitleField.getText())) {
				if (QuestionField.getLength() > 0 && QuestionField.getLength() < 10000) {
					
					SimilarQuestionSearch qSearch = new SimilarQuestionSearch(databaseHelper);
					Question searchResult = qSearch.findSimilarQuestion(TitleField.getText());
					if(searchResult == null)
					{
						boolean isAnon;
						if (postAnon.isSelected()) {isAnon = true;} else {isAnon=false;}
						databaseHelper.postQuestion(username, TitleField.getText(), QuestionField.getText(), isAnon);
						errorLabel.setText("Question Posted.");
						updateList();
						main.setCenter(mainView);
					}
					else
					{
						main.setCenter(confirmForm);
						errorLabel.setText("A similar question has been found: \n" + searchResult.getTitle() 
						+ "\n" + searchResult.getContents() + "\nDo you wish to proceed with asking your question?");
						
						yesButton.setOnAction(b -> {
							confirmForm.getChildren().clear();
							boolean isAnon;
							if (postAnon.isSelected()) {isAnon = true;} else {isAnon=false;}
							databaseHelper.postQuestion(username, TitleField.getText(), QuestionField.getText(), isAnon);
							errorLabel.setText("Question Posted.");
							updateList();
						});
						
						noButton.setOnAction(c -> {
							mainView.getChildren().clear();
							new RoleStudentPage(databaseHelper).show(primaryStage);
						});
						
						confirmForm.getChildren().addAll(errorLabel, yesButton, noButton);
					}	
				}
				else if (QuestionField.getLength() == 0) {
					errorLabel.setText("Question Field cannot be blank.");
				}
				else {
					errorLabel.setText("Question Field cannot be over 10000 characters.");
				}
			}
			else if (databaseHelper.doesQuestionExist(TitleField.getText())) {
				errorLabel.setText("Question already exists.");
			}
			else if (TitleField.getLength() == 0) {
				errorLabel.setText("Title Field cannot be blank.");
			}
			else {
				errorLabel.setText("Title cannot be over 100 characters.");
			}
		});
		questionForm.getChildren().addAll(TitleField, QuestionField, postAnon, errorLabel, submitButton);
		
		//Default display for users
		
		updateList();
		
		questionListView.setOnMouseClicked(a -> {
			mainView.getChildren().clear();
			
			Question selectedQ = databaseHelper.getQByTitle(questionListView.getSelectionModel().getSelectedItem());
			
			Label titleLabel = new Label(selectedQ.getTitle() + " #" + selectedQ.getID());
			Label userNameLabel = new Label("");
			if (selectedQ.getAnonymity()) {
				userNameLabel.setText("Posted by Anonymous\n");
			}
			else {
				userNameLabel.setText("Posted by " + selectedQ.getUserName() + "\n");
			}
			Label contentsLabel = new Label(selectedQ.getContents());
			Label dateLabel = new Label(selectedQ.getDatePosted());
			
			Label numAnswers = new Label("");
			Label answersLabel = new Label("");
			
			// Find all public answers and display their count
			ArrayList<Answer> ans = databaseHelper.getAnswerByQuestion(selectedQ.getID());
			ArrayList<Answer> pubs = new ArrayList<Answer>();
			int size = 0;
			int pub = 0;
			
			if (!selectedQ.getResolved()) {
			
			if (ans != null) {
				size = ans.size();
				for (int i = 0; i < size; ++i) {
					if (!ans.get(i).getPrivate()) {
						++pub;
						pubs.add(ans.get(i));
					}
				}
				numAnswers.setText("\n" + pub + " public answers have been posted\n");
			} else {
				numAnswers.setText("\nNo answers have been posted yet\n");
			}
			
			String anses = "";
			for (int i = 0; i < pub; ++i) {
				anses += i+1 + ": " + pubs.get(i).getContents() + "\n";
			}
			
			answersLabel.setText(anses);
			
			} else {
				// Display the resolved answer first
				int resolvedCounter = 1;
				String resolution = "This question has been marked as resolved:";
				for (int i = 0; i < ans.size(); ++i) {
					System.out.println(i + " " + ans.get(i).getResolved() + " " + ans.get(i).getContents());
					if ((ans.get(i).getResolved()) && (!ans.get(i).getPrivate() || username.equals(selectedQ.getUserName()))) {
						resolution +=  "\n" + /*resolvedCounter +*/ ans.get(i).getContents();
						++resolvedCounter;
					}
				}
				answersLabel.setText(resolution);
				
			}
			
			mainView.getChildren().addAll(titleLabel, userNameLabel, dateLabel, contentsLabel, numAnswers, answersLabel);
			
			if (username.equals(selectedQ.getUserName())) {
				userOptions.getChildren().clear();
				Label privateAns = new Label("");
				String privateAnses = "\nHere are your private answers\n";
				int privs = 0;
				
				//Locate and display any private answers
				for (int i = 0; i < size; ++i) {
					if (ans.get(i).getPrivate()) {
						++privs;
						privateAnses += privs + ": " + ans.get(i).getContents();
					}
				}
				if (privs > 0) {
					privateAns.setText(privateAnses);
				}
				
			
				Button editButton = new Button("Edit");
				
				editButton.setOnAction(b -> { //Opens up menu to edit question
					editForm.getChildren().clear();
					
					TextField editTitle = new TextField(selectedQ.getTitle());
					editTitle.setMaxWidth(250);
					editTitle.setMaxHeight(50);
					
					TextField editQuestion = new TextField(selectedQ.getContents());
					editQuestion.setMaxWidth(250);
					editQuestion.setMaxHeight(50);
					
					boolean isAnon;
					if (postAnon.isSelected()) {isAnon = true;} else {isAnon=false;}
					
					Button saveButton = new Button("Save");
					saveButton.setOnAction(c -> {
						String newTitle = editTitle.getText();
						if(!newTitle.endsWith("(Edited)")) {
							newTitle += " (Edited)";
						}
						databaseHelper.editQuestion(newTitle, editQuestion.getText(), isAnon, selectedQ.getID());
						updateList();
						main.setCenter(mainView);
					});
					
					editForm.getChildren().addAll(editTitle, editQuestion, postAnon, saveButton);
					main.setCenter(editForm);
				});
				
				Button deleteButton = new Button("Delete");
				
				deleteButton.setOnAction(b -> {
					databaseHelper.deleteQuestion(selectedQ.getID());
					mainView.getChildren().clear();
					updateList();
				});
				
				Button flagResolved = new Button("Mark Resolved");
				
				flagResolved.setOnAction(f -> { //Select an answer to be marked correct
					resolveMenu.getChildren().clear();
					
					ArrayList<Answer> pots = databaseHelper.getAnswerByQuestion(selectedQ.getID());
					
					Label instruction = new Label("Here are all the potential answers.\nEnter the number of the one you want\nto mark as correct.\n");
					Label potentials = new Label("");
					String potentialsString = "";
					
					for (int i = 0; i < pots.size(); ++i) {
						potentialsString += "\n" + (i+1) + ": " + pots.get(i).getContents();
					}
					potentials.setText(potentialsString);
					
					TextField number = new TextField("Enter the number here");
					
					Button resolve = new Button("Submit");
					
					resolve.setOnAction(g -> {
						// set resolved status to 1 in the database
						//System.out.println(number.getText());
						//System.out.println(ans.get(Integer.parseInt(number.getText())-1).getID());
						databaseHelper.resolveAnswer(ans.get(Integer.parseInt(number.getText())-1).getID());
						databaseHelper.resolveQuestion(selectedQ.getID());
					});
					
					resolveMenu.getChildren().addAll(instruction, potentials, number, resolve);
					main.setCenter(resolveMenu);
				});

				userOptions.getChildren().addAll(editButton, deleteButton, flagResolved);
				
				mainView.getChildren().addAll(privateAns, userOptions);
			} else { // Question was created by someone else
				
				Button answerButton = new Button("Answer Question");
				userOptions.getChildren().clear();
				answerButton.setOnAction(b -> { //Opens up menu to write an answer
					System.out.println(selectedQ.getID());
					
					answerForm.getChildren().clear();
					
					TextField answerField = new TextField("Type answer here");
					answerField.setMaxWidth(250);
					answerField.setMaxHeight(50);
					
					CheckBox anon = new CheckBox("Answer is anonymous");
					CheckBox privately = new CheckBox("Answer is visible only to the quesiton's poster");
					
					Button submit = new Button("Submit");
					
					submit.setOnAction(c -> {
						Boolean anonymous = false;
						Boolean priv = false;
						if (anon.isSelected()) {
							anonymous = true;
						}
						if (privately.isSelected()) {
							priv = true;
						}
						String answer = answerField.getText();
						
						//TODO This throws an exception. I need to fix it
						ArrayList<Answer> answers = databaseHelper.getAnswerByQuestion(selectedQ.getID());
						int ID = 1;
						if (answers != null) {
							ID = answers.size() + 1;
						}
						databaseHelper.postAnswer(selectedQ.getID(), ID, username, answer, anonymous, priv);
						updateList();
						main.setCenter(mainView);
					});
					
					
					answerForm.getChildren().addAll(answerField, anon, privately, submit);
					main.setCenter(answerForm);
				});
				
				
				userOptions.getChildren().addAll(answerButton);
				mainView.getChildren().add(userOptions);

				
			}
			
			main.setCenter(mainView);
		});
		
		main.setLeft(questionListView);
	    main.setCenter(mainView);
		
	    // label to display the welcome message for the user
        
        String l = "Hello, " + username + "!\nYou're in Student\nmode right now.";
	    Text label = new Text(l);
	    label.setFont(vrs);
        label.setFill(Color.WHITE);
        
        Button askButton = new Button("Ask");
        askButton.setFont(StartCSE360.vrs);
        askButton.setOnAction(a -> {
        	TitleField.clear();
        	QuestionField.clear();
        	main.setCenter(questionForm);
        });
        
        Button searchButton = new Button("Search");
        searchButton.setFont(StartCSE360.vrs);
        searchButton.setOnAction(a -> {
            new QuestionSearchPage(databaseHelper).show(primaryStage);
        });
        
	    
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(StartCSE360.vrs);
        logoutButton.setOnAction(a -> {
            new UserLoginPage(databaseHelper).show(primaryStage);
        });

	    rightSide.getChildren().addAll(label, logoutButton, askButton, searchButton);
	    rightSide.setAlignment(Pos.TOP_CENTER);
	    StackPane sp = new StackPane(side, rightSide);
	    sp.setAlignment(Pos.TOP_RIGHT);
	    border.setTop(top);
	    border.setRight(sp);
	    border.setLeft(main);
	    
	    elements.getChildren().addAll(border, iv);
	    // Set the scene to primary stage
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Student Page");
	    primaryStage.show();
    	
    }
    
    public void updateList() {
    	ArrayList<Question> listOfQs = new ArrayList<Question>();
    	questionListView.getItems().clear();
		listOfQs = databaseHelper.loadQuestions();
		
		if (listOfQs.size() > 0) {
			for (int i = 0; i < listOfQs.size(); i++) {
				questionListView.getItems().add(listOfQs.get(i).getTitle());
			}
		}
		else {
			questionListView.getItems().add("There are currently no questions.");
		}
		
		
    }
}
