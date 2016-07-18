package business.sales;

import application.Utility;
import business.Status;
import java.util.ArrayList;

public class Order {
	private int id;
	private float value;
	private float effectiveValue;
	private Status mode;
	private int customer;
	private int employee;
	private String customerName;
	private String employeeName;
	private Status deliveryMode;
	private Status paymentStatus;
	private String discountCode;
	private boolean couponApplied = false;
	private int discount;
	private long timestamp;
	private int deliveredBy;
	private Status orderSatatus;
	private String address;
	private final ArrayList<Info> list;
	private long estimatedDelivery;
	private long contact;

	public Order() {
		this.list = new ArrayList<>();
	}
	
	public void addInfo(Info info) {
		list.add(info);
	}
	
	public ArrayList<Info> getInfo() {
		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getEffectiveValue() {
		return effectiveValue;
	}

	public void setEffectiveValue(float effectiveValue) {
		this.effectiveValue = effectiveValue;
	}

	public Status getMode() {
		return mode;
	}

	public void setMode(Status mode) {
		this.mode = mode;
	}

	public int getCustomer() {
		return customer;
	}

	public void setCustomer(int customer) {
		this.customer = customer;
	}

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}

	public Status getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(Status deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Status getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Status paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public String getFormattedTimestamp() {
		return Utility.getFormattedDate(timestamp);
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(int deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public Status getOrderSatatus() {
		return orderSatatus;
	}

	public void setOrderSatatus(Status orderSatatus) {
		this.orderSatatus = orderSatatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(long estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}
	
	public String getFormattedEstimatedDelivery() {
		return Utility.getFormattedDate(estimatedDelivery);
	}

	public boolean isAttended() {
		return orderSatatus==Status.ATTENDED;
	}
	
	public boolean isPickup() {
		return deliveryMode==Status.PICKUP;
	}
	
	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public boolean isCouponApplied() {
		return couponApplied;
	}

	public void setCouponApplied(boolean couponApplied) {
		this.couponApplied = couponApplied;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public float getDiscountedValue() {
		return Utility.getFormattedFloat(value-effectiveValue, 0, 1);
	}
	
	public String getOrderStatusValue() {
		return orderSatatus.toString();
	}
	
	public String getOrderStatusClass() {
		switch ( orderSatatus ) {
			case PENDING:
				return "label-warning";
			case ATTENDED:
				return "label-info";
			case DELIVERED:
				return "label-success";
			default:
				return "label-danger";
		}
	}
	
	public boolean isHomeDelivery() {
		return deliveryMode==Status.DELIVERY;
	}
	
	public class Info {
		private int quantity;
		private float price;
		private int priceId;
		private String name;

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPriceId() {
			return priceId;
		}

		public void setPriceId(int priceId) {
			this.priceId = priceId;
		}
		
		public float getAmount() {
			return Utility.getFormattedFloat(price*quantity, 0, 2);
		}
	}
}
