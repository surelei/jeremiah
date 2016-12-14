package com.jeremiahxu.util.encryption;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加密工具类
 * 
 * @author Jeremiah Xu
 *
 */
public class EncryptionUtil {

	/**
	 * 生成密钥
	 * 
	 * @param keyStr
	 *            密钥字符串
	 * @param algorithm
	 *            算法名称
	 * @return
	 */
	private static SecretKey keyGenerator(String keyStr, String algorithm) {
		try {
			SecretKey secreKey = null;
			if (algorithm.equals(EncryptionAlgorithm.DES)) {
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(EncryptionAlgorithm.DES);
				DESKeySpec desKey = new DESKeySpec(keyStr.getBytes());
				secreKey = keyFactory.generateSecret(desKey);
			} else if (algorithm.equals(EncryptionAlgorithm.TRIPLEDES)) {
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(EncryptionAlgorithm.TRIPLEDES);
				DESedeKeySpec desKey = new DESedeKeySpec(keyStr.getBytes());
				secreKey = keyFactory.generateSecret(desKey);
			} else if (algorithm.equals(EncryptionAlgorithm.AES)) {
				KeyGenerator kgen = KeyGenerator.getInstance(EncryptionAlgorithm.AES);
				kgen.init(128, new SecureRandom(keyStr.getBytes()));
				secreKey = kgen.generateKey();
			}
			return secreKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            要加密的数据
	 * @param key
	 *            密钥字符串
	 * @param algorithm
	 *            算法名称
	 * @param encryptionMode
	 *            加密模式
	 * @param paddingMode
	 *            填充模式
	 * @return 加密后的数据
	 */
	public static String encrypt(String data, String key, String algorithm, String encryptionMode, String paddingMode) {
		Key deskey = keyGenerator(key, algorithm);
		// 实例化Cipher对象，它用于完成实际的加密操作
		StringBuffer alg = new StringBuffer();
		alg.append(algorithm).append("/").append(encryptionMode).append("/").append(paddingMode);
		try {
			Cipher cipher = Cipher.getInstance(alg.toString());
			// 初始化Cipher对象，设置为加密模式
			if (encryptionMode.equals(EncryptionMode.ECB)) {
				cipher.init(Cipher.ENCRYPT_MODE, deskey, new SecureRandom());
			} else {
				if (algorithm.equals(EncryptionAlgorithm.AES)) {
					cipher.init(Cipher.ENCRYPT_MODE, deskey, new IvParameterSpec(key.getBytes(), 0, 16));
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, deskey, new IvParameterSpec(key.getBytes(), 0, 8));
				}
			}
			byte[] results = cipher.doFinal(data.getBytes());
			// 执行加密操作。加密后的结果通常都会用Base64编码进行传输
			return Base64.getEncoder().encodeToString(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 默认的加密方法
	 * 
	 * @param data
	 *            要加密的数据
	 * @param key
	 *            密钥字符串
	 * @param algorithm
	 *            算法名称
	 * @return 加密后的数据
	 */
	public static String encrypt(String data, String key, String algorithm) {
		return encrypt(data, key, algorithm, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            密钥字符串
	 * @param algorithm
	 *            算法名称
	 * @param encryptionMode
	 *            加密模式
	 * @param paddingMode
	 *            填充模式
	 * @return 解密后的数据
	 */
	public static String decrypt(String data, String key, String algorithm, String encryptionMode, String paddingMode) {
		Key deskey = keyGenerator(key, algorithm);
		StringBuffer alg = new StringBuffer();
		alg.append(algorithm).append("/").append(encryptionMode).append("/").append(paddingMode);
		try {
			Cipher cipher = Cipher.getInstance(alg.toString());
			// 初始化Cipher对象，设置为解密模式
			if (encryptionMode.equals(EncryptionMode.ECB)) {
				cipher.init(Cipher.DECRYPT_MODE, deskey, new SecureRandom());
			} else {
				if (algorithm.equals(EncryptionAlgorithm.AES)) {
					cipher.init(Cipher.DECRYPT_MODE, deskey, new IvParameterSpec(key.getBytes(), 0, 16));
				} else {
					cipher.init(Cipher.DECRYPT_MODE, deskey, new IvParameterSpec(key.getBytes(), 0, 8));
				}
			}
			// 执行解密操作
			return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 默认解密方法
	 * 
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            密钥字符串
	 * @param algorithm
	 *            算法名称
	 * @return 解密后的数据
	 */
	public static String decrypt(String data, String key, String algorithm) {
		return decrypt(data, key, algorithm, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
	}
}
