package business.employee;

public class LoginStatus {
	// upon login this class will contain login status and AuthToken object
	// for successful login status = true and otherwise {false and token = null}
	private AuthToken token;
	private char status;
	public LoginStatus(AuthToken token, char status) {
		this.token = token;
		this.status = status;
	}
	public AuthToken getToken() {return token;}
	public char getStatus() {return status;}
}