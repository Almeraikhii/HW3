package TestingAutomation;

import PasswordRecognizer.PasswordRecognizer;
import java.util.ArrayList;

public class PasswordTestingAutomation {
	
	// ArrayList to store test results for the summary page
	public static ArrayList<Boolean> testResults = new ArrayList<Boolean>();
	
	// int to store the number of tests performed
	public static int testCount = 0;
	
	public static void main(String[] args) {
		
		//password tests:
		// Test 1 - to prevent empty passwords
		performTest("", false);
		// Test 2 - to prevent passwords with less than eight characters
		performTest("aBcd1!", false);
		// Test 3 - to prevent passwords with no lowercase letters
		performTest("ABCDEFG!2", false);
		// Test 4 - to prevent passwords with no uppercase letters
		performTest("abcdefg1#", false);
		// Test 5 - to prevent passwords with no digits
		performTest("abcDEF!()", false);
		// Test 6 - to prevent passwords with no special characters
		performTest("abcDEF123", false);
		// Test 7 - to prevent passwords with invalid characters
		performTest("abcDEF123<>!", false);
		// Various tests to confirm valid passwords
		performTest("abcDEF123!", true);
		performTest("thisIsAP4ssword!", true);
		performTest("ALSOaPASSW0RD:", true);
		performTest("!!!!password$$$$PASSWORD&&&&12345678", true);
		
		
		
		// Print test summary
		System.out.println("\n\n+------------------------------------------------+");
		System.out.printf("|%48s|\n", "");
		System.out.println("|           Password Testing Summary:            |");
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
	 * This method takes a test password and expected result, runs it through the password evaluator
	 * that the application uses, and checks to see if the evaluator is working as expected.
	 */
	private static void performTest(String password, boolean expectedResult) {
		System.out.printf("Performing test no. %d:\n", testCount+1);
		
		// Evaluate password
		String errorMessage = PasswordRecognizer.checkForValidPassword(password);
		boolean success = errorMessage.equals("Password is valid\n");
		
		// Increment the number of tests performed
		testCount++;
		
		if(success) {
			if(expectedResult) {
				// If the expected and actual are both true, success
				System.out.printf("Success! The password <%s> is valid, as intended.\n", password);
				printPasswordData();
				testResults.add(true);
			}
			else {
				// If the expected and actual differ, fail
				System.out.printf("Fail! The password <%s> is valid, but it wasn't intended to be.\n", password);
				printPasswordData();
				testResults.add(false);
			}
		}
		else {
			if(expectedResult) {
				// If the expected and actual differ, fail
				System.out.printf("Fail! The password <%s> is not valid, but it was intended to be.\n", password);
				printPasswordData();
				testResults.add(false);
			}
			else {
				// If the expected and actual are both false, success
				System.out.printf("Success! The password <%s> is not valid, as intended.\n", password);
				printPasswordData();
				testResults.add(true);
			}
		}
	}
	
	/*
	 * This method prints the requirements of a password and whether the password evaluator
	 * marked them as satisfied.
	 */
	private static void printPasswordData() {
		System.out.printf("Password Requirements:\n");
		System.out.printf("%-32s %s\n", "At least one uppercase letter:", PasswordRecognizer.upperCase ? "Satisfied" : "Not satisfied");
		System.out.printf("%-32s %s\n", "At least one lowercase letter:", PasswordRecognizer.lowerCase ? "Satisfied" : "Not satisfied");
		System.out.printf("%-32s %s\n", "At least one digit:", PasswordRecognizer.numericChar ? "Satisfied" : "Not satisfied");
		System.out.printf("%-32s %s\n", "At least one special character:", PasswordRecognizer.specialChar ? "Satisfied" : "Not satisfied");
		System.out.printf("%-32s %s\n", "At eight characters:", PasswordRecognizer.longEnough ? "Satisfied" : "Not satisfied");
		System.out.printf("%-32s %s\n", "No invalid characters:", PasswordRecognizer.otherChar ? "Not satisfied" : "Satisfied");

	}
}
