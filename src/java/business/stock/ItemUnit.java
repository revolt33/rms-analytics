package business.stock;

public class ItemUnit {
	private int id;
	private String name;
	private float fraction;
	private char status;
	private char type;
	private String added_by;
	private float price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getFraction() {
		return fraction;
	}

	public void setFraction(float fraction) {
		this.fraction = fraction;
	}

	public String getAdded_by() {
		return added_by;
	}

	public void setAdded_by(String added_by) {
		this.added_by = added_by;
	}

	public boolean getStatus() {
		return status=='a';
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getType() {
		return type;
	}
	
	public boolean getIsDefault() {
		return type=='d';
	}

	public void setType(char type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
