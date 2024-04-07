package com.ms.orm.util;

import com.ms.orm.annotation.Column;

import java.lang.reflect.Field;
import java.util.Objects;

public class ColumnField {

	private final Field field;

	private final Column column;

	public ColumnField(Field field) {
		this.field = field;
		this.column = field.getAnnotation(Column.class);
	}

	public String getFieldName(){
		return field.getName();
	}

	public String getColumnName(){
		return Objects.isNull(column.name()) || column.name().isBlank() ? getFieldName() : column.name();
	}

	public Class<?> getType(){
		return field.getType();
	}

	public Field getField() {
		return field;
	}
}
