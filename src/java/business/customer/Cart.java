package business.customer;

import java.util.ArrayList;

public class Cart {
	
	private final ArrayList<CartItem> list = new ArrayList<>();
	private float total = 0f;
	private int size;
	private boolean available = true;
	private String coupon;
	private float effectiveValue = 0f;
	private boolean couponApplied = false;
	private boolean couponValid = false;
	
	public void addCartItem( CartItem item ) {
		list.add(item);
		if ( item.isAvailable() && item.isValid() ) {
			total += item.getPrice() * item.getQuantity();
			size++;
		} else
			available = false;
	}
	
	public void flush() {
		total = 0;
		effectiveValue = 0;
		coupon = "";
		couponApplied = false;
		couponValid = false;
		size = 0;
		list.clear();
	}
	
	public float getTotal() {
		return total;
	}
	
	public ArrayList<CartItem> getList() {
		return list;
	}

	public int getSize() {
		return size;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public float getEffectiveValue() {
		return effectiveValue;
	}

	public void setEffectiveValue(float effectiveValue) {
		this.effectiveValue = effectiveValue;
	}

	public boolean isCouponApplied() {
		return couponApplied;
	}

	public void setCouponApplied(boolean couponApplied) {
		this.couponApplied = couponApplied;
	}

	public boolean isCouponValid() {
		return couponValid;
	}

	public void setCouponValid(boolean couponValid) {
		this.couponValid = couponValid;
	}

	public boolean isAvailable() {
		return available;
	}
}