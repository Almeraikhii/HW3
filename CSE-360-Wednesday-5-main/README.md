# **Screencasts and Meeting video links**
[Screencast 1](https://drive.google.com/file/d/1cH5P8RC7IOX-11dGC8hf-oRMpLBJWO3p/view)

[Screencast 2](https://drive.google.com/file/d/1I_WBtz71Gqsp09tEUkoVQ1h6N2HhjLp7/view?usp=sharing)

[Standup meeting 1 (2/9/25)](https://youtu.be/s9gbvEAD2Gk)

[Standup meeting 2 (2/13/25)](https://youtu.be/PDZnzNyJAaw)

[Standup meeting 3 (2/16/25)](https://youtu.be/yHxu5c2x4rw)

[Standup meeting 4 (2/20/25)](https://youtu.be/kgpXXPRRXy8)

[Standup meeting 5 (2/23/25)](https://youtu.be/OWEbLOmKbKI)

[Standup meeting 6 (2/27/25)](https://youtu.be/QfG8xKWt9gw)


# **NOTE FOR DISPLAYING CORRECT FONTS AND IMAGES**
This program imports an image and two fonts off of filesystem it runs on. The path is set locally (relative to the project), but the file path is written in Unix format. This means that Windows users will need to manually change the '/' character in the file paths of StartCSE360.java to '\\' instead.

# **David's Updates**
Implemented ability to answer a question, and mark an answer as resolved in frontend. Implemented creating private answers and checks to view private answers in the frontend. Led screencast for creating questions and answers, the automatic search feature when a question is created, along with marking an answer and question as resolved. Recorded and uploaded team meetings. 

# **Stavros's Updates**
I added the Question, Answer, QuestionList, and AnswerList classes from my HW2, and adapted them for this project. I added database functionality for posting, editing, and getting answers. I also modified  the QuestionList and AnswerList classes to work with the new database, and I made testing automation for posting, editing, and deleting questions and answers.

# **Sofia's Updates**
Implemented Mohammad’s search function into the RoleStudentPage to give the student an option to search by keyword. I also created an algorithm to find the most similar question by title before the student submits a new question, and added a “yes” and “no” option for the question to be posted or not. 

# **Franz's Updates**
I edited and adapted Stavros’ question class to work in the database and created functions to insert, delete, and edit questions and other database functions. I also developed the question list and its functionality and the main contents of the question main view. I also developed the delete button, the ask button, and the posting a question form and their functionalities.

# **Kirsten's Updates**
Created the functionality for the edit button on the specific questions which allows a user to edit the question if they were the one that wrote it in the first place, and then it also shows that the question has been edited. Also fixed the code to ensure that the edit and delete button only show once per question. Also worked on explaining the application code and showing that the code worked for the edit and delete questions.

# **Mohammad's Updates**
created the search function which allows users to search for a question similar to the question they had in mind, the search uses keywords from the user’s question to find a similar question in the questions stored in the program while also making sure not to use often used words (like can or and) as the keyword for the search function

<!---
OLD README - FOR TP1
# **Screencasts and Meeting video links**
Screencast 1:
https://drive.google.com/file/d/11mYjW2gN_b4HBJjJuNAcQq7QX_R6iUEM/view?usp=sharing

Screencast 2:
https://www.youtube.com/watch?v=qSNJL17Voqc

Standup meeting 1:
https://www.youtube.com/watch?v=Ds6AS1dcYgA

Standup meeting 2:
https://www.youtube.com/watch?v=5XhoN-zyVp8

# **NOTE FOR DISPLAYING CORRECT FONTS AND IMAGES**
This program imports an image and two fonts off of filesystem it runs on. The path is set locally (relative to the project), but the file path is written in Unix format. This means that Windows users will need to manually change the '/' character in the file paths of StartCSE360.java to '\\' instead.

## **David's UI Updates**
I've placed a logo in the top left corner of the UI. Black rectangles surround a smaller bit of whitespace on the top and right sides. Primary text will occupy this whitespace, and secondary text and buttons rest atop the rectangles. The top right corner now displays a greeting message that addresses the user by their name and current role. An imported font is used for most text on the UI. 

SetupLoginSelectionPage now contains a dynamically scaling UI for window resizing.

The pages which include the new UI are:  FirstPage.java  SetupLoginSelectionPage.java  UserHomePage.java  AdminHomePage.java AdminSetupPage.java


## **Stavros's Updates**
Added a deleteUser method and an expiration for invite codes in DatabaseHelper.java

Created testing automation for password, username, and email

Created the email evaluator

## **Sofia's Updates**
Updated UI: SetupAccountPage.java, UserLoginPage.java, AdminModAcc.java, WelcomeLoginPage.java, InvitationPage.java, AdminHomePage.java

## **Franz's Updates**
Added admin home page, account modification page, OTP table and functions, integration of the database, newUser, and admin tasks.
Sources and Acknowledgements:
- Alerts: Oracle Documentation - https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
- CheckBoxes: Orcale Documentation - https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/CheckBox.html
--->
