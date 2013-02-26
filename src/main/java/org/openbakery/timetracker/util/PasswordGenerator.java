package org.openbakery.timetracker.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordGenerator {

	protected static char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

	public static String generate(int length) {

		StringBuilder builder = new StringBuilder();

		byte[] bytes = new byte[length];

		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		random.nextBytes(bytes);

		for (byte b : bytes) {
			builder.append(chars[(b & 0xFF) % chars.length]);
		}

		return builder.toString();

	}

	public static void main(String[] args) {
		System.out.println(generate(10));
		System.out.println(generate(15));
		System.out.println(generate(20));
	}
}
