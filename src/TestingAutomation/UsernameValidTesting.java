package TestingAutomation;
import QuestionAndAnswer.Answer;
import QuestionAndAnswer.Question;
import databasePart1.DatabaseHelper;
import java.util.ArrayList;


/**
 * This test checks that the username 
 * is valid and uses only letters
 *  and numbers.
 */
public class UsernameValidTesting {

	private static DatabaseHelper databaseHelper = new DatabaseHelper();
    
    

    public static void main(String[] args) throws Exception {
        databaseHelper.connectToDatabase();
        
        //the title of the question we would like to test
        String question_title= "testing";
        
       
        //the username of the user posting the question is Mohammad
       



      //to be able to access the questions in the program
        ArrayList<Question> all_questions = databaseHelper.loadQuestions();
        
        
        Question wanted_question=null;
        
        //to go through the list of questions asked in the database and look for the question with the same title
        for(Question the_tested_question: all_questions){
            if (the_tested_question.getTitle().equals(question_title)) {
            wanted_question=the_tested_question;}
        }
        
        if (wanted_question==null){
            System.out.println("question not found");
        }
        
        String the_tested_username = wanted_question.getUserName();
        
        String valid_characters= "^[a-zA-Z0-9]+$";
        
        if (!the_tested_username.matches(valid_characters)){
            System.out.println("Username is not valid");
        }else{
            System.out.println("Username is valid");
        }
    }
}
    
   
  
        
        