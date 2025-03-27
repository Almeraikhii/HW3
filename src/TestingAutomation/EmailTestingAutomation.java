package TestingAutomation;

import EmailRecognizer.EmailRecognizer;
import java.util.ArrayList;

public class EmailTestingAutomation {
	
	// ArrayList to store test results for the summary page
	public static ArrayList<Boolean> testResults = new ArrayList<Boolean>();
	
	// int to store the number of tests performed
	public static int testCount = 0;
	
	public static void main(String[] args) {
		
		//Email tests:
		// Test 1 - to prevent empty strings
		performTest("", false);
		// Test 2 - to prevent emails with no local part
		performTest("@gmail.com", false);
		// Test 3, 4 - to prevent emails that begin or end with a special character
		performTest("!test@yahoo.com", false);
		performTest("test!@yahoo.com", false);
		// Test 5 - to prevent emails with consecutive special characters
		performTest("te..st@gmail.com", false);
		// Test 6 - to prevent emails with invalid characters in the local part
		performTest("email<test>email@gmail.com", false);
		// Test 7 - tp prevent emails with no @ symbol
		performTest("testgmail.com", false);
		// Test 8 - to prevent emails with no domain
		performTest("test@.com", false);
		// Test 9, 10 - to prevent emails with domains that begin or end with hyphens
		performTest("test@-gmail.com", false);
		performTest("test@gmail-.com", false);
		// Test 11 - to prevent emails with invalid characters in the domain
		performTest("test@gm_ail.com", false);
		// Test 12, 13 - to prevent emails with a too short/long TLD
		performTest("test@gmail.c", false);
		performTest("test@gmail.cooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooom", false);
		// Test 14 - to prevent emails with special characters in the TLD
		performTest("test@gmail.co!", false);
		// Test 15 - to prevent emails with no TLD
		performTest("test@gmail", false);
		// Test 16 - to prevent emails with no TLD or domain
		performTest("test@", false);
		// Test 17 - to prevent too long emails
		performTest("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest@gmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmailgmail.comcomcomcomcomcomcomcomcomcom", false);
		// Various tests to accept valid emails
		performTest("test@gmail.com", true);
		performTest("te_st@gmail.com", true);
		performTest("test@gm-ail.com", true);
		performTest("t_e!s#t@gmail.net", true);
		
		


		
		// Print test summary
		System.out.println("\n\n+------------------------------------------------+");
		System.out.printf("|%48s|\n", "");
		System.out.println("|            E-mail Testing Summary:             |");
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
		System.out.println("\nNote: If an email is invalid, the evaluator will not continue evaluating the email.");
	}
	
	
	/* 
	 * This method takes a test Email and expected result, runs it through the Email evaluator
	 * that the application uses, and checks to see if the evaluator is working as expected.
	 */
	private static void performTest(String Email, boolean expectedResult) {
		System.out.printf("\nPerforming test no. %d:\n", testCount+1);
		
		// Evaluate Email
		String errorMessage = EmailRecognizer.checkForValidEmail(Email);
		System.out.println(errorMessage);
		boolean success = errorMessage.equals("E-mail is valid.\n");
		
		// Increment the number of tests performed
		testCount++;
		
		if(success) {
			if(expectedResult) {
				// If the expected and actual are both true, success
				System.out.printf("Success! The e-mail <%s> is valid, as intended.\n", Email);
				printEmailData();
				testResults.add(true);
			}
			else {
				// If the expected and actual differ, fail
				System.out.printf("Fail! The e-mail <%s> is valid, but it wasn't intended to be.\n", Email);
				printEmailData();
				testResults.add(false);
			}
		}
		else {
			if(expectedResult) {
				// If the expected and actual differ, fail
				System.out.printf("Fail! The e-mail <%s> is not valid, but it was intended to be.\n", Email);
				printEmailData();
				testResults.add(false);
			}
			else {
				// If the expected and actual are both false, success
				System.out.printf("Success! The e-mail <%s> is not valid, as intended.\n", Email);
				printEmailData();
				testResults.add(true);
			}
		}
	}
	
	/*
	 * This method prints the requirements of a Email and whether the Email evaluator
	 * marked them as satisfied.
	 */
	private static void printEmailData() {
		System.out.printf("\nE-mail Requirements:\n");
		System.out.printf("%-50s %s\n", "E-mail begins with alphanumeric character:", EmailRecognizer.localBeginAlphanumeric ? "Satisfied" : "Not satisfied");
		System.out.printf("%-50s %s\n", "\"@\" is preceeded by alphanumeric character:", EmailRecognizer.localEndAlphanumeric ? "Satisfied" : "Not satisfied");
		System.out.printf("%-50s %s\n", "Contains \"@\":", EmailRecognizer.at ? "Satisfied" : "Not satisfied");
		System.out.printf("%-50s %s\n", "E-mail domain begins with alphanumeric character:", EmailRecognizer.domainBeginAlphanumeric ? "Satisfied" : "Not satisfied");
		System.out.printf("%-50s %s\n", "E-mail domain ends with alphanumeric character:", EmailRecognizer.domainEndAlphanumeric ? "Satisfied" : "Not satisfied");
		System.out.printf("%-50s %s\n", "Contains \".\":", EmailRecognizer.dot ? "Satisfied" : "Not satisfied");
	}
}
