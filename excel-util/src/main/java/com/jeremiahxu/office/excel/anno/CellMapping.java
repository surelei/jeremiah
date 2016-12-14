package com.jeremiahxu.office.excel.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jeremiahxu.office.excel.CellType;

/**
 * 类字段和excel列的对应关系
 * 
 * @author Jeremiah Xu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CellMapping {
    public String name();

    public CellType type() default CellType.STRING;
}
