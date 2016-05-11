package business.stock;

public class Result {
	private char status;
	private int code;
	public void setStatus(char status) {
		this.status = status;
	}
	public char getStatus() {return status;}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {return code;}
}