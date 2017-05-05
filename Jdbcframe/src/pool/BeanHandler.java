package pool;

import java.lang.reflect.Field;
import java.sql.ResultSet;

import com.mysql.jdbc.ResultSetMetaData;

public class BeanHandler implements ResultSetHandler {

	private Class clazz;
	
	
	public BeanHandler(Class clazz){
		this.clazz = clazz;
	}
	
	
	//模板设计模式
	public Object handler(ResultSet rs) {
		
		try{
			if(!rs.next()){
				return null;
			}
			Object bean = clazz.newInstance();
			
			ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData();			//得到元数据
			int columnCount = metadata.getColumnCount();		//得到结果集中有多少列数据
			for(int i=0;i<columnCount;i++){
				String columnName = metadata.getColumnName(i+1);			//得到每列的列名
				Object columnData = rs.getObject(i+1);					//得到每一列的数据
				
				Field f = clazz.getDeclaredField(columnName);					//通过属性名反射出这个属性
				f.setAccessible(true);								//强行打开权限开关
				f.set(bean, columnData);						//将值封装到这个属性当中
			}
			
			return bean;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

}
