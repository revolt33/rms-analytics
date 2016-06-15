package business.customer;

import business.Status;

public class Coupon {
	private String code;
	private Status applicable;
	private String items;
	private float percentage, max;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Status getApplicable() {
		return applicable;
	}

	public void setApplicable(Status applicable) {
		this.applicable = applicable;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}
	
	public boolean isItemList() {
		return applicable==Status.ITEM_LIST;
	}
}
