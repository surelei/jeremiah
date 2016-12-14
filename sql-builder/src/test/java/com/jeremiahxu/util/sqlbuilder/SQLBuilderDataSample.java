package com.jeremiahxu.util.sqlbuilder;

import java.util.Date;

import com.jeremiahxu.util.sqlbuilder.anno.Position;

public class SQLBuilderDataSample {

	@Position(placeholder = "#1#", type = FieldType.NUMBER)
	private int id;
	@Position(placeholder = "#2#", type = FieldType.STRING)
	private String name;
	@Position(placeholder = "#3#", type = FieldType.DATE)
	private Date date;
	@Position(placeholder = "#4#", type = FieldType.NUMBER)
	private double doubleNumber;
	@Position(placeholder = "#5#", type = FieldType.NUMBER)
	private int intNumber;
	@Position(placeholder = "#6#", type = FieldType.DATETIME)
	private Date datetime;
	@Position(placeholder = "#7#", type = FieldType.TIME)
	private Date time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getDoubleNumber() {
		return doubleNumber;
	}

	public void setDoubleNumber(double doubleNumber) {
		this.doubleNumber = doubleNumber;
	}

	public int getIntNumber() {
		return intNumber;
	}

	public void setIntNumber(int intNumber) {
		this.intNumber = intNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
