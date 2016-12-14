package com.jeremiahxu.office.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excle处理结果，包括出错的位置和原因，以及处理总记录数等。
 * 
 * @author Jeremiah Xu
 * 
 */
public class ExcelImportResult<T> {
    private Map<String, List<T>> data = new HashMap<String, List<T>>();// 导入的数据，按照sheet页的名字分别存储
    private List<ErrorLog> errors = new ArrayList<ErrorLog>();// 导入或导出时的错误记录。

    /**
     * 取得全部导入的数据，可能包含多个sheet页，每个sheet页的数据保存为一个list。
     * 
     * @return
     */
    public Map<String, List<T>> getAllSheetsData() {
        return data;
    }

    /**
     * 取得指定sheet名字的数据。
     * 
     * @param sheetName
     * @return
     */
    public List<?> getSheetData(String sheetName) {
        return data.get(sheetName);
    }

    /**
     * 添加一个sheet页的数据到结果集中。
     * 
     * @param sheetName
     * @param data
     */
    protected void addData(String sheetName, List<T> data) {
        this.data.put(sheetName, data);
    }

    /**
     * excel处理结果信息。
     * 
     * @return
     */
    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        if (this.getErrors().size() > 0) {// 如果有错误信息
            sb.append("操作错误记录：\r\n");
            for (ErrorLog er : this.getErrors()) {
                sb.append("第").append(er.getRowNumber() + 1).append("行第").append(er.getColumnNumber() + 1).append("列，错误原因：").append(er.getReason()).append("\r\n");
            }
        } else {
            sb.append("操作成功，没有任何错误。");
        }
        return sb.toString();
    }

    public List<ErrorLog> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorLog> errors) {
        this.errors = errors;
    }

}
