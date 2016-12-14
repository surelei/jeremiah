package com.jeremiahxu.util.sqlbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class SQLBuilderTest {
	private static String path = "e:/workspace/private/sql-builder/src/test/resources/";

	public void clear() {
		File dir = new File(path + "exports");
		for (File file : dir.listFiles()) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	private static String getMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
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

	@Test
	public void testMakeSQL() throws Exception {
		String inputFile = path + "test.sql";
		String outputFile = path + "exports/" + new Date().getTime() + ".sql";
		List<SQLBuilderDataSample> data = new ArrayList<SQLBuilderDataSample>();
		for (int i = 0; i < 10; i++) {
			SQLBuilderDataSample d = new SQLBuilderDataSample();
			d.setId(i);
			d.setName("name" + i);
			d.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(("2016-11-30")));
			d.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(("2016-11-30 12:23:59")));
			d.setTime(new SimpleDateFormat("HH:mm:ss").parse(("12:23:59")));
			d.setDoubleNumber(i + 999999.99);
			d.setIntNumber(i + 100);
			data.add(d);
		}
		new SQLBuilder<SQLBuilderDataSample>().makeSQL(inputFile, outputFile, data, SQLBuilderDataSample.class);
		String resultFile = path + "result.sql";
		String code1 = getMD5(new File(resultFile));
		String code2 = getMD5(new File(outputFile));
		Assert.assertEquals("生成的sql文件不正确！", code1, code2);
	}
}