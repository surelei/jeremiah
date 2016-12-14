package com.jeremiahxu.office.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeremiahxu.office.excel.anno.CellMapping;
import com.jeremiahxu.office.excel.anno.ExcelMapping;

/**
 * simple excel import&export tools
 * 
 * @author Jeremiah Xu
 * 
 */
public class ExcelUtil<T> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * excel列名转列号
	 * 
	 * @param columnName
	 * @return
	 */
	public int colName2colNo(String columnName) {
		// 转成大写
		String str = columnName.toUpperCase();
		// 判断是否都是字母
		boolean flag = str.matches("[A-Z]+");
		if (flag) {// 如果都是字母则转换成数字
			int colNo = 0;
			for (int i = 0; i < str.length(); i++) {
				int index = str.length() - i - 1;
				String ch = str.substring(index, index + 1);
				colNo += (ch.getBytes()[0] - 65) + Math.pow(26, i) - 1;
			}
			return colNo;
		} else {// 如果不全是字母则不处理
			log.error("column's name[" + columnName + "] is wrong.");
			throw new ExcelException("column's name[" + columnName + "] is wrong.");
		}
	}

	/**
	 * 导入指定excel到指定类，可以设置是否记录错误日志。
	 * 
	 * @param importFile
	 * @param resultClass
	 * @param log
	 * @return
	 */
	private ExcelImportResult<T> importExcel(FileInputStream importFile, Class<T> resultClass,
			boolean log) {
		// 如果类中不存在指定的注解配置，则不能进行excel导入。
		if (!resultClass.isAnnotationPresent(ExcelMapping.class)) {
			this.log.error("class[" + resultClass.getName() + "] needs mapping config.");
			throw new ExcelException("class[" + resultClass.getName() + "] needs mapping config.");
		}
		// 取得该类所有字段和注解配置
		Map<String, CellMeta> fieldMapping = new HashMap<String, CellMeta>();
		Field[] fields = resultClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(CellMapping.class)) {
				// 取得字段的注解信息
				CellMapping cell = field.getAnnotation(CellMapping.class);
				// 字段名
				String fieldName = field.getName();
				// 对应excel中列名
				String columnName = cell.name().toUpperCase();
				// 对应excel列号
				int columnNo = this.colName2colNo(columnName);
				// 列名注解配置不能为空
				if (columnName.length() == 0) {
					this.log.error("class[" + resultClass.getName() + "] field[" + fieldName
							+ "] mapping config can't be ''.");
					throw new ExcelException("class[" + resultClass.getName() + "] field["
							+ fieldName + "] mapping config can't be ''.");
				}
				// excel表格中对应的类型
				CellType type = cell.type();
				// 将配置信息暂存
				CellMeta cm = new CellMeta();
				cm.setColumnName(columnName);
				cm.setColumnNo(columnNo);
				cm.setField(fieldName);
				cm.setType(type);
				fieldMapping.put(String.valueOf(columnNo), cm);
			}
		}
		// 如果没有一个字段有映射关系的注解配置，则不能进行导入。
		if (fieldMapping.size() == 0) {
			this.log.error("class[" + resultClass.getName()
					+ "] needs a field mapping config at least.");
			throw new ExcelException("class[" + resultClass.getName()
					+ "] needs a field mapping config at least.");
		}
		// 取得类指定注解配置信息
		ExcelMapping em = resultClass.getAnnotation(ExcelMapping.class);
		int beginRow = em.begin() - 1;
		List<String> sheetNameList = Arrays.asList(em.sheets());
		// 定义导入的结果集
		ExcelImportResult<T> result = new ExcelImportResult<T>();
		Workbook wb = null;
		// 打开excel文件
		try {
			wb = WorkbookFactory.create(importFile);
		} catch (Exception e) {
			this.log.error("", e);
			throw new ExcelException(e);
		} finally {
			try {
				importFile.close();
			} catch (IOException e) {
				this.log.error("", e);
				throw new ExcelException(e);
			}
		}
		// 循环遍历sheet页
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			List<T> data = new ArrayList<T>();
			// 取得sheet页
			Sheet sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			// 判断该sheet页是否在映射配置中，如果配置中未指定sheet页名称，则所有sheet页都要导入，否则不导入该sheet页。
			if (sheetNameList.size() > 0 && !sheetNameList.contains(sheetName)) {
				continue;
			}
			// 循环遍历每一行
			for (int j = beginRow; j <= sheet.getLastRowNum(); j++) {
				// 取得一行
				Row row = sheet.getRow(j);
				// 如果没有记录则继续下一个sheet
				if (row == null) {
					continue;
				}
				// 创建一个对应的对象
				T obj;
				try {
					obj = resultClass.newInstance();
				} catch (Exception e) {
					this.log.error("", e);
					throw new ExcelException(e);
				}
				// 循环遍历每一列
				for (String colNo : fieldMapping.keySet()) {
					CellMeta cellMeta = fieldMapping.get(colNo);
					int columnNo = cellMeta.getColumnNo();// 对应的列序号
					// 如果excel单元格为null则跳过继续下一个
					if (row.getCell(columnNo) == null) {
						continue;
					}
					// 对应的类属性
					String field = cellMeta.getField();
					// 对应的类型
					CellType type = cellMeta.getType();
					// 赋值方法
					String methodName = "set" + field.substring(0, 1).toUpperCase()
							+ field.substring(1);
					// 取得单元格的类型
					int cellType = row.getCell(columnNo).getCellType();
					// 创建错误记录
					ErrorLog errorLog = new ErrorLog();
					// 取值
					Object value = null;
					switch (cellType) {
					case HSSFCell.CELL_TYPE_BLANK:// 空
						value = "";
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔值
						value = row.getCell(columnNo).getBooleanCellValue();
						break;
					case HSSFCell.CELL_TYPE_ERROR:// 错误
						value = "";
						break;
					case HSSFCell.CELL_TYPE_FORMULA:// 公式
						value = row.getCell(columnNo).getNumericCellValue();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:// 数值
						DecimalFormat df = new DecimalFormat("0.##########");
						value = df.format(row.getCell(columnNo).getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:// 文本
						value = row.getCell(columnNo).getStringCellValue();
						break;
					default:// 其它
						// 错误记录：不支持的类型
						this.log.error("The type[" + cellType + "] is unsupport.");
						if (log) {
							errorLog.setRowNumber(j);
							errorLog.setColumnNumber(columnNo);
							errorLog.setReason("The type[" + cellType + "] is unsupport.");
							result.getErrors().add(errorLog);
						}
						continue;
					}
					// 类型转换
					switch (type) {
					case STRING:
						String str = value.toString().trim();
						try {
							resultClass.getMethod(methodName, String.class).invoke(obj, str);
						} catch (Exception e) {
							this.log.error("", e);
							throw new ExcelException(e);
						}
						break;
					case INT:
						try {
							double doubleValue = Double.parseDouble(value.toString().trim());
							if (doubleValue % 1 == 0) {// 如果是整数
								int intValue = (int) doubleValue;
								if (intValue != doubleValue) {// 如果转换后不等于原值，比如原值超出int范围。
									this.log.error("out of integer's range [" + doubleValue + "]");
									throw new NumberFormatException();
								}
								try {
									resultClass.getMethod(methodName, int.class).invoke(obj,
											intValue);
								} catch (Exception e) {
									this.log.error("", e);
									throw new ExcelException(e);
								}
							} else {// 如果不是整数
								this.log.error("it's not a integer [" + doubleValue + "]");
								throw new NumberFormatException();
							}
						} catch (NumberFormatException ex) {
							if (log) {
								errorLog.setRowNumber(j);
								errorLog.setColumnNumber(columnNo);
								errorLog.setReason("The value is not a integer.");
								result.getErrors().add(errorLog);
							}
							continue;
						}
						break;
					case LONG:
						try {
							double doubleValue = Double.parseDouble(value.toString().trim());
							if (doubleValue % 1 == 0) {// 如果是整数
								long longValue = (long) doubleValue;
								if (longValue != doubleValue) {// 如果转换后不等于原值，比如原值超出long范围。
									this.log.error("out of long's range [" + doubleValue + "]");
									throw new NumberFormatException();
								}
								try {
									resultClass.getMethod(methodName, long.class).invoke(obj,
											longValue);
								} catch (Exception e) {
									this.log.error("", e);
									throw new ExcelException(e);
								}
							} else {// 如果不是整数
								this.log.error("it's not a long [" + doubleValue + "]");
								throw new NumberFormatException();
							}
						} catch (NumberFormatException ex) {
							if (log) {
								errorLog.setRowNumber(j);
								errorLog.setColumnNumber(columnNo);
								errorLog.setReason("The value is not a long.");
								result.getErrors().add(errorLog);
							}
							continue;
						}
						break;
					case DOUBLE:
						try {
							double doubleValue = Double.parseDouble(value.toString().trim());
							try {
								resultClass.getMethod(methodName, double.class).invoke(obj,
										doubleValue);
							} catch (Exception e) {
								this.log.error("", e);
								throw new ExcelException(e);
							}
						} catch (NumberFormatException ex) {
							this.log.error("The value is not a double [" + value + "]");
							if (log) {
								errorLog.setRowNumber(j);
								errorLog.setColumnNumber(columnNo);
								errorLog.setReason("The value is not a double.");
								result.getErrors().add(errorLog);
							}
							continue;
						}
						break;
					case DATE:
						Date dateValue;
						if (value instanceof String) {
							try {
								dateValue = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(value
										.toString().trim());
							} catch (ParseException e) {
								this.log.error("The value is not a date [" + value + "]");
								if (log) {
									errorLog.setRowNumber(j);
									errorLog.setColumnNumber(columnNo);
									errorLog.setReason("The value is not a date.");
									result.getErrors().add(errorLog);
								}
								continue;
							}
						} else {
							try {
								dateValue = row.getCell(columnNo).getDateCellValue();
							} catch (Exception ex) {
								this.log.error("The value is not a date.", ex);
								if (log) {
									errorLog.setRowNumber(j);
									errorLog.setColumnNumber(columnNo);
									errorLog.setReason("The value is not a date.");
									result.getErrors().add(errorLog);
								}
								continue;
							}
						}
						try {
							resultClass.getMethod(methodName, Date.class).invoke(obj, dateValue);
						} catch (Exception e) {
							this.log.error("", e);
							throw new ExcelException(e);
						}
						break;
					}
				}
				data.add(obj);
			}
			result.addData(sheetName, data);
		}
		return result;
	}

	/**
	 * 导入指定excle文件到指定的对象结果集中，并且记录未成功导入的内容。
	 * 
	 * @param importFileName
	 *            要导入的excel文件
	 * @param resultClass
	 *            要导入的目标类
	 * @return 导入后的结果集
	 */
	public ExcelImportResult<T> importExcelWithLog(String importFileName, Class<T> resultClass) {
		FileInputStream importFile = null;
		try {
			importFile = new FileInputStream(importFileName);
			return this.importExcel(importFile, resultClass, true);
		} catch (Exception e) {
			this.log.error("", e);
			throw new ExcelException(e);
		} finally {
			if (importFile != null) {
				try {
					importFile.close();
				} catch (IOException e) {
					this.log.error("", e);
					throw new ExcelException(e);
				}
			}
		}

	}

	/**
	 * 导入指定excle文件到指定的对象结果集中，并且记录未成功导入的内容。
	 * 
	 * @param importFile
	 *            要导入的excel文件
	 * @param resultClass
	 *            要导入的目标类
	 * @return 导入后的结果集
	 */
	public ExcelImportResult<T> importExcelWithLog(FileInputStream importFile, Class<T> resultClass) {
		return this.importExcel(importFile, resultClass, true);
	}

	/**
	 * 导入指定excle文件到指定的对象结果集中。
	 * 
	 * @param importFileName
	 *            要导入的excel文件
	 * @param resultClass
	 *            要导入的目标类
	 * @return 导入后的结果集
	 */
	public Map<String, List<T>> importExcel(String importFileName, Class<T> resultClass) {
		FileInputStream importFile = null;
		try {
			importFile = new FileInputStream(importFileName);
			return this.importExcel(importFile, resultClass, false).getAllSheetsData();
		} catch (Exception e) {
			this.log.error("", e);
			throw new ExcelException(e);
		} finally {
			if (importFile != null) {
				try {
					importFile.close();
				} catch (IOException e) {
					this.log.error("", e);
					throw new ExcelException(e);
				}
			}
		}

	}

	/**
	 * 导入指定excle文件到指定的对象结果集中
	 * 
	 * @param importFile
	 *            要导入的excel文件
	 * @param resultClass
	 *            要导入的目标类
	 * @return 导入后的结果集
	 */
	public Map<String, List<T>> importExcel(FileInputStream importFile, Class<T> resultClass) {
		return this.importExcel(importFile, resultClass, false).getAllSheetsData();
	}

	/**
	 * 导出指定数据到指定excel文件。
	 * 
	 * @param exportFile
	 *            要导出的目标excel文件流
	 * @param data
	 *            要导出的数据
	 * @param dataClass
	 *            要导出的数据映射类
	 * @param ver
	 *            excel版本
	 */
	public void exportExcel(FileOutputStream exportFile, Map<String, List<T>> data,
			Class<T> dataClass, ExcelVersion ver) {
		// 如果类中不存在指定的注解配置，则不能进行excel导出。
		if (!dataClass.isAnnotationPresent(ExcelMapping.class)) {
			this.log.error("class[" + dataClass.getName() + "] needs mapping config.");
			throw new ExcelException("class[" + dataClass.getName() + "] needs mapping config.");
		}
		// 取得该类所有字段和注解配置
		Map<String, CellMeta> fieldMapping = new HashMap<String, CellMeta>();
		Field[] fields = dataClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(CellMapping.class)) {
				// 取得字段的注解信息
				CellMapping cell = field.getAnnotation(CellMapping.class);
				// 字段名
				String fieldName = field.getName();
				// 对应excel中列名
				String columnName = cell.name().toUpperCase();
				// 对应excel列号
				int columnNo = this.colName2colNo(columnName);
				// 列名注解配置不能为空
				if (columnName.length() == 0) {
					this.log.error("class[" + dataClass.getName() + "] field[" + fieldName
							+ "] mapping config can't be ''.");
					throw new ExcelException("class[" + dataClass.getName() + "] field["
							+ fieldName + "] mapping config can't be ''.");
				}
				// excel表格中对应的类型
				CellType type = cell.type();
				// 将配置信息暂存
				CellMeta cm = new CellMeta();
				cm.setColumnName(columnName);
				cm.setColumnNo(columnNo);
				cm.setField(fieldName);
				cm.setType(type);
				fieldMapping.put(String.valueOf(columnNo), cm);
			}
		}
		// 如果没有一个字段有映射关系的注解配置，则不能进行导入。
		if (fieldMapping.size() == 0) {
			this.log.error("class[" + dataClass.getName()
					+ "] needs a field mapping config at least.");
			throw new ExcelException("class[" + dataClass.getName()
					+ "] needs a field mapping config at least.");
		}
		// 取得类指定注解配置信息
		ExcelMapping sheets = dataClass.getAnnotation(ExcelMapping.class);
		int beginRow = sheets.begin() - 1;
		// 如果不指定版本号，则默认采用2007版本导出。
		Workbook wb = null;
		if (ver == null) {
			ver = ExcelVersion.EXCEL2007;
		}
		// 创建excel
		switch (ver) {
		case EXCEL2003:
			wb = new HSSFWorkbook();
			break;
		case EXCEL2007:
			wb = new XSSFWorkbook();
			break;
		default:
			wb = new HSSFWorkbook();
		}
		// 循环创建sheet页
		for (String sheetName : data.keySet()) {
			// 取得数据
			List<T> list = data.get(sheetName);
			// 创建sheet页
			Sheet sheet = wb.createSheet(sheetName);
			// 循环写入数据
			for (int i = 0; i < list.size(); i++) {
				// 取得一行数据
				T obj = list.get(i);
				// 创建一行
				Row row = sheet.createRow(i + beginRow);
				// 写入每列的值
				for (CellMeta cellMeta : fieldMapping.values()) {
					int columnNo = cellMeta.getColumnNo();
					// 对应对象的属性
					String field = cellMeta.getField();
					// 创建单元格
					Cell cell = row.createCell(columnNo);
					// 对象取值方法
					String methodName = "get" + field.substring(0, 1).toUpperCase()
							+ field.substring(1);
					Object value;
					try {
						value = dataClass.getMethod(methodName).invoke(obj);
					} catch (Exception e) {
						this.log.error("", e);
						throw new ExcelException(e);
					}
					if (value instanceof Double) {
						double doubleValue = Double.parseDouble(value.toString().trim());
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(doubleValue);
					} else if (value instanceof Date) {
						String dateValue = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
								.format(value);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(dateValue);
					} else if (value instanceof Boolean) {
						boolean boolValue = Boolean.parseBoolean(value.toString().trim());
						cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
						cell.setCellValue(boolValue);
					} else {
						String strValue = value.toString().trim();
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(strValue);
					}
				}
			}
		}
		// 写入文件
		try {
			wb.write(exportFile);
		} catch (Exception e) {
			this.log.error("", e);
			throw new ExcelException(e);
		}
	}

	/**
	 * 导出指定数据到指定excel文件。
	 * 
	 * @param exportFileName
	 *            要导出的目标excel文件名称
	 * @param data
	 *            要导出的数据
	 * @param dataClass
	 *            要导出的数据映射类
	 * @param ver
	 *            excel版本
	 */
	public void exportExcel(String exportFileName, Collection<T> data, Class<T> dataClass,
			ExcelVersion ver) {
		FileOutputStream exportFile = null;
		try {
			exportFile = new FileOutputStream(exportFileName);
			this.exportExcel(exportFile, data, dataClass, ver);
		} catch (Exception e) {
			this.log.error("", e);
			throw new ExcelException(e);
		} finally {
			if (exportFile != null) {
				try {
					exportFile.close();
				} catch (IOException e) {
					this.log.error("", e);
					throw new ExcelException(e);
				}
			}
		}
	}

	/**
	 * 导出指定数据到指定excel文件。
	 * 
	 * @param exportFile
	 *            要导出的目标excel文件流
	 * @param data
	 *            要导出的数据
	 * @param dataClass
	 *            要导出的数据映射类
	 * @param ver
	 *            excel版本
	 */
	public void exportExcel(FileOutputStream exportFile, Collection<T> data, Class<T> dataClass,
			ExcelVersion ver) {
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		List<T> list = new ArrayList<T>(data);
		map.put("sheet1", list);
		this.exportExcel(exportFile, map, dataClass, ver);
	}

	/**
	 * 导出指定数据到指定excel文件。
	 * 
	 * @param exportFileName
	 *            要导出的目标excel文件名称
	 * @param data
	 *            要导出的数据
	 * @param dataClass
	 *            要导出的数据映射类
	 * @param ver
	 *            excel版本
	 */
	public void exportExcel(String exportFileName, Map<String, List<T>> data, Class<T> dataClass,
			ExcelVersion ver) {
		FileOutputStream exportFile = null;
		try {
			exportFile = new FileOutputStream(exportFileName);
			this.exportExcel(exportFile, data, dataClass, ver);
		} catch (Exception e) {
			this.log.error("", e);
			throw new ExcelException(e);
		} finally {
			if (exportFile != null) {
				try {
					exportFile.close();
				} catch (IOException e) {
					this.log.error("", e);
					throw new ExcelException(e);
				}
			}
		}
	}

}
