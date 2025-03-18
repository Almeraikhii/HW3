package TestingAutomation;

import QuestionAndAnswer.Answer;

public class EditAnswerTest {
    public static void main(String[] args) {
        // Create two users
        String user1 = "John";
        String user2 = "Alice";

        // Create an answer owned by user1
        Answer answer = new Answer(user1, "This is my original answer.");

        // Test 1: User1 should be able to edit their own answer
        boolean user1CanEdit = answer.editAnswer(user1, "This is my updated answer.");
        System.out.println("User1 editing their own answer: " + (user1CanEdit ? "✅ Allowed" : "❌ Not Allowed"));

        // Test 2: User2 should NOT be able to edit User1's answer
        boolean user2CanEdit = answer.editAnswer(user2, "I'm trying to edit someone else's answer.");
        System.out.println("User2 trying to edit User1's answer: " + (user2CanEdit ? "❌ Allowed" : "✅ Blocked"));
    }
}
