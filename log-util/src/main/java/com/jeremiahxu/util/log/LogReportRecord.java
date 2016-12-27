package com.jeremiahxu.util.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LogReportRecord {
	private int id = 0;
	private String url;// 访问的url
	private int userNumber = 0;// 访问的用户数量
	private int pageView = 0;// 访问的次数
	private int year;
	private int month;
	private int day;
	private int hour;
	private Set<String> ip = new HashSet<String>();

	public LogReportRecord() {
	}

	/**
	 * 根据日志记录构建日记报表记录。
	 * 
	 * @param record
	 */
	public LogReportRecord(LogRecord record) throws Exception {
		this.url = record.getUrl();
		this.ip.add(record.getIp());
		this.userNumber = 1;
		this.pageView = 1;
		Date date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US).parse(record
				.getDatetime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.day = cal.get(Calendar.DAY_OF_MONTH);
		this.hour = cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 将日志记录数据合并到报表数据。
	 * 
	 * @param record
	 *            要统计的数据记录
	 */
	public void add(LogRecord record) throws Exception {
		// 要统计的URL必须是一样的
		if (!this.url.equals(record.getUrl())) {
			throw new LogUtilException("The two URL should be same.[" + this.url + "]["
					+ record.getUrl() + "]");
		}
		// 要统计的时间必须是一样的
		Date date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US).parse(record
				.getDatetime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (this.year != year || this.month != month || this.day != day || this.hour != hour) {
			throw new LogUtilException("The datetime should be same.");
		}
		// 判断IP是否已经存在
		if (!this.ip.contains(record.getIp())) {// 如果ip不存在
			this.ip.add(record.getIp());
			this.userNumber += 1;
		}
		this.pageView += 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getPageView() {
		return pageView;
	}

	public void setPageView(int pageView) {
		this.pageView = pageView;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Set<String> getIp() {
		return ip;
	}

	public void setIp(Set<String> ip) {
		this.ip = ip;
	}

}
