package TestingAutomation;
import QuestionAndAnswer.Answer;
import QuestionAndAnswer.Question;
import databasePart1.DatabaseHelper;
import java.util.ArrayList;


/**
 * This test checks that the only one 
 * who can edit the answer is the
 *  one who posted it.
 */
public class AnswerEditingTesting {





    private static DatabaseHelper databaseHelper = new DatabaseHelper();
    
    

    public static void main(String[] args) throws Exception {
        databaseHelper.connectToDatabase();
        
        //the title of the question we would like to test
        String question_title= "testing";
        
       
        //the username of the user posting the answer is Mohammad10
       



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
        
        //after finding the question we get its id to find the answer posted to the question
        int question_value = wanted_question.getID();
        
        //we use the question id in order to find the answer posted to it
        ArrayList<Answer> the_answers = databaseHelper.getAnswerByQuestion(question_value);

        
        //to get the first answer of the tested question
        Answer student_answer = the_answers.get(0);


        //the username of the user trying to edit the answer, this user should not be allowed to edit
        String other_student_editing = "Not_Mohammad";


        //to check if the user that is trying to edit is different than the user who posted the answer
        if (!student_answer.getUserName().equals(other_student_editing)) {
            System.out.println("cannot edit the answer since this user wasn't the one who posted it.");
        }else {
        	System.out.println("the correct user edited the answer");
        }
        }
}
