package business.employee;

import business.Status;

public class AuthToken {

	private int id;
	private String authToken;
	private Status type;
	private String name;

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {return id;}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getAuthToken() {return authToken;}
	public void setType(Status type) {
		this.type = type;
	}
	public Status getType() {return type;}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {return name;}
}