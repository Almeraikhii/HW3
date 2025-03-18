package databasePart1;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import QuestionAndAnswer.*;
import application.User;


/**
 * The DatabaseHelper class is responsible for managing the connection to the database,
 * performing operations such as user registration, login validation, and handling invitation codes.
 */
public class DatabaseHelper {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/FoundationDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt

	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			// You can use this command to clear the database and restart from fresh.
			//statement.execute("DROP ALL OBJECTS");

			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}

	private void createTables() throws SQLException {
		String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "userName VARCHAR(255) UNIQUE, "
				+ "password VARCHAR(255), "
				+ "roles VARCHAR(5), "
				+ "email VARCHAR(255))";
		statement.execute(userTable);
		
		// Create the invitation codes table
	    String invitationCodesTable = "CREATE TABLE IF NOT EXISTS InvitationCodes ("
	            + "code VARCHAR(10) PRIMARY KEY, "
	            + "isUsed BOOLEAN DEFAULT FALSE, "
	            + "dateCreated VARCHAR(50))"; // Change -- added a timestamp to invitation codes	
	    statement.execute(invitationCodesTable);
	    
	    String OTPCodesTable = "CREATE TABLE IF NOT EXISTS OTPCodes ("
	    		+ "code VARCHAR(16) PRIMARY KEY, "
	    		+ "userName VARCHAR(255) UNIQUE)";
	    statement.execute(OTPCodesTable);
	    
	    String questionsTable = "CREATE TABLE IF NOT EXISTS Questions ("
	    		+ "id INT AUTO_INCREMENT PRIMARY KEY, "
	    		+ "userName VARCHAR(255), "
	    		+ "title VARCHAR(100) UNIQUE, "
	    		+ "contents VARCHAR(10000), "
	    		+ "datePosted TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
	    		+ "dateEdited TIMESTAMP NULL DEFAULT NULL, "
	    		+ "edited BOOLEAN DEFAULT FALSE, "
	    		+ "anonymous BOOLEAN DEFAULT FALSE, "
	    		+ "resolved BOOLEAN DEFAULT FALSE)";
	    statement.execute(questionsTable);
	    
	    String answersTable = "CREATE TABLE IF NOT EXISTS Answers ("
	    		+ "id INT AUTO_INCREMENT PRIMARY KEY, "
	    		+ "questionID INT, "
	    		+ "answerID INT, "
	    		+ "userName VARCHAR(255), "
	    		+ "contents VARCHAR(10000), "
	    		+ "datePosted TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
	    		+ "dateEdited TIMESTAMP NULL DEFAULT NULL, "
	    		+ "edited BOOLEAN DEFAULT FALSE, "
	    		+ "anonymous BOOLEAN DEFAULT FALSE, "
	    		+ "private BOOLEAN DEFAULT FALSE, "
	    		+ "resolved BOOLEAN DEFAULT FALSE)";
	    statement.execute(answersTable);
	}


	// Check if the database is empty
	public boolean isDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM cse360users";
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}

	// Registers a new user in the database.
	public void register(User user) throws SQLException {
		String insertUser = "INSERT INTO cse360users (userName, password, email, roles) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(4, user.getRole());
			pstmt.setString(3, user.getEmail());
			pstmt.executeUpdate();
		}
	}

	// Validates a user's login credentials.
	public boolean login(User user) throws SQLException {
		String query = "SELECT * FROM cse360users WHERE userName = ? AND password = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	public boolean OTPlogin (String user, String code) throws SQLException {
		String query = "SELECT * FROM OTPCodes WHERE userName = ? AND code = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user);
			pstmt.setString(2, code);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	public boolean deleteOTP(String user, String code) throws SQLException {
	    String query = "DELETE FROM OTPCodes WHERE userName = ? AND code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, user);
	        pstmt.setString(2, code);
	        int result = pstmt.executeUpdate();
			if(result >= 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Checks if a user already exists in the database based on their userName.
	public boolean doesUserExist(String userName) {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the user exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume user doesn't exist
	}
	
	// Retrieves the role of a user from the database using their UserName.
	public String getUserRole(String userName) {
	    String query = "SELECT roles FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("roles"); // Return the role if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	public boolean updateRoles(String username, String roles) {
		String query = "UPDATE cse360users SET roles = ? WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, roles);
			pstmt.setString(2, username);
			int result = pstmt.executeUpdate();
			if(result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Generates a new invitation code and inserts it into the database.
	public String generateInvitationCode() {
	    String code = UUID.randomUUID().toString().substring(0, 4); // Generate a random 4-character code
	    String query = "INSERT INTO InvitationCodes (code, dateCreated) VALUES (?, CURRENT_TIMESTAMP)"; // Change -- adds the current timestamp to the invitation code

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return code;
	}
	
	// Validates an invitation code to check if it is unused.
	public boolean validateInvitationCode(String code) {
	    String query = "SELECT * FROM InvitationCodes WHERE code = ? AND isUsed = FALSE";
	    // change -- new query to get the current time
	    String query2 = "SELECT CURRENT_TIMESTAMP AS currentTime";
	    try (PreparedStatement pstmt = connection.prepareStatement(query); PreparedStatement pstmt2 = connection.prepareStatement(query2)) {
	        pstmt.setString(1, code);
	        ResultSet rs = pstmt.executeQuery();
	        ResultSet rs2 = pstmt2.executeQuery();
	        if (rs.next() && rs2.next()) {
	            // Mark the code as used
	            markInvitationCodeAsUsed(code);
	        	
	            // Change -- compare the times, if more than 30seconds (can change, do not accept invite code)
	        	Timestamp dateCreated = rs.getTimestamp("dateCreated");
	        	Timestamp currentTime = rs2.getTimestamp("currentTime");
	        	if(currentTime.getTime() - dateCreated.getTime() >= 300000) {
	        		return false;
	        	}
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	// Marks the invitation code as used in the database.
	private void markInvitationCodeAsUsed(String code) {
	    String query = "UPDATE InvitationCodes SET isUsed = TRUE WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public String generateOTPCode(String username) {
		String code = UUID.randomUUID().toString().substring(0,8);
		String query = "INSERT INTO OTPCodes (code, userName) VALUES (?, ?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1, code);
			pstmt.setString(2, username);
	        pstmt.executeUpdate();
		} catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return code;
		
	}
	
	//Change -- Checks for a user and deletes them.
	public boolean deleteUser(String userName) {
		String query = "DELETE FROM cse360users WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, userName);
			int result = pstmt.executeUpdate();
			if(result >= 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// changes a users password
	public boolean changePassword(String user, String password) {
		String query = "UPDATE cse360users SET password = ? WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, password);
			pstmt.setString(2, user);
			int result = pstmt.executeUpdate();
			if(result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// lists all users
	public String listUsers() throws SQLException {
        String usernames = "";
        String query = "SELECT userName FROM cse360users";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                usernames = usernames + rs.getString("userName") + "\n";
            }
        }
        return usernames;
    }
	
	//Functions for Student Page
	
	
	// posts a question to the database
	public void postQuestion(String userName, String title, String contents, boolean isAnonymous){
		String query = "INSERT INTO Questions (userName, title, contents, anonymous) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, userName);
			pstmt.setString(2, title);
			pstmt.setString(3, contents);
			pstmt.setBoolean(4,  isAnonymous);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// posts an answer to the database
	public void postAnswer(int questionID, int answerID, String userName, String contents, boolean isAnonymous, boolean isPrivate) {
		String query = "INSERT INTO Answers (questionID, answerID, userName, contents, anonymous, private) VALUES (?, ?, ?, ?, ?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, questionID);
			pstmt.setInt(2, answerID);
			pstmt.setString(3, userName);
			pstmt.setString(4, contents);
			pstmt.setBoolean(5, isAnonymous);
			pstmt.setBoolean(6, isPrivate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// edits a question in the database
	public void editQuestion(String title, String contents, boolean anonymity, int id) {
		String query = "UPDATE Questions SET title = ?, contents = ?, anonymous = ?, "
					 + "edited = TRUE, dateEdited = CURRENT_TIMESTAMP WHERE id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setBoolean(3, anonymity);
			pstmt.setInt(4, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// edits an answer in the database
	public void editAnswer(String contents, boolean anonymity, int id) {
		String query = "UPDATE Answers SET contents = ?, anonymous = ?, "
					 + "edited = TRUE, dateEdited = CURRENT_TIMESTAMP WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, contents);
			pstmt.setBoolean(2, anonymity);
			pstmt.setInt(3, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// sets a questions resolved boolean to true
	public void resolveQuestion(int id) {
		String query = "UPDATE Questions SET resolved = TRUE WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resolveAnswer(int id) {
		String query = "UPDATE Answers SET resolved = TRUE WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// deletes a question from the database, including all corresponding answers
	public boolean deleteQuestion(int id) {
		String query = "DELETE FROM Questions WHERE id = ?";
		String query2 = "DELETE FROM Answers WHERE questionID = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query); PreparedStatement pstmt2 = connection.prepareStatement(query2)) {
			pstmt.setInt(1, id);
			pstmt2.setInt(1, id);
			int result = pstmt.executeUpdate();
			int result2 = pstmt2.executeUpdate();
			if(result >= 1 && result2 >= 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// deletes an answer from the databasae, including all corresponding answer replies
	public boolean deleteAnswer(int id) {
		String query = "DELETE FROM Answers WHERE id = ? OR answerID = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.setInt(2, id);
			int result = pstmt.executeUpdate();
			if(result >= 1) {
					return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean doesQuestionExist(String title) {
		String query = "SELECT COUNT(*) FROM Questions WHERE title = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, title);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the question exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume question doesn't exist
	}
	
	public Question getQByTitle(String t) {
		String query = "SELECT * FROM Questions WHERE title = ?";
		
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, t);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	        	int id = rs.getInt("id");
	            String userName = rs.getString("userName");
	            String title = rs.getString("title");
	            String contents = rs.getString("contents");
	            Timestamp datePosted = rs.getTimestamp("datePosted");
	            Timestamp dateEdited = rs.getTimestamp("dateEdited");
	            boolean edited = rs.getBoolean("edited");
	            boolean anonymous = rs.getBoolean("anonymous");
	            boolean resolved = rs.getBoolean("resolved");
	            
	            return new Question(id, title, contents, userName, anonymous, datePosted, dateEdited, edited, resolved, 0);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	// returns all of the questions in the database as Question objects in an ArrayList
	public ArrayList<Question> loadQuestions() {
		String query = "SELECT * FROM Questions";
		try(ResultSet rs = statement.executeQuery(query)) {
		
			ArrayList<Question> temp = new ArrayList<Question>();
			while (rs.next()) {
				int id = rs.getInt("id");
	            String userName = rs.getString("userName");
	            String title = rs.getString("title");
	            String contents = rs.getString("contents");
	            Timestamp datePosted = rs.getTimestamp("datePosted");
	            Timestamp dateEdited = rs.getTimestamp("dateEdited");
	            boolean edited = rs.getBoolean("edited");
	            boolean anonymous = rs.getBoolean("anonymous");
	            boolean resolved = rs.getBoolean("resolved");
	             
	            temp.add(new Question(id, title, contents, userName, anonymous, datePosted, dateEdited, edited, resolved, 0));
			}
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// returns a specific question from the id number
	public Question getQuestionByID(int id) {
		String query = "SELECT * FROM Questions WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
	            String userName = rs.getString("userName");
	            String title = rs.getString("title");
	            String contents = rs.getString("contents");
	            Timestamp datePosted = rs.getTimestamp("datePosted");
	            Timestamp dateEdited = rs.getTimestamp("dateEdited");
	            boolean edited = rs.getBoolean("edited");
	            boolean anonymous = rs.getBoolean("anonymous");
	            boolean resolved = rs.getBoolean("resolved");
	            
	            return new Question(id, title, contents, userName, anonymous, datePosted, dateEdited, edited, resolved, 0);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	public Answer getAnswerByID(int id) {
		String query = "SELECT * FROM Answers WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int questionID = rs.getInt("questionID");
				int answerID = rs.getInt("answerID");
				String userName = rs.getString("userName");
				String contents = rs.getString("contents");
				Timestamp datePosted = rs.getTimestamp("datePosted");
				Timestamp dateEdited = rs.getTimestamp("dateEdited");
				boolean edited = rs.getBoolean("edited");
				boolean anonymous = rs.getBoolean("anonymous");
				boolean resolved = rs.getBoolean("Resolved");
				boolean isPrivate = rs.getBoolean("private");
				
				return new Answer(id, questionID, answerID, contents, userName, anonymous, datePosted, edited, dateEdited, resolved, isPrivate);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	public ArrayList<Question> getQuestionsByUser(String userName) {
		String query = "SELECT * FROM Questions WHERE userName = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Question> temp = new ArrayList<Question>();
			while (rs.next()) {
				int id = rs.getInt("id");
	            String title = rs.getString("title");
	            String contents = rs.getString("contents");
	            Timestamp datePosted = rs.getTimestamp("datePosted");
	            Timestamp dateEdited = rs.getTimestamp("dateEdited");
	            boolean edited = rs.getBoolean("edited");
	            boolean anonymous = rs.getBoolean("anonymous");
	            boolean resolved = rs.getBoolean("resolved");
	            
	            temp.add(new Question(id, title, contents, userName, anonymous, datePosted, dateEdited, edited, resolved, 0));
			}
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Answer> getAnswerByQuestion(int questionID) {
		String query = "SELECT * FROM Answers WHERE questionID = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, questionID);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Answer> temp = new ArrayList<Answer>();
			while(rs.next()) {
				int id = rs.getInt("id");
				int answerID = rs.getInt("answerID");
				String userName = rs.getString("userName");
				String contents = rs.getString("contents");
				Timestamp datePosted = rs.getTimestamp("datePosted");
				Timestamp dateEdited = rs.getTimestamp("dateEdited");
				boolean edited = rs.getBoolean("edited");
				boolean anonymous = rs.getBoolean("anonymous");
				boolean resolved = rs.getBoolean("Resolved");
				boolean isPrivate = rs.getBoolean("private");
				temp.add(new Answer(id, questionID, answerID, contents, userName, anonymous, datePosted, edited, dateEdited, resolved, isPrivate));
			}
			return temp;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Closes the database connection and statement.
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}

}
