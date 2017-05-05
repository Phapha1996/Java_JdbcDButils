package domain;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.ResultSetMetaData;

import pool.ResultSetHandler;

public class ListHandler implements ResultSetHandler {

	Class clazz;

	public ListHandler(Class clazz) {
		this.clazz = clazz;
	}

	public Object handler(ResultSet rs) {
		try {
			ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData(); // 得到rs元数据
			int columnCount = metadata.getColumnCount(); // 得到有多少列

			List list = new LinkedList();
			while (rs.next()) { // 只要结果集里面有数据，就继续往下跑

				Object bean = clazz.newInstance();

				for (int i = 0; i < columnCount; i++) {
					String columnName = metadata.getColumnName(i + 1); // 得到第i+1列的列名字
					Object columnValue = rs.getObject(columnName); // 通过这个名字得到值

					Field f = clazz.getDeclaredField(columnName); // 反射出属性
					f.setAccessible(true);
					f.set(bean, columnValue);
				}
				list.add(bean);
			}

			return list;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
