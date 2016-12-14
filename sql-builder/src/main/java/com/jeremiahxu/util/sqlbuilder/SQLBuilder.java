package com.jeremiahxu.util.sqlbuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeremiahxu.util.sqlbuilder.anno.Position;

/**
 * sql脚本生成工具
 * 
 * @author Jeremiah Xu
 *
 */
public class SQLBuilder<T> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	public final static int BUFF_SIZE = 1024;

	/**
	 * 根据模板和给定数据生成SQL脚本
	 * 
	 * @param input
	 * @param output
	 * @param data
	 * @param inputClass
	 */
	public void makeSQL(FileInputStream input, FileOutputStream output, List<T> data,
			Class<T> inputClass) {
		// sql脚本字符串
		StringBuffer sql = new StringBuffer();
		// 读取sql脚本模板文件
		byte[] b = new byte[BUFF_SIZE];
		StringBuffer sb = new StringBuffer();
		try {
			while (input.read(b) != -1) {
				sb.append(new String(b));
			}
		} catch (IOException e) {
			this.log.error("", e);
			throw new SQLBuilderException(e);
		}
		// 遍历所有数据
		for (int i = 0; i < data.size(); i++) {
			T obj = data.get(i);
			// 取得要输入的数据对象的所有字段和注解
			Field[] fields = inputClass.getDeclaredFields();
			String str = new String(sb.toString().trim());
			for (Field field : fields) {
				if (field.isAnnotationPresent(Position.class)) {
					// 取得字段注解信息
					Position pos = field.getAnnotation(Position.class);
					// 取得占位符
					String placeHolder = pos.placeholder();
					// 取得字段类型
					FieldType type = pos.type();
					// 字段名
					String column = field.getName();
					// 对象取值方法
					String methodName = "get" + column.substring(0, 1).toUpperCase()
							+ column.substring(1);
					// 取值
					String value;
					Object object;
					try {
						object = inputClass.getMethod(methodName).invoke(obj);
					} catch (Exception e) {
						this.log.error("", e);
						throw new SQLBuilderException(e);
					}
					// 根据不同类型处理数据
					switch (type) {
					case STRING:
						value = "'" + object.toString() + "'";
						break;
					case DATE:
						value = "'" + new SimpleDateFormat("yyyy-MM-dd").format(object) + "'";
						break;
					case DATETIME:
						value = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object)
								+ "'";
						break;
					case TIME:
						value = "'" + new SimpleDateFormat("HH:mm:ss").format(object) + "'";
						break;
					default:
						value = object.toString();
					}
					// 用数据替换占位符
					str = str.replaceAll(placeHolder, value);
				}
			}
			sql.append(str).append("\r\n");
		}
		try {
			output.write(sql.toString().getBytes());
		} catch (IOException e) {
			this.log.error("", e);
			throw new SQLBuilderException(e);
		}
	}

	/**
	 * 根据模板和给定数据生成SQL脚本
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param data
	 * @param inputClass
	 */
	public void makeSQL(String inputFile, String outputFile, List<T> data, Class<T> inputClass) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(inputFile);
			output = new FileOutputStream(outputFile);
			this.makeSQL(input, output, data, inputClass);
		} catch (FileNotFoundException e) {
			this.log.error("", e);
			throw new SQLBuilderException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					this.log.error("", e);
					throw new SQLBuilderException(e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					this.log.error("", e);
					throw new SQLBuilderException(e);
				}
			}
		}
	}
}
