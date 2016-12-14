package com.jeremiahxu.office.excel;

/**
 * 错误记录
 * 
 * @author Jeremiah Xu
 * 
 */
public class ErrorLog {
    private int rowNumber;// 行号
    private int columnNumber;// 列号
    private String reason;// 错误原因

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
