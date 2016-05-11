package business.stock;

public class Ingredient {

	private String name;
	private int id;
	private float stock;
	private char state;
	private String added_by;

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {return name;}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {return id;}
	public void setStock(double stock) {
		this.stock = new Double(stock).floatValue();
	}
	public float getStock() {return stock;}
	public void setState(char state) {
		this.state = state;
	}
	public char getState() {return state;}
	public void setAddedBy(String added_by) {
		this.added_by = added_by;
	}
	public String getAddedBy() {return added_by;}
}