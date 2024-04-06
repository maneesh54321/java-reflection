package com.ms.reflection.core;

import com.ms.reflection.helper.ThrowingBiConsumer;
import com.ms.reflection.helper.ThrowingBiFunction;
import com.ms.reflection.model.Person;
import com.ms.reflection.util.ColumnField;
import com.ms.reflection.util.MetaDataModel;
import com.ms.reflection.util.PrimaryKeyField;
import com.ms.reflection.util.SQLQueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class EntityManagerImpl implements EntityManager {

	private final Connection connection;

	public EntityManagerImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public <T> void persist(T obj) {
		MetaDataModel metaDataModel = MetaDataModel.of(obj.getClass());
		String sql = SQLQueryBuilder.buildInsert(metaDataModel);
		try (PreparedStatement ps = prepareStatementWith(sql).andParameters(metaDataModel, obj)) {
			ps.executeUpdate();
		} catch (SQLException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private PreparedStatementWrapper prepareStatementWith(String sql)
			throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		return new PreparedStatementWrapper(preparedStatement);
	}

	@Override
	public <T> List<T> getAll() {
		return List.of();
	}

	@Override
	public <T, P> T find(Class<T> clazz, P p) throws SQLException {
		MetaDataModel metaDataModel = MetaDataModel.of(clazz);
		String sql = SQLQueryBuilder.buildFindByPrimaryKeySql(metaDataModel);
		try (PreparedStatement ps = prepareStatementWith(sql).addFindByPrimaryKeyParameter(metaDataModel, p)) {
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				T obj = clazz.getConstructor().newInstance();

				PrimaryKeyField primaryKeyField = metaDataModel.getPrimaryKeyField();
				fieldSetters.get(primaryKeyField.getType()).apply(resultSet, primaryKeyField.getName()).accept(primaryKeyField.getField(), obj);

				for(ColumnField columnField: metaDataModel.getColumnFields()) {
					fieldSetters.get(columnField.getType()).apply(resultSet, columnField.getName()).accept(columnField.getField(), obj);
				}
				return obj;
			}
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private static final Map<Class<?>, ThrowingBiFunction<ResultSet, String, ThrowingBiConsumer<Field, Object, IllegalAccessException>, SQLException>> fieldSetters =
			Map.of(
					int.class, (rs, fieldName) -> {
						int value = rs.getInt(fieldName);
						return (field, obj) -> {
							field.setAccessible(true);
							field.setInt(obj, value);
						};
					},
					long.class, (rs, fieldName) -> {
						long value = rs.getLong(fieldName);
						return (field, obj) -> {
							field.setAccessible(true);
							field.setLong(obj, value);
						};
					},
					String.class, (rs, fieldName) -> {
						String value = rs.getString(fieldName);
						return (field, obj) -> {
							field.setAccessible(true);
							field.set(obj, value);
						};
					}
			);

	private record PreparedStatementWrapper(PreparedStatement preparedStatement) {

		private static final Map<Class<?>, Function<Object, ThrowingBiConsumer<PreparedStatement, Integer, SQLException>>> statementSetters =
				Map.of(
						int.class, value -> (stmt, idx) -> stmt.setInt(idx, (Integer) value),
						long.class, value -> (stmt, idx) -> stmt.setLong(idx, (Long) value),
						String.class, value -> (stmt, idx) -> stmt.setString(idx, (String) value)
				);

		public <P> PreparedStatement addFindByPrimaryKeyParameter(MetaDataModel metaDataModel, P p) throws SQLException {
			statementSetters.get(metaDataModel.getPrimaryKeyField().getType()).apply(p).accept(preparedStatement, 1);
			return preparedStatement;
		}

		private static final AtomicLong atomicInteger = new AtomicLong(0);

		public <T> PreparedStatement andParameters(MetaDataModel metaDataModel, T obj)
				throws IllegalAccessException, SQLException {
			Field pkField = metaDataModel.getPrimaryKeyField().getField();
			pkField.setAccessible(true);
			statementSetters.get(pkField.getType()).apply(atomicInteger.incrementAndGet()).accept(preparedStatement, 1);
			int idx = 2;
			for (ColumnField colField : metaDataModel.getColumnFields()) {
				colField.getField().setAccessible(true);
				statementSetters.get(colField.getType()).apply(colField.getField().get(obj))
						.accept(preparedStatement, idx++);
			}
			return preparedStatement;
		}

	}

}
