package PasswordRecognizer;

public class PasswordRecognizer {
	
	// Booleans to check validity of a password.
	// To be a valid password, each one must be true
	// except for otherChar, can either be true or false.
	// However, if otherChar is flagged as true, the FSM
	// stops checking the password.
	public static Boolean upperCase;
	public static Boolean lowerCase;
	public static Boolean numericChar;
	public static Boolean specialChar;
	public static Boolean longEnough;
	public static Boolean otherChar;
	
	public static String checkForValidPassword (String password) {
		
		
		// Variables to be used within FSM
		int state;
		int charCounter;
		char currentChar;
		
		// Initialize all Booleans to be false at the 
		// start of each check.
		upperCase = false;
		lowerCase = false;
		numericChar = false;
		specialChar = false;
		longEnough = false;
		otherChar = false;
		
		// Set state variable to initial state of 0
		// and initialize charCounter to beginning character of Password
		state = 0;
		charCounter = 0;
		
		// This code is implemented in the image of a Finite State Machine
		// (FSM). It contains two states: state 0 and state 1. The FSM only 
		// finishes execution once it reaches state 1, and state 1 can only
		// be reached when an invalid character is detected, or when the Password
		// has been completely read by the FSM.
		while (password != null && state == 0 && 
				charCounter < password.length()) {
			// Update currentChar to next portion of the Password
			currentChar = password.charAt(charCounter);
			
			if (currentChar >= 65 && 
					currentChar <= 90) { // Check for capital letters A-Z
				upperCase = true;
			}
			
			else if (currentChar >= 97 && 
					currentChar <= 122) { // Check for lowercase letters a-z
				lowerCase = true;
			}
			
			else if (currentChar >= 48 && 
					currentChar <= 57) { // Check for numbers 0-9
				numericChar = true;
			}
			
			else if (currentChar == '~' ||
					currentChar == '`' ||
					currentChar == '!' ||
					currentChar == '@' ||
					currentChar == '#' ||
					currentChar == '$' ||
					currentChar == '%' ||
					currentChar == '^' ||
					currentChar == '&' || 
					currentChar == '*' || 
					currentChar == '(' || 
					currentChar == ')' ||
					currentChar == '_' ||
					currentChar == '-' ||
					currentChar == '+' ||
					currentChar == '{' ||
					currentChar == '}' ||
					currentChar == '[' ||
					currentChar == ']' ||
					currentChar == '|' ||
					currentChar == ':' ||
					currentChar == ',' ||
					currentChar == '.' ||
					currentChar == '?' ||
					currentChar == '/') { // Check for any special characters
				
				specialChar = true;
			}
			
			else { // Invalid character detected, transition to state 1
				otherChar = true;
				++charCounter;
				state = 1;
				break;
			}
			
			if (charCounter != password.length()-1) { // Increment charCounter if it's not at the 
				                                      // end of the Password
				++charCounter;
				
			} else { // CharCounter has traversed through all of the Password, transition to state 1
				state = 1;
				++charCounter;
				
				
				
			}
			
			if (password.length() >= 8) { // Check for appropriate Password length
				longEnough = true;
			}
		}
		
		// Once the FSM has finished running, a proper error message will be returned by this method,
		// Assuming that the password is invalid. If the password is valid, however, a message declaring
		// it so will be returned in place of the error.
		
		String error = ""; // Will either display errors with Password, or confirm that it is valid
		
		
		 
		if (upperCase == true &&
			lowerCase == true &&
			numericChar == true &&
			specialChar == true &&
			longEnough == true) { // Check if Password is valid
				error = "Password is valid\n";
				
			}
		
		else { // Password is invalid, display correct errors
			error += "*** ERROR *** "; 
			
			if (otherChar == true) { // Invalid character
				error += "Not all requirements are met before the " + (charCounter+1) + "th character.\n"; //See boolean flags and errors below for more information
			}
			
			if (upperCase == false ||
				lowerCase == false ||
				numericChar == false ||
				specialChar == false ||
				longEnough == false) { // At least one Password requirement is missing
				
					error += "Password must contain:\n";
			
			
					if (upperCase == false) { // Password needs an uppercase letter
						error += "at least one uppercase letter\n";
					}
			
					if (lowerCase == false) { // Password needs a lowercase letter
						error += "at least one lowercase letter\n";
					}
			
					if (numericChar == false) { // Password needs an number
						error += "at least one number\n";
					}
			
					if (specialChar == false) { // Password needs a special character
						error += "at least one of the following special characters: ~`!@#$%^&*()_-+{}[]|:,.?/\n";
					}
			
					if (longEnough == false) { // Password needs to be longer
						error += "at least 8 characters\n";
					}
					
					if (otherChar == true) { // Invalid character
						error += "before including any other characters in your password.\n"; //See boolean flags and errors below for more information
					}
			}
		}
		
		// Return the Password error(s) or confirmation and exit
		System.out.println(error); 
		return error;
	}
	
	
	
}
