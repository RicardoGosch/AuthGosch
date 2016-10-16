package net.fycraft.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	public static String encrypt(String noEncrypt) {
		try {
			MessageDigest message = MessageDigest.getInstance("MD5");

			message.update(noEncrypt.getBytes(), 0, noEncrypt.length());
			return new BigInteger(1, message.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean compare(String encrypt, String noEncrypt){
		return encrypt.equals(encrypt(noEncrypt));
	}
}
