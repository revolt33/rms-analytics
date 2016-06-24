package business.customer;

public class Address {
	private String name;
	private int id;
	private String house;
	private String street;
	private String landmark;
	private String city;
	private int pin;
	private long contact;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}
	
	public boolean isHavingLandmark() {
		return landmark.length()>0;
	}
	
	public String getAddress() {
		StringBuilder address = new StringBuilder();
		address.append(house).append(", ").append(street).append(", ").append(city);
		return address.toString();
	}
}
