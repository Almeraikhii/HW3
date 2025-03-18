package EmailRecognizer;

public class EmailRecognizer {

	// Strings to store error message and input
	public static String emailErrorMessage;
	private static String emailRecognizerInput;
	
	// char and ints to store current character and state
	private static char currentChar;
	private static int currentCharIndex = 0;
	public static int state = 0;
	public static int nextState = 0;
	
	// booleans to check for errors
	private static boolean running = false;
	public static boolean localBeginAlphanumeric = false;
	public static boolean localEndAlphanumeric = false;
	public static boolean at = false;
	public static boolean domainBeginAlphanumeric = false;
	public static boolean domainEndAlphanumeric = false;
	public static boolean dot = false;


	/*
	 * This method moves to the next character in the email
	 */
	private static void moveToNextCharacter() {		
		if(currentCharIndex < emailRecognizerInput.length()) {
			currentChar = emailRecognizerInput.charAt(currentCharIndex);
			currentCharIndex++;
		}
		else {
			currentChar = ' ';
			running = false;
		}
	}
	
	/*
	 * This method goes through states the evaluate whether an email is long enough,
	 * and provides error messages detailing the problems if invalid.
	 * If the email is valid, it will return "The email is valid.\n"
	 */
	public static String checkForValidEmail(String email) {
		// record the size of the email
		int localSize = 0;
		int domainSize = 0;
		int TLDSize = 0;
		
		// reset the booleans
		localBeginAlphanumeric = false;
		localEndAlphanumeric = false;
		at = false;
		domainBeginAlphanumeric = false;
		domainEndAlphanumeric = false;
		dot = false;
		
		// reset the variables 
		emailErrorMessage = "";
		state = 0;
		nextState = -1;
		running = true;
		emailRecognizerInput = email;
		currentCharIndex = 0;
		// get the first character
		moveToNextCharacter();
		if(!running) {
			emailErrorMessage = "Please enter an email.\n";
			return emailErrorMessage;
		}
		System.out.println("Letter:     State:");
		
		// keep going until there are no more characters
		while(running) {
			nextState = -1;
			switch(state) {
			case -1:
				running = false;
				break;
			// starting state, must begin with alphanumeric
			case 0:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					localBeginAlphanumeric = true;
					localEndAlphanumeric = true;
					localSize++;
					nextState = 1;
				}
				break;
			// state for successive characters in the local part of the email
			case 1:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					localSize++;
					nextState = 1;
				}
				else if("!#$%&'*+-/=?^_`{|}~.".indexOf(currentChar) >= 0) {
					localEndAlphanumeric = false;
					localSize++;
					nextState = 2;
				}
				else if(currentChar == '@') {
					at = true;
					nextState = 3;
				}
				break;
			// state for special characters in email
			case 2:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					localEndAlphanumeric = true;
					localSize++;
					nextState = 1;
				}
				break;
			// state for "@" received
			case 3:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					domainBeginAlphanumeric = true;
					domainEndAlphanumeric = true;
					domainSize++;
					nextState = 4;
				}
				break;
			// state for domain beginning in alphanumeric
			case 4:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || (currentChar >= '0' && currentChar <= '9')){
					domainSize++;
					nextState = 4;
				}
				else if(currentChar == '-') {
					domainEndAlphanumeric = false;
					domainSize++;
					nextState = 5;
				}
				else if(currentChar == '.') {
					dot = true;
					nextState = 6;
				}
				break;
			// state for successive characters in the domain
			case 5:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					domainEndAlphanumeric = true;
					domainSize++;
					nextState = 4;
				}
				break;
			// state for "." in email
			case 6:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					TLDSize++;
					nextState = 7;
				}
				break;
			// state for first character in TLD
			case 7:
				if((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z') || currentChar >= '0' && currentChar <= '9'){
					TLDSize++;
					nextState = 7;
				}
				break;
			}
			if(state != -1) {
				state = nextState;
				System.out.printf("%-12s%s\n", currentChar, String.valueOf(state));
				moveToNextCharacter();
			}
		}
		
		// error messages for different problems
		if(state != 7 || TLDSize < 2 || TLDSize > 63 || localSize + domainSize + TLDSize + 2 > 320) {
			emailErrorMessage = "E-mail is invalid.\n";
		}
		else
			emailErrorMessage = "E-mail is valid.\n";
		
		return emailErrorMessage;
	}

}
