package QuestionAndAnswer;

import java.time.Instant;
import java.io.*;
import java.sql.Timestamp;
import java.time.Duration;

/*
 * The Answer class represents a single answer, and contains
 * necessary information such as the answer contents, the author,
 * and whether they wish to remain anonymous
 */
public class Answer implements Serializable{
	
	// Relevant data of a answer
	private int id;
	private int questionID;
	// The answer ID should be -1 if the answer is replying to a question.
	// Otherwise, it should be the ID of the answer it is replying to.
	private int answerID;
	private String contents;
	private String userName;
	private boolean anonymous;
	private Instant datePosted;
	private boolean edited;
	private Instant dateEdited;
	private boolean resolved;
	private boolean isPrivate;
	
	/**
	 * Constructor to create an answer
	 * @param id
	 * @param questionID
	 * @param answerID -- will be -1 if not replying to an answer
	 * @param contents
	 * @param userName
	 * @param anonymous
	 * @param datePosted
	 * @param edited
	 * @param dateEdited
	 * @param resolved
	 * @param isPrivate
	 */
	public Answer (int id, int questionID, int answerID, String contents, String userName, boolean anonymous, Timestamp datePosted, boolean edited, Timestamp dateEdited, boolean resolved, boolean isPrivate) {
		this.id = id;
		this.questionID = questionID;
		this.answerID = answerID;
		this.contents = contents;
		this.userName = userName;
		this.anonymous = anonymous;
		this.edited = edited;
		this.isPrivate = isPrivate;
		this.resolved = resolved;
		this.datePosted = datePosted.toInstant();
		if (dateEdited != null) {	
			this.dateEdited = dateEdited.toInstant();
		}
		else {
			this.dateEdited = null;
		}

	}
	
	
	public int getID() { return id; }
	
	public String getContents() { return contents; }
	
	public boolean getResolved() { return resolved; }
	
	public boolean getPrivate() { return isPrivate; }
		
	public String getUserName() { return userName; }
	
	public boolean getAnonymity() { return anonymous; }
	
	public int getQuestionID() { return questionID; }
	
	public int getAnswerID() { return answerID; }
	
	
	// returns a string describing how long ago the answer was posted
	public String getDatePosted() {
		Instant currentTime = Instant.now();
		String dateMessage = "";
		dateMessage += getTimeAgo(datePosted, currentTime);
		if(edited)
			dateMessage += " (Edited " + getTimeAgo(dateEdited, currentTime) + ")";
		return dateMessage;
	}
	
	// helper method to calculate how long ago a message was posted/edited
	private String getTimeAgo(Instant originalTime, Instant currentTime) {
		double time = Duration.between(originalTime, currentTime).getSeconds();
		int seconds = (int)(time + 0.5);
		if(seconds < 60)
			return seconds + " second" + ((seconds == 1) ? "" : "s") + " ago";
		int minutes = (int)(time / 60 + 0.5);
		if(minutes < 60)
			return minutes + " minute" + ((minutes == 1) ? "" : "s") + " ago";
		int hours = (int)(time / 3600 + 0.5);
		if(hours < 24)
			return hours + " hour" + ((hours == 1) ? "" : "s") + " ago";
		int days = (int)(time / 86400 + 0.5);
		if(days < 7)
			return days + " day" + ((days == 1) ? "" : "s") + " ago";
		int weeks = (int)(time / 604800 + 0.5);
		if(days < 52)
			return weeks + " week" + ((weeks == 1) ? "" : "s") + " ago";
		int years = (int)(time / 31536000 + 0.5);
		return years + " year" + ((years == 1) ? "" : "s") + " ago";
	}
	
	
	// writes the data of an answer
		public String toString() {
			String data;
			data = String.format("%s%s\nPosted by %s %s Reply ID: %d", (resolved ? "**Helpful Answer**\n" : ""), contents, getUserName(), getDatePosted(), id);
			return data;
		}
}
