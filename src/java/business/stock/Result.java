package business.stock;

import business.Status;

public class Result {
	private Status status;
	private int code;
	private String token;
	public void setStatus(Status status) {
		this.status = status;
	}
	public Status getStatus() {return status;}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {return code;}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}