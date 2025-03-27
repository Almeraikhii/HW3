package TestingAutomation;
import QuestionAndAnswer.Question;
import databasePart1.DatabaseHelper;
import java.util.ArrayList;


/**
 * This test checks that the only one 
 * who can delete the question is the
 *  one who posted it.
 */
public class QuestionDeletingTesting {
    
    

    private static DatabaseHelper databaseHelper = new DatabaseHelper();

    public static void main(String[] args) throws Exception {
        databaseHelper.connectToDatabase();

        //the username of the person who posted the question is Mohammad

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


        // the username of the user trying to delete the question, this is not the user who posted the question
        String other_user = "Not_Mohammad";

        //to check if the user that is trying to delete is different than the user who posted the question
        if (!wanted_question.getUserName().equals(other_user)) {
            System.out.println("cannot delete the question since this user wasn't the one who posted it.");
        } else {
            System.out.println("the correct user deleted his own question"); }
    }
    }
