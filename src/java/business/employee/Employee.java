package business.employee;

import business.Status;

public class Employee {

	private String name;
	private char type;
	private String username;
	private String mobile;
	private String email;
	private String dob;
	private String photo;
	private String colony;
	private String city;
	private String state;
	private int zip;
	private String landmark;
	private char gender;
	private char active;
	private int id;
	private String street;
	private String imageExt;
	
	public void setImageExt(String imageExt) {
		this.imageExt = imageExt;
	}
	public String getImageExt() {
		return imageExt;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet () {
		return street;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getType() {
		return type;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDob() {
		return dob;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getColony() {
		return colony;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getZip() {
		return zip;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public char getGender() {
		return gender;
	}

	public String getEmpGender() {
		switch (gender) {
			case 'm':
				return "Male";
			case 'f':
				return "Female";
			case 'o':
				return "Other";
			default:
				return "Unknown";
		}
	}

	public String getEmpType() {
		switch (Status.parseEmployee(type)) {
			case MANAGER:
				return "Manager";
			case SALES_EMPLOYEE:
				return "Sales";
			case STOCK_EMPLOYEE:
				return "Stock";
			default:
				return "Unknown";
		}
	}

	public void setActive(char active) {
		this.active = active;
	}

	public char getActive() {
		return active;
	}

	public boolean getIsActive() {
		return active == 'y';
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() { return id;}
}
