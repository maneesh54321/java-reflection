package com.ms.reflection.util;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SQLQueryBuilder {

	public static String buildInsert(MetaDataModel metaDataModel) {
		return String.format("INSERT INTO %s(%s) VALUES(%s)",
				metaDataModel.getTableName(),
				buildAllColumns(metaDataModel),
				buildQuestionMarks(metaDataModel)
		);
	}

	private static String buildQuestionMarks(MetaDataModel metaDataModel) {
		return IntStream.range(1, metaDataModel.getColumnFields().size() + 2).mapToObj(i -> "?")
				.collect(
						Collectors.joining(", "));
	}

	private static String buildAllColumns(MetaDataModel metaDataModel) {
		return metaDataModel.getPrimaryKeyField().getName() + ", "
				+ metaDataModel.getColumnFields().stream().map(ColumnField::getName)
				.collect(Collectors.joining(", "));
	}

	public static String buildFindByPrimaryKeySql(MetaDataModel metaDataModel) {
		String tableName = metaDataModel.getTableName();
		String allColumns = buildAllColumns(metaDataModel);
		return String.format("select %s from %s where %s = ?", allColumns, tableName, metaDataModel.getPrimaryKeyField().getName());
	}
}
