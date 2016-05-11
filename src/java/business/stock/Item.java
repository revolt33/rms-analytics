package business.stock;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Item {

	private final ArrayList<ItemUnit> itemUnits = new ArrayList<>();
	private ItemUnit defaultUnit;
	private int category;
	private int id;
	private String name;
	private String image;
	private String description;
	private float minPercentage;
	private float stock;
	private char onOrder;
	private char delivery;
	private int oneStar;
	private int twoStar;
	private int threeStar;
	private int fourStar;
	private int fiveStar;
	private int userId; // Id of employee who Added this item
	private String userName; // Name of employee who added this item
	private char active;
	private String addedBy;
	private String imageExt;
	private boolean hasUnits = false;
	private float price;

	public void setCategory(int category) {
		this.category = category;
	}

	public int getCategory() {
		return category;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setMinPercentage(double minPercentage) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		this.minPercentage = Float.parseFloat(df.format(minPercentage));
	}

	public float getMinPercentage() {
		return minPercentage;
	}

	public void setStock(double stock) {
		this.stock = new Double(stock).floatValue();
	}

	public float getStock() {
		return stock;
	}

	public void setOnOrder(char onOrder) {
		this.onOrder = onOrder;
	}

	public boolean getOnOrder() {
		return onOrder == 'y';
	}

	public String getProducedOnOrder() {
		return onOrder == 'y' ? "Yes" : "No";
	}

	public void setDelivery(char delivery) {
		this.delivery = delivery;
	}

	public boolean getDelivery() {
		return delivery == 'y';
	}

	public String getIsEligibleForDelivery() {
		return delivery == 'y' ? "Eligible" : "Not Eligible";
	}

	public void setOneStar(int oneStar) {
		this.oneStar = oneStar;
	}

	public int getOneStar() {
		return oneStar;
	}

	public void setTwoStar(int twoStar) {
		this.twoStar = twoStar;
	}

	public int getTwoStar() {
		return twoStar;
	}

	public void setThreeStar(int threeStar) {
		this.threeStar = threeStar;
	}

	public int getThreeStar() {
		return threeStar;
	}

	public void setFourStar(int fourStar) {
		this.fourStar = fourStar;
	}

	public int getFourStar() {
		return fourStar;
	}

	public void setfiveStar(int fiveStar) {
		this.fiveStar = fiveStar;
	}

	public int getfiveStar() {
		return fiveStar;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setActive(char active) {
		this.active = active;
	}

	public boolean getActive() {
		return active == 'y';
	}

	public String getIsActive() {
		return active == 'y' ? "Yes" : "No";
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setImageExt(String imageExt) {
		this.imageExt = imageExt;
	}

	public String getImageExt() {
		return imageExt;
	}

	public ArrayList<ItemUnit> getItemUnits() {
		return itemUnits;
	}

	public void addItemUnits(ItemUnit itemUnit) {
		if ( itemUnit.getType() == 'd' && itemUnit.getStatus()) {
			defaultUnit = itemUnit;
		}
		if (itemUnit.getType() == 'o' && itemUnit.getStatus()) {
			hasUnits = true;
		}
		this.itemUnits.add(itemUnit);
	}

	public ItemUnit getDefaultUnit() {
		return defaultUnit;
	}

	public boolean getHasUnits() {
		return hasUnits;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
