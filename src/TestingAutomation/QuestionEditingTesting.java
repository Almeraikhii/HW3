package TestingAutomation;
import QuestionAndAnswer.Answer;
import QuestionAndAnswer.Question;
import databasePart1.DatabaseHelper;
import java.util.ArrayList;


/**
 * This test checks that the only one 
 * who can edit the question is the
 *  one who posted it.
 */
public class QuestionEditingTesting {




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
            
            
             //the username of the user trying to edit the question, this user should not be allowed to edit
        String other_student_editing = "Not_Mohammad";
        
        if (!wanted_question.getUserName().equals(other_student_editing)) {
            System.out.println("cannot edit the question since this user wasn't the one who posted it.");
        }else {
        	System.out.println("the correct user edited the question"); }
        }
    
}
            
            
            
            