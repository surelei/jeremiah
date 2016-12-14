package com.jeremiahxu.office.excel;

import java.util.Date;

import com.jeremiahxu.office.excel.CellType;
import com.jeremiahxu.office.excel.anno.CellMapping;
import com.jeremiahxu.office.excel.anno.ExcelMapping;

@ExcelMapping
public class ExcelDataSample {
    @CellMapping(name = "A", type = CellType.INT)
    private int id;
    @CellMapping(name = "B", type = CellType.LONG)
    private long length;
    @CellMapping(name = "C", type = CellType.STRING)
    private String code;
    @CellMapping(name = "D")
    private String name;
    @CellMapping(name = "E", type = CellType.DOUBLE)
    private double price;
    @CellMapping(name = "F", type = CellType.DATE)
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
