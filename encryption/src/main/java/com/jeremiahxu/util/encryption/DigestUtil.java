package com.jeremiahxu.util.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 摘要算法工具类。
 * 
 * @author Jeremiah Xu
 *
 */
public class DigestUtil {
	public final static int BUFF_SIZE = 1024;

	/**
	 * 取得字符串的摘要编码。
	 * 
	 * @param str
	 *            要编码的字符串
	 * @param algorithm
	 *            算法名称
	 * @return 摘要信息
	 */
	public static String encode(String str, String algorithm) {
		if (str == null) {
			return null;
		}
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		msgDigest.update(str.getBytes());
		BigInteger bigInt = new BigInteger(1, msgDigest.digest());
		return bigInt.toString(16);
	}

	/**
	 * 取得一个文件的摘要编码。
	 * 
	 * @param file
	 *            要编码的文件
	 * @param algorithm
	 *            算法名称
	 * @return 摘要信息
	 */
	public static String encode(File file, String algorithm) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[BUFF_SIZE];
		int len;
		try {
			digest = MessageDigest.getInstance(algorithm);
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 取得随机密钥
	 * 
	 * @param algorithm
	 *            算法名称
	 * @return 密钥
	 */
	public static byte[] getSecretKey(String algorithm) {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 取得指定字符串的Hmac算法的摘要信息。
	 * 
	 * @param str
	 *            要摘要的字符串
	 * @param algorithm
	 *            Hmac算法名称
	 * @param keyBytes
	 *            密钥字节数组
	 * @return 摘要信息
	 */
	public static String encodeHmac(String str, String algorithm, byte[] keyBytes) {
		Key key = new SecretKeySpec(keyBytes, algorithm);
		Mac mac = null;
		try {
			mac = Mac.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			mac.init(key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		mac.update(str.getBytes());
		byte[] result = mac.doFinal();
		BigInteger bigInt = new BigInteger(1, result);
		return bigInt.toString(16);
	}

	/**
	 * 取得指定文件的Hmac算法摘要。
	 * 
	 * @param file
	 *            指定文件
	 * @param algorithm
	 *            Hmac算法名称
	 * @param keyBytes
	 *            密钥字节数组
	 * @return 摘要信息
	 */
	public static String encodeHmac(File file, String algorithm, byte[] keyBytes) {
		Key key = new SecretKeySpec(keyBytes, algorithm);
		Mac mac = null;
		try {
			mac = Mac.getInstance(algorithm);
			mac.init(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileInputStream in = null;
		byte buffer[] = new byte[BUFF_SIZE];
		int len;
		try {
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				mac.update(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		byte[] result = mac.doFinal();
		BigInteger bigInt = new BigInteger(1, result);
		return bigInt.toString(16);
	}

}
