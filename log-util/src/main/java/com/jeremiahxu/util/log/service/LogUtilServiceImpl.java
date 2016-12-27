package com.jeremiahxu.util.log.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiahxu.util.log.ApchComLogUtil;
import com.jeremiahxu.util.log.LogFormat;
import com.jeremiahxu.util.log.LogReportRecord;
import com.jeremiahxu.util.log.LogUtil;
import com.jeremiahxu.util.log.mapper.LogMapper;

public class LogUtilServiceImpl implements LogUtilService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private int logFormat = LogFormat.COMMON;// 日志格式
	private String logFile = "";// 要分析的日志文件路径或者文件名
	private LogUtil logUtil;
	@Autowired
	private LogMapper logMapper;

	public void init() {
		switch (this.logFormat) {
		case 0:
			this.logUtil = new ApchComLogUtil();
			break;
		default:
		}
	}

	@Transactional
	public void parseLog() {
		this.init();
		log.info("开始分析日志");
		this.logUtil.parse(this.logFile);
		log.info("日志分析结束");
		log.info("开始保存分析结果");
		Map<String, Map<String, LogReportRecord>> data = logUtil.getData();
		switch (this.logFormat) {
		case 0:
			for (Map<String, LogReportRecord> map : data.values()) {
				for (LogReportRecord report : map.values()) {
					this.logMapper.insertPageView(report);
				}
			}
			break;
		default:
		}
		log.info("日志分析结果已保存");
	}

	public LogUtil getLogUtil() {
		return logUtil;
	}

	public void setLogUtil(LogUtil logUtil) {
		this.logUtil = logUtil;
	}

	public int getLogFormat() {
		return logFormat;
	}

	public void setLogFormat(int logFormat) {
		this.logFormat = logFormat;
	}

	public String getLogFile() {
		return this.logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public LogMapper getLogMapper() {
		return logMapper;
	}

	public void setLogMapper(LogMapper logMapper) {
		this.logMapper = logMapper;
	}

}
