package com.ms.reflection.util;

import com.ms.reflection.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.util.Objects;

public class PrimaryKeyField {

	private final Field field;

	private final PrimaryKey primaryKey;

	public PrimaryKeyField(Field field) {
		this.field = field;
		this.primaryKey = field.getAnnotation(PrimaryKey.class);
	}

	public Field getField() {
		return field;
	}

	public String getFieldName() {
		return field.getName();
	}

	public String getColumnName() {
		return Objects.isNull(primaryKey.name()) || primaryKey.name().isBlank() ? getFieldName() : primaryKey.name();
	}

	public Class<?> getType() {
		return field.getType();
	}


}
