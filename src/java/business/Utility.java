package business;

import java.security.SecureRandom;
import java.math.BigInteger;

public class Utility {
	// create random secure string
	public static String getRandomString(int size) {
		return new BigInteger(130, new SecureRandom()).toString(32).substring(0, size);
	}
}