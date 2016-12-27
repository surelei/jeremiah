package com.jeremiahxu.util.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志分析工具
 * 
 * @author Jeremiah Xu
 *
 */
public class ApchComLogUtil implements LogUtil {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private Map<String, Map<String, LogReportRecord>> data = new HashMap<String, Map<String, LogReportRecord>>();

	public Map<String, Map<String, LogReportRecord>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, LogReportRecord>> data) {
		this.data = data;
	}

	/**
	 * 分析日志
	 * 
	 * @param filePath
	 *            日志文件路径
	 */
	public void parse(String filePath) {
		File logFile = new File(filePath);
		if (logFile.isDirectory()) {// 判断是否是目录
			for (File file : logFile.listFiles()) {
				this.parse(file);
			}
		} else if (logFile.isFile()) {// 判断是否是文件
			this.parse(logFile);
		} else {// 文件或目录不存在
			log.error(filePath + " is not exist.");
		}
	}

	/**
	 * 分析单个日志文件
	 * 
	 * @param logFile
	 *            日志文件
	 */
	public void parse(File logFile) {
		if (logFile.isDirectory()) {// 判断是否是目录
			for (File file : logFile.listFiles()) {
				this.parse(file);
			}
		} else if (logFile.isFile()) {// 判断是否是文件
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(logFile));
				String line = null;
				while ((line = input.readLine()) != null) {// 逐行读取
					String[] str = line.split(" ");
					LogRecord record = new LogRecord();
					record.setIp(str[0]);
					record.setDatetime(str[3].substring(1));
					record.setMethod(str[5].substring(1));
					record.setUrl(str[6]);
					record.setProtocol(str[7].substring(0, str[7].length() - 1));
					record.setReturnCode(str[8]);
					// 取得指定URL的数据
					Map<String, LogReportRecord> urlMap = this.getData().get(record.getUrl());
					// 如果URL统计数据不存在
					if (urlMap == null) {
						urlMap = new HashMap<String, LogReportRecord>();
					}
					// 取得格式化的时间数据
					Date date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US)
							.parse(record.getDatetime());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MINUTE, 0);
					Date datetime = cal.getTime();
					// 判断根据时间统计的数据是否存在
					LogReportRecord report = urlMap.get(datetime.toString());
					if (report == null) {// 根据时间统计的数据不存在
						report = new LogReportRecord(record);
					} else {// 根据时间统计的数据存在
						report.add(record);
					}
					urlMap.put(datetime.toString(), report);
					this.data.put(record.getUrl(), urlMap);
				}
			} catch (Exception e) {
				log.error("", e);
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (IOException e) {
					log.error("", e);
				}
			}
		} else {
			log.error(logFile.getAbsolutePath() + " is not exist.");
		}
	}
}
