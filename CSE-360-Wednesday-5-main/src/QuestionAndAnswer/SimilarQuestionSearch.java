package QuestionAndAnswer;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Iterator;

import databasePart1.DatabaseHelper;
import databasePart1.*;

public class SimilarQuestionSearch {
	
	//words that wont be used as keywords for the search.
	public static String[] unwantedWords= {"can", "or", "because", "and", "of", "I", "why", "how", "is", "what", "for"}; 
	private static DatabaseHelper databaseHelper;
	
	public SimilarQuestionSearch(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
	
	//returns Question object with most keyword overlap in titles
	public Question findSimilarQuestion(String title) {
		
		//Gets array of lower case words from title
		String[] word_list = title.toLowerCase().split(" ");
		ArrayList<String> arrayWords = new ArrayList<>(Arrays.asList(word_list)); 
		
		//remove common words from list of words
		for (Iterator<String> it = arrayWords.iterator(); it.hasNext();) {
			if (isUnwantedWord(it.next())) {
			    it.remove();
			}
		}
		
		
		int max = 0; //keeps track of maximum similar words
		int count = 0;
		Question mostSimilar = null;
		
		ArrayList<Question> listOfQs = new ArrayList<Question>();
		QuestionList q1 = new QuestionList(databaseHelper);
		listOfQs = q1.getAllQuestions();
		
		for(Question q : listOfQs)
		{
			String[] compareList = q.getTitle().toLowerCase().split(" ");
			for(String word : compareList)
			{
				if(arrayWords.contains(word))
				{
					count++;
				}
			}
			if(count > max)
			{
				max = count;
				mostSimilar = q;
			}
		}
		
		if(max < 2) //if less than 2 shared keywords, return null
		{
			return null;
		}
		else
		{
			return mostSimilar; // returns most similar
		}
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
