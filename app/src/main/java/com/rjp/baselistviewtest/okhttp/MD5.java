package com.rjp.baselistviewtest.okhttp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	public static String toMd5(byte[] bytes) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");

			algorithm.update(bytes);
			return toHexString(algorithm.digest());
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[((b[i] & 0xF0) >>> 4)]);
			sb.append(HEX_DIGITS[(b[i] & 0xF)]);
		}
		return sb.toString();
	}

	
}
