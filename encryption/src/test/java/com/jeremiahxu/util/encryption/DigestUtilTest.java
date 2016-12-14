package com.jeremiahxu.util.encryption;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class DigestUtilTest {

	private static String path = "e:/workspace/private/encryption/src/test/resources/";

	@Test
	public void testEncodeString() {
		String code = DigestUtil.encode("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.MD5);
		String codeCom = "3bd694e7bfa64026e333b970215de9df";
		Assert.assertEquals("字符串的MD5码生成的不正确", codeCom, code);
		code = DigestUtil.encode("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.SHA);
		codeCom = "36209c311b77656306cbc3dda0d62056f0dce2d9";
		Assert.assertEquals("字符串的SHA码生成的不正确", codeCom, code);
		code = DigestUtil.encode("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.SHA256);
		codeCom = "2353ab474ca442a971fcff662318fbf4db2e626f66f5299977fa0935b47bdec1";
		Assert.assertEquals("字符串的SHA-256码生成的不正确", codeCom, code);
		code = DigestUtil.encode("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.SHA384);
		codeCom = "89420b401563b66a5df1929148f51e9fb5f77d1195f0cb69c474f33a16c60f319754dcf1a5aa173b0fde4ca89d2d9828";
		Assert.assertEquals("字符串的SHA-384码生成的不正确", codeCom, code);
		code = DigestUtil.encode("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.SHA512);
		codeCom = "d01b6f9e472741050d5e3bae8a9be4117532d0bd5e6236ccd5334010598ee76d2b784fd0a35398941dd55c71ed9faa5879ecd45a6e7ae584d0b71b6005b964fe";
		Assert.assertEquals("字符串的SHA-512码生成的不正确", codeCom, code);
	}

	@Test
	public void testEncodeFile() {
		File file = new File(path + "test.txt");
		String code = DigestUtil.encode(file, DigestAlgorithm.MD5);
		String codeCom = "3bd694e7bfa64026e333b970215de9df";
		Assert.assertEquals("文件的MD5码生成的不正确", codeCom, code);
		code = DigestUtil.encode(file, DigestAlgorithm.SHA);
		codeCom = "36209c311b77656306cbc3dda0d62056f0dce2d9";
		Assert.assertEquals("文件的SHA码生成的不正确", codeCom, code);
		code = DigestUtil.encode(file, DigestAlgorithm.SHA256);
		codeCom = "2353ab474ca442a971fcff662318fbf4db2e626f66f5299977fa0935b47bdec1";
		Assert.assertEquals("文件的SHA-256码生成的不正确", codeCom, code);
		code = DigestUtil.encode(file, DigestAlgorithm.SHA384);
		codeCom = "89420b401563b66a5df1929148f51e9fb5f77d1195f0cb69c474f33a16c60f319754dcf1a5aa173b0fde4ca89d2d9828";
		Assert.assertEquals("文件的SHA-384码生成的不正确", codeCom, code);
		code = DigestUtil.encode(file, DigestAlgorithm.SHA512);
		codeCom = "d01b6f9e472741050d5e3bae8a9be4117532d0bd5e6236ccd5334010598ee76d2b784fd0a35398941dd55c71ed9faa5879ecd45a6e7ae584d0b71b6005b964fe";
		Assert.assertEquals("文件的SHA-512码生成的不正确", codeCom, code);
	}

	@Test
	public void testEncodeHmacString() {
		byte[] key = { 15, 10, 1, 0, 127, 64 };
		String code = DigestUtil.encodeHmac("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.HmacMD5, key);
		String codeCom = "6b7f8db97f5e52390ce0d9a38a488cb1";
		Assert.assertEquals("字符串的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.HmacSHA1, key);
		codeCom = "c229a5aeb91a6d9832b9ae746e0adc4d07dea697";
		Assert.assertEquals("字符串的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.HmacSHA256, key);
		codeCom = "750002cd3224124de122c672fd217a951d1e4f04831b063e7ef5f00786d9927a";
		Assert.assertEquals("字符串的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.HmacSHA384, key);
		codeCom = "87f789aec5e7bd8cca116449149be49ff14b896febd612f1ac50de805bd6d9c46b34cedffc99bc75f4ae4b616fb1c3fb";
		Assert.assertEquals("字符串的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac("This is a character string of hexadecimal digits that is unique for all partitio", DigestAlgorithm.HmacSHA512, key);
		codeCom = "d73063354fa31ebb16b90de8796d075dde0c906cca2a31d4a8d8822099677b986253a36cdd66d1a6a5d736e6d8879970f52ff2c4ab5b7379a6ec35805e4a1915";
		Assert.assertEquals("字符串的摘要信息不正确", codeCom, code);
	}

	@Test
	public void testEncodeHmacFile() {
		byte[] key = { 15, 10, 1, 0, 127, 64 };
		File file = new File(path + "test.txt");
		String code = DigestUtil.encodeHmac(file, DigestAlgorithm.HmacMD5, key);
		String codeCom = "6b7f8db97f5e52390ce0d9a38a488cb1";
		Assert.assertEquals("文件的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac(file, DigestAlgorithm.HmacSHA1, key);
		codeCom = "c229a5aeb91a6d9832b9ae746e0adc4d07dea697";
		Assert.assertEquals("文件的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac(file, DigestAlgorithm.HmacSHA256, key);
		codeCom = "750002cd3224124de122c672fd217a951d1e4f04831b063e7ef5f00786d9927a";
		Assert.assertEquals("文件的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac(file, DigestAlgorithm.HmacSHA384, key);
		codeCom = "87f789aec5e7bd8cca116449149be49ff14b896febd612f1ac50de805bd6d9c46b34cedffc99bc75f4ae4b616fb1c3fb";
		Assert.assertEquals("文件的摘要信息不正确", codeCom, code);
		code = DigestUtil.encodeHmac(file, DigestAlgorithm.HmacSHA512, key);
		codeCom = "d73063354fa31ebb16b90de8796d075dde0c906cca2a31d4a8d8822099677b986253a36cdd66d1a6a5d736e6d8879970f52ff2c4ab5b7379a6ec35805e4a1915";
		Assert.assertEquals("文件的摘要信息不正确", codeCom, code);
	}
}
