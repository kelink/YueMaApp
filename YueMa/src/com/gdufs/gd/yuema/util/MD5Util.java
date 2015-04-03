package com.gdufs.gd.yuema.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具包
 * 
 * @author Administrator
 * 
 */
public class MD5Util {
	/* md5 加密 */
	public static String md5Encryption(String str) {
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (algorithm != null) {
			algorithm.reset();
			algorithm.update(str.getBytes());
			byte[] bytes = algorithm.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte b : bytes) {
				hexString.append(Integer.toHexString(0xFF & b));
			}
			return hexString.toString();
		}
		return "";

	}
}
