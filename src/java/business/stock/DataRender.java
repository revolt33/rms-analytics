package business.stock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DataRender {
	
	private float floatData;
	private long date;
	private String string;
	private int integer;
	private long longData;
	private double doubleData;

	public float getFloatData() {
		return floatData;
	}

	public void setFloatData(float floatData) {
		this.floatData = floatData;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
	public String getFormattedDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		cal.setTimeInMillis(date);
		SimpleDateFormat format = new SimpleDateFormat("MMM dd");
		return format.format(cal.getTime());
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}

	public long getLongData() {
		return longData;
	}

	public void setLongData(long longData) {
		this.longData = longData;
	}

	public long getDate() {
		return date;
	}

	public double getDoubleData() {
		return doubleData;
	}

	public void setDoubleData(double doubleData) {
		this.doubleData = doubleData;
	}
}
