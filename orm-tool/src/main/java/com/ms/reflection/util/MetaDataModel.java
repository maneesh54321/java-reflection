package com.ms.reflection.util;

import com.ms.reflection.annotation.Column;
import com.ms.reflection.annotation.PrimaryKey;
import com.ms.reflection.annotation.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MetaDataModel {

	private final String tableName;

	private final PrimaryKeyField primaryKeyField;

	private final List<ColumnField> columnFields;

	private MetaDataModel(String tableName, PrimaryKeyField primaryKeyField, List<ColumnField> columnFields) {
		this.primaryKeyField = primaryKeyField;
		this.columnFields = columnFields;
		this.tableName = tableName;
	}

	public static MetaDataModel of(Class<?> clazz) {
		Table tableAnn = clazz.getAnnotation(Table.class);
		if(tableAnn == null) throw new IllegalArgumentException("The class is missing table annotation");
		String tableName;
		if(tableAnn.name() == null){
			tableName = clazz.getSimpleName();
		} else {
			tableName = tableAnn.name();
		}
		List<ColumnField> columnFields = new ArrayList<>();
		PrimaryKeyField primaryKeyField = null;
		for (Field field : clazz.getDeclaredFields()) {
			PrimaryKey primaryKeyAnn = field.getAnnotation(PrimaryKey.class);
			if (primaryKeyAnn != null) {
				primaryKeyField = new PrimaryKeyField(field);
			} else {
				Column columnAnn = field.getAnnotation(Column.class);
				if(columnAnn != null) {
					columnFields.add(new ColumnField(field));
				}
			}
		}
		if (primaryKeyField == null) throw new IllegalArgumentException("The class doesn't have a Primary Key");
		return new MetaDataModel(tableName, primaryKeyField, columnFields);
	}

	public PrimaryKeyField getPrimaryKeyField() {
		return primaryKeyField;
	}

	public List<ColumnField> getColumnFields() {
		return columnFields;
	}

	public String getTableName() {
		return tableName;
	}
}
