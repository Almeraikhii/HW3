package QuestionAndAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionSearcher {
	
	
	//words that wont be used as keywords for the search.
	public static String[] unwantedWords= {"can", "or", "because", "and", "of", "I", "why", "how", "is"}; 
	
	public static String finding_Keyword(String Keyword_search) {
		
	//converting to lowercase so that search would be correct since everything is lowercase, and splitting the input of the user.
	 String[] seperating_search = Keyword_search.toLowerCase().split(" ");
	
	
	
	for (String key_words : seperating_search) {
		if (key_words.length()>=4 && !isUnwantedWord(key_words)) {// to make sure the keyword is atleast 4 charachters and not one of the unwanted words
			
			return key_words;}

	}
	return null;// return null if no keyword was found
}
	
	
	
	private static boolean isUnwantedWord(String checking_word) {
        

        for (String not_keyword : unwantedWords) {
            if (checking_word.toLowerCase().equals(not_keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}