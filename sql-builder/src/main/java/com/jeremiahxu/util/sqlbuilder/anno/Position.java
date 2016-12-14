package com.jeremiahxu.util.sqlbuilder.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jeremiahxu.util.sqlbuilder.FieldType;

/**
 * 标注字段在脚本中的位置
 * 
 * @author Jeremiah Xu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Position {
	public String placeholder();

	public FieldType type() default FieldType.STRING;
}
