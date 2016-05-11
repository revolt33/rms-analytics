package business.employee;
public class AuthToken {

	private int id;
	private String authToken;
	private char type;
	private String name;

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {return id;}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getAuthToken() {return authToken;}
	public void setType(char type) {
		this.type = type;
	}
	public char getType() {return type;}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {return name;}
}