package com.jeremiahxu.office.excel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

public class ExcelUtilTest {
	private static String path = "e:/workspace/private/excel-util/src/test/resources/";

	@After
	public void clear() {
		File dir = new File(path + "exports");
		for (File file : dir.listFiles()) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	@Test
	public void testImport2007Excel() {
		String fileName = path + "import1.xlsx";
		Map<String, List<ExcelDataSample>> result = new ExcelUtil<ExcelDataSample>().importExcel(fileName, ExcelDataSample.class);
		List<ExcelDataSample> sheet1 = result.get("Sheet1");
		List<ExcelDataSample> sheet2 = result.get("Sheet2");
		List<ExcelDataSample> sheet3 = result.get("Sheet3");
		Assert.assertEquals("导入的数据数量不对", 4, sheet1.size());
		Assert.assertEquals("导入的数据数量不对", 0, sheet2.size());
		Assert.assertEquals("导入的数据数量不对", 2, sheet3.size());
	}

	@Test
	public void testImport2003Excel() {
		String fileName = path + "import1.xls";
		Map<String, List<ExcelDataSample>> result = new ExcelUtil<ExcelDataSample>().importExcel(fileName, ExcelDataSample.class);
		List<ExcelDataSample> sheet1 = result.get("Sheet1");
		List<ExcelDataSample> sheet2 = result.get("Sheet2");
		List<ExcelDataSample> sheet3 = result.get("Sheet3");
		Assert.assertEquals("导入的数据数量不对", 4, sheet1.size());
		Assert.assertEquals("导入的数据数量不对", 0, sheet2.size());
		Assert.assertEquals("导入的数据数量不对", 2, sheet3.size());
	}

	@Test
	public void testExport2007Excel() {
		String excelFile = path + "exports/export1.xlsx";
		List<ExcelDataSample> data = new ArrayList<ExcelDataSample>();
		Date date = new Date();
		for (int i = 0; i < 10; i++) {
			ExcelDataSample eds = new ExcelDataSample();
			eds.setId(i);
			eds.setCode("code" + i);
			eds.setName("名称" + i);
			eds.setLength(i * 10000);
			eds.setPrice(0.024);
			eds.setDate(date);
			data.add(eds);
		}
		ExcelUtil<ExcelDataSample> eu = new ExcelUtil<ExcelDataSample>();
		eu.exportExcel(excelFile, data, ExcelDataSample.class, ExcelVersion.EXCEL2007);
		Map<String, List<ExcelDataSample>> map = eu.importExcel(excelFile, ExcelDataSample.class);
		List<ExcelDataSample> list = map.get("sheet1");
		for (int i = 0; i < 10; i++) {
			ExcelDataSample eds = list.get(i);
			Assert.assertEquals("导出的id不对", eds.getId(), i);
			Assert.assertEquals("导出的code不对", eds.getCode(), "code" + i);
			Assert.assertEquals("导出的name不对", eds.getName(), "名称" + i);
			Assert.assertEquals("导出的length不对", eds.getLength(), i * 10000);
			Assert.assertEquals("导出的price不对", eds.getPrice(), 0.024);
			Assert.assertEquals("导出的date不对", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eds.getDate()), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date));
		}
	}

	@Test
	public void testExport2003Excel() {
		String excelFile = path + "exports/export2.xls";
		List<ExcelDataSample> data = new ArrayList<ExcelDataSample>();
		Date date = new Date();
		for (int i = 0; i < 10; i++) {
			ExcelDataSample eds = new ExcelDataSample();
			eds.setId(i);
			eds.setCode("code" + i);
			eds.setName("名称" + i);
			eds.setLength(i * 10000);
			eds.setPrice(0.024);
			eds.setDate(date);
			data.add(eds);
		}
		ExcelUtil<ExcelDataSample> eu = new ExcelUtil<ExcelDataSample>();
		eu.exportExcel(excelFile, data, ExcelDataSample.class, ExcelVersion.EXCEL2003);
		Map<String, List<ExcelDataSample>> map = eu.importExcel(excelFile, ExcelDataSample.class);
		List<ExcelDataSample> list = map.get("sheet1");
		for (int i = 0; i < 10; i++) {
			ExcelDataSample eds = list.get(i);
			Assert.assertEquals("导出的id不对", eds.getId(), i);
			Assert.assertEquals("导出的code不对", eds.getCode(), "code" + i);
			Assert.assertEquals("导出的name不对", eds.getName(), "名称" + i);
			Assert.assertEquals("导出的length不对", eds.getLength(), i * 10000);
			Assert.assertEquals("导出的price不对", eds.getPrice(), 0.024);
			Assert.assertEquals("导出的date不对", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eds.getDate()), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date));
		}
	}
}
