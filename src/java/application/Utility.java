package application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
}