package com.ms.reflection.core;

import com.ms.reflection.util.ColumnField;
import com.ms.reflection.util.MetaDataModel;
import com.ms.reflection.util.SQLQueryBuilder;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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

	private record PreparedStatementWrapper(PreparedStatement preparedStatement) {

		private static final Map<Class<?>, Function<Object, ThrowingBiConsumer<PreparedStatement, Integer, SQLException>>> statementSetters =
				Map.of(
						int.class, value -> (stmt, idx) -> stmt.setInt(idx, (Integer) value),
						long.class, value -> (stmt, idx) -> stmt.setLong(idx, (Long) value),
						String.class, value -> (stmt, idx) -> stmt.setString(idx, (String) value)
				);

		@FunctionalInterface
		private interface ThrowingBiConsumer<T, R, Ex extends Throwable> {

			void apply(T t, R r) throws Ex;
		}

		private static final AtomicInteger atomicInteger = new AtomicInteger(0);

		public <T> PreparedStatement andParameters(MetaDataModel metaDataModel, T obj)
				throws IllegalAccessException, SQLException {
			Field pkField = metaDataModel.getPrimaryKeyField().getField();
			pkField.setAccessible(true);
			statementSetters.get(pkField.getType()).apply(atomicInteger.incrementAndGet()).apply(preparedStatement, 1);
			int idx = 2;
			for (ColumnField colField : metaDataModel.getColumnFields()) {
				colField.getField().setAccessible(true);
				statementSetters.get(colField.getType()).apply(colField.getField().get(obj))
						.apply(preparedStatement, idx++);
			}
			return preparedStatement;
		}

	}

}
