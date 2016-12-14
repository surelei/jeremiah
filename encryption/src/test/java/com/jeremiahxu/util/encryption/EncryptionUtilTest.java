package com.jeremiahxu.util.encryption;

import junit.framework.Assert;

import org.junit.Test;

public class EncryptionUtilTest {

	@Test
	public void testEncryptDES() {
		String key = "thisisakey";
		String data = "abcdefgh";
		String str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.ECB, PaddingMode.NoPadding);
		String str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.ECB, PaddingMode.NoPadding);
		Assert.assertEquals("ECB/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.CBC, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.CBC, PaddingMode.NoPadding);
		Assert.assertEquals("CBC/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.CFB, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.CFB, PaddingMode.NoPadding);
		Assert.assertEquals("CFB/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.OFB, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.OFB, PaddingMode.NoPadding);
		Assert.assertEquals("OFB/NoPadding加密或解密不正确", data, str2);

		data = "The all-volunteer ASF develops, stewards, and incubates more than 350 Open Source projects and initiatives that cover a wide range of technologies.";
		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("ECB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.CBC, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.CBC, PaddingMode.PKCS5Padding);
		Assert.assertEquals("CBC/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.CFB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.CFB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("CFB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.OFB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.OFB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("OFB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.ECB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.ECB, PaddingMode.ISOPadding);
		Assert.assertEquals("ECB/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.CBC, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.CBC, PaddingMode.ISOPadding);
		Assert.assertEquals("CBC/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.CFB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.CFB, PaddingMode.ISOPadding);
		Assert.assertEquals("CFB/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.DES, EncryptionMode.OFB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.DES, EncryptionMode.OFB, PaddingMode.ISOPadding);
		Assert.assertEquals("OFB/ISOPadding加密或解密不正确", data, str2);

	}
	
	@Test
	public void testEncrypt3DES() {
		String key = "thisisakeythisisakeythisisakey";
		String data = "abcdefgh";
		String str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.ECB, PaddingMode.NoPadding);
		String str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.ECB, PaddingMode.NoPadding);
		Assert.assertEquals("ECB/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CBC, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CBC, PaddingMode.NoPadding);
		Assert.assertEquals("CBC/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CFB, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CFB, PaddingMode.NoPadding);
		Assert.assertEquals("CFB/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.OFB, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.OFB, PaddingMode.NoPadding);
		Assert.assertEquals("OFB/NoPadding加密或解密不正确", data, str2);

		data = "The all-volunteer ASF develops, stewards, and incubates more than 350 Open Source projects and initiatives that cover a wide range of technologies.";
		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("ECB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CBC, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CBC, PaddingMode.PKCS5Padding);
		Assert.assertEquals("CBC/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CFB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CFB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("CFB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.OFB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.OFB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("OFB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.ECB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.ECB, PaddingMode.ISOPadding);
		Assert.assertEquals("ECB/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CBC, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CBC, PaddingMode.ISOPadding);
		Assert.assertEquals("CBC/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CFB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.CFB, PaddingMode.ISOPadding);
		Assert.assertEquals("CFB/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.OFB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.TRIPLEDES, EncryptionMode.OFB, PaddingMode.ISOPadding);
		Assert.assertEquals("OFB/ISOPadding加密或解密不正确", data, str2);

	}

	@Test
	public void testEncryptAES() {
		String key = "thisisakeythisisakeythisisakey";
		String data = "abcdefgh12345678";
		String str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.ECB, PaddingMode.NoPadding);
		String str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.ECB, PaddingMode.NoPadding);
		Assert.assertEquals("ECB/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.CBC, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.CBC, PaddingMode.NoPadding);
		Assert.assertEquals("CBC/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.CFB, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.CFB, PaddingMode.NoPadding);
		Assert.assertEquals("CFB/NoPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.OFB, PaddingMode.NoPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.OFB, PaddingMode.NoPadding);
		Assert.assertEquals("OFB/NoPadding加密或解密不正确", data, str2);

		data = "The all-volunteer ASF develops, stewards, and incubates more than 350 Open Source projects and initiatives that cover a wide range of technologies.";
		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.ECB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("ECB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.CBC, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.CBC, PaddingMode.PKCS5Padding);
		Assert.assertEquals("CBC/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.CFB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.CFB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("CFB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.OFB, PaddingMode.PKCS5Padding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.OFB, PaddingMode.PKCS5Padding);
		Assert.assertEquals("OFB/PKCS5Padding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.ECB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.ECB, PaddingMode.ISOPadding);
		Assert.assertEquals("ECB/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.CBC, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.CBC, PaddingMode.ISOPadding);
		Assert.assertEquals("CBC/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.CFB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.CFB, PaddingMode.ISOPadding);
		Assert.assertEquals("CFB/ISOPadding加密或解密不正确", data, str2);

		str1 = EncryptionUtil.encrypt(data, key, EncryptionAlgorithm.AES, EncryptionMode.OFB, PaddingMode.ISOPadding);
		str2 = EncryptionUtil.decrypt(str1, key, EncryptionAlgorithm.AES, EncryptionMode.OFB, PaddingMode.ISOPadding);
		Assert.assertEquals("OFB/ISOPadding加密或解密不正确", data, str2);

	}
}
