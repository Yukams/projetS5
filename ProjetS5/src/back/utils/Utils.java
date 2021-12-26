package back.utils;

import java.util.Random;

import static java.lang.Math.abs;

public class Utils {

	public static int createRandomId() {
		Random r = new Random();
		return abs(r.nextInt());
	}

	public static String createRandomPassword() {
		// TODO
		return "";
	}

}
