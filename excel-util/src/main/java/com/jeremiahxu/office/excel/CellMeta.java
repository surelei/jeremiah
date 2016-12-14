package com.jeremiahxu.office.excel;

public class CellMeta {
    private String field;// 对象属性名
    private String columnName;// excel字段名
    private CellType type;// 数据类型
    private int columnNo;// 列号

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public int getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

}
