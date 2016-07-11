package hyman.highbird.orm.core;

import hyman.highbird.orm.converter.ConverterUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 
 * @ClassName: EntityRelationConvert 
 * @Description: this class is used for converting the entity to row, row to entity
 * @author hyman.liu <zhiquanliu@foxmail.com>
 * @date 2015年10月16日 下午9:55:24 
 * 
 * @param <H>
 */
public abstract class EntityRelationConvert<H> {
	
	private Class<H> entityClass = null;
	private TableMapping tableMapping = null;
	
	@SuppressWarnings("unchecked")
	public EntityRelationConvert(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass = (Class<H>) type.getActualTypeArguments()[0];
		tableMapping = HighBirdOrmContext.getInstance().getConfiguration().get(entityClass);
	}

	/**
	 * turn result to entity
	 * @param result
	 * @return
	 */
	protected H turnToEntity(Result result){
		DataRow row = resultToRow(result);
		if(row==null) return null;
		H t = null;
		try {
			t = entityClass.newInstance();
			Map<Column,String> columns = row.getCols();
			for(Column key : columns.keySet()){
				Field field = tableMapping.getField(key);
				field.setAccessible(true);
				Object value = ConverterUtil.convert(columns.get(key), field.getType());
				field.set(t, value);
			}
			Field rowKey = tableMapping.getRowkey();
			rowKey.setAccessible(true);
			rowKey.set(t, row.getId());
		} catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		return t;
	}
	
	/**
	 * transform the Entity to DataRow
	 * @param o
	 * @return
	 */
	private DataRow entityToRow(H o){
		if(o==null) return null;
		
		DataRow row = new DataRow();
		
		Field rowkey = tableMapping.getRowkey();
		rowkey.setAccessible(true);
		try {
			row.setId((String) rowkey.get(o));
			
			for(Column column : tableMapping.getColumns()){
				Field field = tableMapping.getField(column);
				field.setAccessible(true);
				row.putColumn(column, ConverterUtil.convert(field.get(o)));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	/**
	 * transform the result to DataRow
	 * @param result
	 * @return
	 */
	private DataRow resultToRow(Result result){
		if(result==null || result.isEmpty()) return null;
		DataRow row = new DataRow();

		for(String family:tableMapping.getFamilies()){
			NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(family.getBytes());
			for(byte[] qualifier : familyMap.keySet()){
				String name = Bytes.toString(qualifier);
				String value = Bytes.toString(familyMap.get(qualifier));
				row.putColumn(new Column(family,name), value);
			}
		}
		String id = Bytes.toString(result.getRow());
		row.setId(id);
		return row ;
	}
	
	/**
	 * build a Get
	 * @param id
	 * @return
	 */
	protected Get buildGet(String id){
		Get get = new Get(Bytes.toBytes(id));
		for(String family:tableMapping.getFamilies())
			get.addFamily(Bytes.toBytes(family));
		return get;
	}
	
	/**
	 * turn an entity to Put 
	 * @param o
	 * @return
	 */
	protected Put entityToPut(H o){
		DataRow row = entityToRow(o);
		if(row ==null) return null;
		Put put = new Put(Bytes.toBytes(row.getId()));
		Map<Column,String> columnMap = row.getCols();
		for(Column key:columnMap.keySet()){
			put.addColumn(key.getFamily().getBytes(), Bytes.toBytes(key.getQualifier()), Bytes.toBytes(columnMap.get(key)));
		}
		return put;
	}

	/**
	 * get the Entity Class
	 * @return
	 */
	protected Class<H> getEntityClass() {
		return entityClass;
	}

	/**
	 * get the TableMapping configuration
	 * @return
	 */
	protected TableMapping getTableMapping() {
		return tableMapping;
	}
	 
}
