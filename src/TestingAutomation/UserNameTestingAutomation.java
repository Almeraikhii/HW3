package TestingAutomation;

import userNameRecognizerTestbed.UserNameRecognizer;
import java.util.ArrayList;

public class UserNameTestingAutomation {
	
	// ArrayList to store test results for the summary page
	public static ArrayList<Boolean> testResults = new ArrayList<Boolean>();
	
	// int to store the number of tests performed
	public static int testCount = 0;
	
	public static void main(String[] args) {
		
		//UserName tests:
		// Test 1 - to prevent empty usernames
		performTest("", false);
		// Test 2 - to prevent usernames with less than 4 characters
		performTest("abc", false);
		// Test 3 - to prevent usernames with more than 16 characters
		performTest("abcdefghijklmopqrstuvwxyz0123456789", false);
		// Test 4, 5 - to prevent usernames that begin or end with _, -, or .
		performTest("_abcd0", false);
		performTest("abcd.", false);
		// Test 6 - to prevent usernames with invalid characters
		performTest("ab!cd", false);
		// Test 7 - to prevent usernames that start with numbers
		performTest("0abcd", false);
		// Various tests to approve usernames
		performTest("abcd_e", true);
		performTest("us3rname", true);
		performTest("thisIs-v4lid", true);
		performTest("w.o.a.h", true);
		
		
		// Print test summary
		System.out.println("\n\n+------------------------------------------------+");
		System.out.printf("|%48s|\n", "");
		System.out.println("|           Username Testing Summary:            |");
		System.out.printf("|%48s|\n", "");
		System.out.println("|    Test No.:                        Result:    |");
		System.out.printf("|%48s|\n", "");
		System.out.println("+------------------------------------------------+");
		System.out.printf("|%48s|\n", "");
		if(testCount == 0) {
			System.out.println("|               No tests performed               |");
		}
		else {
			for(int i = 1; i <= testCount; i++)
			{
				System.out.printf("|    %-20s%20s    |\n", String.valueOf(i), testResults.get(i-1) ? "Success" : "Fail");
			}
		}
		System.out.printf("|%48s|\n", "");
		System.out.println("+------------------------------------------------+");
	}
	
	
	/* 
	 * This method takes a test UserName and expected result, runs it through the UserName evaluator
	 * that the application uses, and checks to see if the evaluator is working as expected.
	 */
	private static void performTest(String userName, boolean expectedResult) {
		System.out.printf("Performing test no. %d:\n", testCount+1);
		
		// Evaluate UserName
		String errorMessage = UserNameRecognizer.checkForValidUserName(userName);
		boolean success = errorMessage.equals("UserName is valid\n");
		
		// Increment the number of tests performed
		testCount++;
		
		if(success) {
			if(expectedResult) {
				// If the expected and actual are both true, success
				System.out.printf("Success! The username <%s> is valid, as intended.\n", userName);
				testResults.add(true);
			}
			else {
				// If the expected and actual differ, fail
				System.out.printf("Fail! The username <%s> is valid, but it wasn't intended to be.\n", userName);
				testResults.add(false);
			}
		}
		else {
			if(expectedResult) {
				// If the expected and actual differ, fail
				System.out.printf("Fail! The username <%s> is not valid, but it was intended to be.\n", userName);
				testResults.add(false);
			}
			else {
				// If the expected and actual are both false, success
				System.out.printf("Success! The username <%s> is not valid, as intended.\n", userName);
				testResults.add(true);
			}
		}
	}
}
