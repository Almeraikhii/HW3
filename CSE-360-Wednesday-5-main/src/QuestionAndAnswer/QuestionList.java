package QuestionAndAnswer;

import java.util.ArrayList;
import databasePart1.DatabaseHelper;

public class QuestionList {
	
	private static DatabaseHelper databaseHelper;
	private ArrayList<Question> questionList;
	
	/**
	 * This function reads the question data from a text file and gets the next question id
	 * for the next question to be creates
	 */
	public QuestionList(DatabaseHelper databaseHelper){
		this.databaseHelper = databaseHelper;
		this.questionList = new ArrayList<Question>();		
	}
	
	//this function calls the QuestionSearcher to use a keyword for the searching feature
	public ArrayList<Question> searching_questions(String userSearch) {
    		String keyword = QuestionSearcher.finding_Keyword(userSearch);

   		 if (keyword == null) {
        		return null;
    }

    		ArrayList<Question> similar_questions = new ArrayList<>();

    		for (Question current_question : getAllQuestions()) {
        	if (current_question.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
            current_question.getContents().toLowerCase().contains(keyword.toLowerCase())) {
            similar_questions.add(current_question);
        }
    }

    return similar_questions;
}

	
	// this function returns a specific question
	public Question getQuestion(int id) {
		return databaseHelper.getQuestionByID(id);
	}
	
	// this function returns all of the questions
	public ArrayList<Question> getAllQuestions() {
		questionList = databaseHelper.loadQuestions();
		return questionList;
	}
	
	// this functions returns all of the questions by a specific user
	public ArrayList<Question> getAllQuestionsByUser(String userName) {
		questionList = databaseHelper.getQuestionsByUser(userName);
		return questionList;
	}
	
	//this funciton returns all unresolved questions for a specific user
	public ArrayList<Question> getUnresolvedQuestionsByUser(String userName) {
		questionList = databaseHelper.getQuestionsByUser(userName);
		ArrayList<Question> subsetList = new ArrayList<Question>();
		for(Question question : questionList)
		{
			if(!question.getResolved())
				subsetList.add(question);
		}
		return subsetList;
	}
	
	//this funciton returns all unresolved questions
	public ArrayList<Question> getUnresolvedQuestions() {
		questionList = databaseHelper.loadQuestions();
		ArrayList<Question> subsetList = new ArrayList<Question>();
		for(Question question : questionList)
		{
			if(!question.getResolved())
				subsetList.add(question);
		}
		return subsetList;
	}
	
	// adds a question to the list and stores it
	public boolean addQuestion(String title, String contents, String userName, boolean anonymous) {
		if(title.length() < 10) {
			System.out.println("Error! Title must be at least 10 characters.");
			return false;
		}
		else if(title.length() > 200) {
			System.out.println("Error! Title must be no more than 200 characters.");
			return false;
		}
		else if(contents.length() < 10) {
			System.out.println("Error! Contents must be at least 10 characters.");
			return false;
		}
		else if(contents.length() > 5000) {
			System.out.println("Error! Contents must be no more than 5000 characters.");
			return false;
		}
		databaseHelper.postQuestion(userName, title, contents, anonymous);
		return true;
	}
	
	/**
	 * This function edits a specific question
	 * @param id
	 * @return true if edited, false if error
	 */
	public boolean editQuestion(int id, String title, String contents, boolean anonymity, String userName) {
		if(title.length() < 10) {
			System.out.println("Error! Title must be at least 10 characters.");
			return false;
		}
		else if(title.length() > 200) {
			System.out.println("Error! Title must be no more than 200 characters.");
			return false;
		}
		if(contents.length() < 10) {
			System.out.println("Error! Contents must be at least 10 characters.");
			return false;
		}
		else if(contents.length() > 5000) {
			System.out.println("Error! Contents must be no more than 5000 characters.");
			return false;
		}
		Question toBeEdited = databaseHelper.getQuestionByID(id);
		// if username does not match creator
		if(!toBeEdited.getUserName().equals(userName))
		{
			System.out.println("Error! You are not authorized to edit this question!");
			return false;
		}
		databaseHelper.editQuestion(title, contents, anonymity, id);;
		return true;
	}
	
	/**
	 * deletes a question from the list
	 * @param q
	 * @return true if deleted, false if there is an error
	 */
	public boolean deleteQuestion(int id, String userName) {
		Question toBeEdited = databaseHelper.getQuestionByID(id);
		// if username does not match creator
		if(!toBeEdited.getUserName().equals(userName))
		{
			System.out.println("Error! You are not authorized to delete this question!");
			return false;
		}
		databaseHelper.deleteQuestion(id);
		return true;
	}
	
	// marks a question as resolved if an answer was marked as helpful
	public boolean markResolved(int id, String userName) {
		Question toBeEdited = databaseHelper.getQuestionByID(id);
		// if username does not match creator
		if(!toBeEdited.getUserName().equals(userName))
		{
			System.out.println("Error! You are not authorized to resolve this question!");
			return false;
		}
		if(!toBeEdited.getResolved())
			databaseHelper.resolveQuestion(id);
		return true;
	}

}
