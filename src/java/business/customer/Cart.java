package business.customer;

import application.Utility;
import java.util.ArrayList;

public class Cart {
	
	private final ArrayList<CartItem> list = new ArrayList<>();
	private float total = 0f;
	private int size;
	private boolean available = true;
	private String coupon;
	private int discountId;
	private float effectiveValue = 0f;
	private float discount = 0f;
	private boolean couponApplied = false;
	private boolean couponValid = false;
	private boolean havingPickup = false;
	private boolean havingAddress = false;
	private Address address = new Address();
	
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
	
	public float getFormattedTotal() {
		return Utility.getFormattedFloat(total, 0, 2);
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
		this.effectiveValue = Utility.getFormattedFloat(effectiveValue, 0, 2);
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

	public boolean isHavingPickup() {
		return havingPickup;
	}

	public void setHavingPickup(boolean havingPickup) {
		this.havingPickup = havingPickup;
	}

	public boolean isHavingAddress() {
		return havingAddress;
	}

	public void setHavingAddress(boolean havingAddress) {
		this.havingAddress = havingAddress;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getDeliveryMode() {
		if ( havingAddress ) {
			return "d";
		} else if ( havingPickup ) {
			return "p";
		} else {
			return "u";
		}
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = Utility.getFormattedFloat(discount, 0, 2);
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}
}