package business.stock;

import application.Utility;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Entry {

	private int id;
	private long expiry;
	private long timestamp;
	private float amount;
	private float consumed;
	private float stock;
	private String addedBy;
	private char status;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the expiry
	 */
	public long getExpiry() {
		return expiry;
	}
	
	public String getExpiryDateTime() {
		return Utility.getFormattedDate(expiry);
	}

	/**
	 * @param expiry the expiry to set
	 */
	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getAddedTimestamp() {
		return Utility.getFormattedDate(timestamp);
	}
	
	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the consumed
	 */
	public float getConsumed() {
		return consumed;
	}

	/**
	 * @param consumed the consumed to set
	 */
	public void setConsumed(float consumed) {
		this.consumed = consumed;
	}

	/**
	 * @return the stock
	 */
	public float getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(float stock) {
		this.stock = stock;
	}

	/**
	 * @return the addedBy
	 */
	public String getAddedBy() {
		return addedBy;
	}

	/**
	 * @param addedBy the addedBy to set
	 */
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	/**
	 * @return the status
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(char status) {
		this.status = status;
	}
	public boolean getIsActive() {
		return status=='a';
	}
}
