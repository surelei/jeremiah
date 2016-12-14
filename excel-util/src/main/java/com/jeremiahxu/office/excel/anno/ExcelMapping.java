package com.jeremiahxu.office.excel.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义当前类映射一个excel的sheet页，sheet页用名字来标明。
 * 
 * @author Jeremiah Xu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelMapping {
    public String[] sheets() default {};// 导出时不需要配置该参数

    public int begin() default 1;// 导入和导出的开始行号
}
