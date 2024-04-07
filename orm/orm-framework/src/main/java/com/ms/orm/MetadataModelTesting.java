package com.ms.orm;

import com.ms.orm.model.Person;
import com.ms.orm.util.ColumnField;
import com.ms.orm.util.MetaDataModel;
import com.ms.orm.util.PrimaryKeyField;

import java.util.List;

public class MetadataModelTesting {
	public static void main(String[] args) {
		Person ram = new Person(1, "Ram", 23);
		Class<? extends Person> clazz = ram.getClass();

		MetaDataModel metaDataModel = MetaDataModel.of(clazz);

		PrimaryKeyField primaryKeyField = metaDataModel.getPrimaryKeyField();
		System.out.printf("PrimaryKeyField:\nname: %s, type: %s%n", primaryKeyField.getFieldName(), primaryKeyField.getType().getSimpleName());

		List<ColumnField> columnFields = metaDataModel.getColumnFields();
		System.out.println("Column Fields:");
		columnFields.forEach(cf -> System.out.printf("name: %s, type: %s%n",
				cf.getFieldName(), cf.getType().getSimpleName()));
	}
}
