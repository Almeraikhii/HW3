package QuestionAndAnswer;

import java.util.ArrayList;
import databasePart1.DatabaseHelper;

public class AnswerList {
	
	private static DatabaseHelper databaseHelper;
	private ArrayList<Answer> answerList;
	
	/**
	 * This function reads the answer data from a text file and gets the next answer id
	 * for the next answer to be creates
	 */
	public AnswerList(DatabaseHelper databaseHelper){
		this.databaseHelper = databaseHelper;
		this.answerList = new ArrayList<Answer>();
	}
	
	// returns all answers to a specific question
	public ArrayList<Answer> getAnswersByQuestion(int id) {
		answerList = databaseHelper.getAnswerByQuestion(id);
		return answerList;
	}
	
	// adds an answer to the list and stores it
	public boolean addAnswer(int questionID, int answerID, String contents, String userName, boolean anonymous, boolean isPrivate) {
		if(contents.length() < 10) {
			System.out.println("Error! Contents must be at least 10 characters.");
			return false;
		}
		else if(contents.length() > 5000) {
			System.out.println("Error! Contents must be no more than 5000 characters.");
			return false;
		}
		databaseHelper.postAnswer(questionID, answerID, userName, contents, anonymous, isPrivate);
		return true;
	}
	
	/**
	 * This function edits a specific answer
	 * @param id
	 * @return true if edited, false if error
	 */
	public boolean editAnswer(int id, String contents, boolean anonymity, String userName) {
		if(contents.length() < 10) {
			System.out.println("Error! Contents must be at least 10 characters.");
			return false;
		}
		else if(contents.length() > 5000) {
			System.out.println("Error! Contents must be no more than 5000 characters.");
			return false;
		}
		Answer toBeEdited = databaseHelper.getAnswerByID(id);
		// if username does not match creator
		if(!toBeEdited.getUserName().equals(userName))
		{
			System.out.println("Error! You are not authorized to edit this answer!");
			return false;
		}
		databaseHelper.editAnswer(contents, anonymity, id);
		return true;
	}
	
	/**
	 * deletes an answer from the list
	 * @param q
	 * @return true if deleted, false if there is an error
	 */
	public boolean deleteAnswer(int id, String userName) {
		Answer toBeEdited = databaseHelper.getAnswerByID(id);
		// if username does not match creator
		if(!toBeEdited.getUserName().equals(userName))
		{
			System.out.println("Error! You are not authorized to delete this answer!");
			return false;
		}
		databaseHelper.deleteAnswer(id);
		return true;
	}
	
	// marks an answer as resolved if an answer was marked as helpful
	public boolean markResolved(int id, String userName) {
		Answer toBeEdited = databaseHelper.getAnswerByID(id);
		Question question = databaseHelper.getQuestionByID(toBeEdited.getQuestionID());
		// if answer does not match creator
		if(!question.getUserName().equals(userName))
		{
			System.out.println("Error! You are not authorized to resolve this answer!");
			return false;
		}
		if(!toBeEdited.getResolved())
		{
			databaseHelper.resolveAnswer(id);
			databaseHelper.resolveQuestion(toBeEdited.getQuestionID());
		}
		return true;
	}
}
