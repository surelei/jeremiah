package com.jeremiahxu.util.log;

import java.io.File;
import java.util.Map;

public interface LogUtil {
	/**
	 * 解析指定文件路径的日志文件
	 * 
	 * @param filePath
	 *            日志文件路径
	 */
	public void parse(String filePath);

	/**
	 * 解析指定日志文件
	 * 
	 * @param logFile
	 *            日志文件
	 */
	public void parse(File logFile);

	/**
	 * 取得日志统计数据
	 * 
	 * @return 日志统计数据
	 */
	public Map<String, Map<String, LogReportRecord>> getData();
}
