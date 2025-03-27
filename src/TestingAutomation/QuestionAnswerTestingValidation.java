package TestingAutomation;

import java.sql.SQLException;
import java.util.ArrayList;

import QuestionAndAnswer.*;
import databasePart1.DatabaseHelper;

public class QuestionAnswerTestingValidation {
	
	private static DatabaseHelper databaseHelper = new DatabaseHelper();
	private static QuestionList ql;
	private static AnswerList al;
	private static ArrayList<Question> questions;
	private static ArrayList<Answer> answers;
	private static int testNo = 1;
	
	// empties database and creates test questions
	private static void setUpQuestionAndAnswers()
	{
		databaseHelper = new DatabaseHelper();
		try {
			databaseHelper.connectToDatabase();
			ql = new QuestionList(databaseHelper);
			al = new AnswerList(databaseHelper);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		ql.addQuestion("This is my question?", "This is a sample question for testing purposes", "poster1", false);
		ql.addQuestion("This is my other question?", "This is the second sample question for testing purposes", "poster2", false);
		al.addAnswer(1, -1, "This is a sample response to a question", "poster1", false, false);
		al.addAnswer(1, -1, "This is a sample response to a question", "poster2", false, false);
	}
	
	// interprets what the result vs expected result means, and returns true if success
	private static boolean printResult(boolean result, boolean expResult, String operation)
	{
		if(result && expResult)
		{
			System.out.println("\nSuccess! The test " + operation + " was valid, and it was expected to be!");
			return true;
		}
		else if(!result && expResult)
		{
			System.out.println("\nFail! The test " + operation + " was invalid, but it was expected to be!");
			return false;
		}
		else if(result && !expResult)
		{
			System.out.println("\nFail! The test " + operation + " was valid, but it was not supposed to be!");
			return false;
		}
		else if(!result && !expResult)
		{
			System.out.println("\nSuccess! The test " + operation + " was invalid, and it was expected to be invalid!");
			return true;
		}
		return false;
	}
	
	// prints questions for debugging
	private static void printQuestions(boolean before)
	{
		System.out.printf("\n------------------------------Questions %s:------------------------------\n", (before ? "Before" : "After"));
		questions = ql.getAllQuestions();
		for(Question q : questions)
			System.out.println(q.toString() + "\n");
	}
	
	
	// prints answers for debugging
	private static void printAnswers(boolean before)
	{
		System.out.printf("\n------------------------------Answers %s:------------------------------\n", (before ? "Before" : "After"));
		answers = al.getAnswersByQuestion(1);
		for(Answer a : answers)
			System.out.println(a.toString() + "\n");
	}
	
	// attempts to post a question, returns true if success
	private static boolean testPostQuestion(String userName, String title, String contents, boolean expectedResult)
	{
		System.out.printf("\n\nTest %s:\n", testNo);
		testNo++;
		setUpQuestionAndAnswers();
		printQuestions(true);
		boolean result = ql.addQuestion(title, contents, userName, false);
		printQuestions(false);
		return printResult(result, expectedResult, "post");
	}
	
	// attempts to post an answer, returns true if success
	private static boolean testPostAnswer(String userName, String contents, boolean expectedResult)
	{
		System.out.printf("\n\nTest %s:\n", testNo);
		testNo++;
		setUpQuestionAndAnswers();
		printQuestions(true);
		boolean result = al.addAnswer(1, -1, contents, userName, false, false);
		printQuestions(false);
		return printResult(result, expectedResult, "post");
	}
	
	// attempts to edit a question, returns true if success
	private static boolean testEditQuestion(String userName, String title, String contents, boolean expectedResult)
	{
		System.out.printf("\n\nTest %s:\n", testNo);
		testNo++;
		setUpQuestionAndAnswers();
		printQuestions(true);
		boolean result = ql.editQuestion(1, title, contents, expectedResult, userName);
		printQuestions(false);
		return printResult(result, expectedResult, "edit");
	}
	
	// attempts to edit an answer, returns true if success
	private static boolean testEditAnswer(String userName, String contents, boolean expectedResult)
	{
		System.out.printf("\n\nTest %s:\n", testNo);
		testNo++;
		setUpQuestionAndAnswers();
		printAnswers(true);
		boolean result = al.editAnswer(1, contents, expectedResult, userName);
		printAnswers(false);
		return printResult(result, expectedResult, "edit");
	}
	
	// attempts to delete a question, returns true if success
	private static boolean testDeleteQuestion(String userName, boolean expectedResult)
	{
		System.out.printf("\n\nTest %s:\n", testNo);
		testNo++;
		setUpQuestionAndAnswers();
		printQuestions(true);
		boolean result = ql.deleteQuestion(1, userName);
		printQuestions(false);
		return printResult(result, expectedResult, "deletion");
	}
	
	// attempts to delete an answer, returns true if success
	private static boolean testDeleteAnswer(String userName, boolean expectedResult)
	{
		System.out.printf("\n\nTest %s:\n", testNo);
		testNo++;
		setUpQuestionAndAnswers();
		printAnswers(true);
		boolean result = al.deleteAnswer(1, userName);
		printAnswers(false);
		return printResult(result, expectedResult, "deletion");
	}

	public static void main(String[] args) {
		String tooLong = "";
		for(int i = 0; i < 5001; i++)
			tooLong += "a";
		ArrayList<Boolean> results = new ArrayList<Boolean>();
		// test 1: ensure that posting questions works
		results.add(testPostQuestion("poster1", "This is the new question to be added!", "These are the contents of the new question", true));
		// test 2: questions must have at least 10 chars in title
		results.add(testPostQuestion("poster1", "short", "These are the contents of the new question", false));
		// test 3: question must have at least 10 chars in contents
		results.add(testPostQuestion("poster1", "This is the new question to be added!", "short", false));
		// test 4: question must not have more than 200 chars in title
		results.add(testPostQuestion("poster1", tooLong, "These are the contents of the new question", false));
		// test 5: question must not have more than 5000 chars in contents
		results.add(testPostQuestion("poster1", "This is the new question to be added!", tooLong, false));
		// test 6: ensure that posting answers works
		results.add(testPostAnswer("poster1", "These are the contents of the new question", true));
		// test 7: answers must have at least 10 chars in contents
		results.add(testPostAnswer("poster1", "short", false));
		// test 8: answers must not have more than 5000 chars in contents
		results.add(testPostAnswer("poster1", tooLong, false));
		// test 9: ensure that someone can not edit another's question
		results.add(testEditQuestion("poster2", "This is a new title", "These are new contents", false));
		// test 10: ensure that a user can edit their question
		results.add(testEditQuestion("poster1", "This is a new title", "These are new contents", true));
		// test 11: a user can't edit their question title to have less than 10 chars
		results.add(testEditQuestion("poster1", "short", "These are new contents", false));
		// test 12: a user can't edit their question title to have more than 200 chars
		results.add(testEditQuestion("poster1", tooLong, "These are new contents", false));
		// test 13: a user can't edit their question contents to have less than 10 chars
		results.add(testEditQuestion("poster1", "This is a new title", "short", false));
		// test 14: a user can't edit their question contents to have more than 5000 chars
		results.add(testEditQuestion("poster1", "This is a new title", tooLong, false));
		// test 15: ensure that someone can not edit another's answer
		results.add(testEditAnswer("poster2", "These are new contents", false));
		// test 16: ensure that a user can edit their answer
		results.add(testEditAnswer("poster1", "These are new contents", true));
		// test 17: a user can't edit their answer contents to have less than 10 chars
		results.add(testEditAnswer("poster1", "short", false));
		// test 18: a user can't edit their answer contents to have more than 5000 chars
		results.add(testEditAnswer("poster1", tooLong, false));
		// test 19: a user can delete their question
		results.add(testDeleteQuestion("poster1", true));
		// test 20: a user can't delete someone else's question
		results.add(testDeleteQuestion("poster2", false));
		// test 21: a user can delete their answer
		results.add(testDeleteAnswer("poster1", true));
		// test 22: a user can't delete someone else's answer
		results.add(testDeleteAnswer("poster2", false));
		
		
		// prints out a summary for fast debugging
		System.out.println("\n\n+------------------------------------------------+");
		System.out.printf("|%48s|\n", "");
		System.out.println("|               Testing Summary:                 |");
		System.out.printf("|%48s|\n", "");
		System.out.println("|    Test No.:                        Result:    |");
		System.out.printf("|%48s|\n", "");
		System.out.println("+------------------------------------------------+");
		System.out.printf("|%48s|\n", "");
		if(results.size() == 0) {
			System.out.println("|               No tests performed               |");
		}
		else {
			for(int i = 1; i <= results.size(); i++)
			{
				System.out.printf("|    %-20s%20s    |\n", String.valueOf(i), results.get(i-1) ? "Success" : "Fail");
			}
		}
		System.out.printf("|%48s|\n", "");
		System.out.println("+------------------------------------------------+");
	}

}
