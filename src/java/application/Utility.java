package application;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utility {
	public static String getSuitableExtension(String type) {
		switch (type) {
			case "image/png":
				return ".png";
			case "image/jpg":
				return ".jpg";
			case "image/jpeg":
				return ".jpeg";
			default:
				return "";
		}
	}
	public static long parseInputDateTime(String input) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
		Calendar time = Calendar.getInstance();
		try {
			StringBuilder str = new StringBuilder(input);
			int index = str.indexOf("T");
			str.replace(index, index + 1, ":");
			time.setTime(df.parse(str.toString()));
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return time.getTimeInMillis();
	}
	public static String getFormattedDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		cal.setTimeInMillis(time);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		return format.format(cal.getTime());
	}
	
	public static boolean isSuitableExtension(String type) {
		return (type.equals("image/png") || type.equals("image/jpg") || type.equals("image/jpeg"));
	}
	
	public static float getFormattedFloat(float value, int min, int max) {
		NumberFormat formatter = NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(max);
		formatter.setMinimumFractionDigits(min);
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		return Float.parseFloat(formatter.format(value));
	}
	
	public static long getTimestamp() {
		Calendar timestamp = Calendar.getInstance();
		timestamp.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		return timestamp.getTimeInMillis();
	}
}
