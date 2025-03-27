package TestingAutomation;
import QuestionAndAnswer.Question;
import databasePart1.DatabaseHelper;
import java.util.ArrayList;


/**
 * This test checks that the title
 * is 200 characters or less
 * and makes sure its not more
 */
public class TitleLessThan {
    
    

    private static DatabaseHelper databaseHelper = new DatabaseHelper();

    public static void main(String[] args) throws Exception {
        databaseHelper.connectToDatabase();

    

        // the title of the question
        String question_title = "testing";
        
    
        //to be able to access the questions in the program
        ArrayList<Question> all_questions = databaseHelper.loadQuestions();

        Question wanted_question=null;
        
        //to go through the list of questions asked in the database and look for the question with the same title
        for(Question the_tested_question: all_questions){
            if (the_tested_question.getTitle().equals(question_title)) {
            wanted_question=the_tested_question;
            
        }
        }
        
        //print the message if the question wasn't found in the database
        if (wanted_question==null){
            System.out.println("question not found");
        }
        
        //storing the title from the question into the_tested_title
        String the_tested_title=wanted_question.getTitle();
        
        
        //using the title we stored earlier to make sure that it doesn't have more than 200
        if(the_tested_title.length()>200){
            System.out.println("the title is more than 200 characters");
        } else{
            System.out.println("the title is good and less than 200 characters");
        }
        
        //to print the title we tested
        System.out.println("the tested title was: "+ the_tested_title);
    }
}
        
        
        
        
        
        
        
        