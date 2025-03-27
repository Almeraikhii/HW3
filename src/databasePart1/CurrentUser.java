
package databasePart1;

public class CurrentUser {
	
	private String username;
	
	private String role;
	
	public CurrentUser() {
		
	}
	
	public CurrentUser(String username) {
		
		this.username = username;
		
	}
	
	public String GetUserName() {
		return this.username;
	}
	
	public void SetUserName(String username) {
		this.username = username;
	}
	
	public void SetRole(String role) {
		this.role = role;
	}

	public String GetRole() {
		return this.role;
	}
}
