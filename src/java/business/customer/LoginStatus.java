package business.customer;


import business.Status;

public class LoginStatus {
	// upon login this class will contain login status and AuthToken object
	// for successful login status = true and otherwise {false and token = null}
	private final AuthToken token;
	private Status status;
	
	public LoginStatus(AuthToken token, Status status) {
		this.token = token;
		this.status = status;
	}
	public AuthToken getToken() {return token;}
	public Status getStatus() {return status;}
}