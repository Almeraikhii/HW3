package QuestionAndAnswer;

import java.sql.Timestamp;
import java.io.*;
import java.time.Duration;
import java.time.Instant;

/*
 * The Question class represents a single question, and contains
 * necessary information such as the question contents, the author,
 * and whether they wish to remain anonymous
 */
public class Question implements Serializable{
	
	// Relevant data of a question
	private int id;
	private String title;
	private String contents;
	private String userName;
	private boolean anonymous;
	private Instant datePosted;
	private boolean edited;
	private Instant dateEdited;
	private boolean resolved;
	private int replies;
	
	/**
	 * Constructor to create a question
	 * @param id
	 * @param title
	 * @param contents
	 * @param userName
	 * @param anonymous
	 * @param datePosted
	 * @param dateEdited
	 * @param edited
	 * @param resolved
	 * @param replies
	 */
	public Question (int id, String title, String contents, String userName, boolean anonymous, Timestamp datePosted,
					 Timestamp dateEdited, boolean edited, boolean resolved, int replies) {
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.userName = userName;
		this.anonymous = anonymous;
		this.datePosted = datePosted.toInstant();
		if (dateEdited != null) {	
			this.dateEdited = dateEdited.toInstant();
		}
		else {
			this.dateEdited = null;
		}
		this.edited = edited;
		this.resolved = resolved;
		this.replies = replies;
	}
	
	// Increments the number of replies counter
	public void addReply() {
		replies++;
	}
	
	public int getID() { return id; }
	
	public String getTitle() { return title; }
	
	public String getContents() { return contents; }
	
	public boolean getResolved() { return resolved; }
	
	public int getReplies() { return replies; }
	
	public String getUserName() { return userName; }
	
	public boolean getAnonymity() { return anonymous; }
	
	
	// returns a string describing how long ago the question was posted
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
	
	// writes the data of a question without the contents
	public String toShortString() {
		String data;
		data = String.format("%s - %s\nPosted by %s %s\nReplies: %d Post ID: %d", title, (resolved ? "Resolved" : "Unresolved"), (anonymous ? "Anonymous" : userName), getDatePosted(), replies, id);
		return data;
	}
	
	// writes the data of a question
	public String toString() {
		String data;
		data = String.format("%s - %s\nPosted by %s %s\n%s\nReplies: %d Post ID: %d", title, (resolved ? "Resolved" : "Unresolved"), (anonymous ? "Anonymous" : userName), getDatePosted(), contents, replies, id);
		return data;
	}
}
