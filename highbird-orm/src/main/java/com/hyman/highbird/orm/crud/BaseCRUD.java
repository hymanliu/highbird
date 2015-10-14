package com.hyman.highbird.orm.crud;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.hyman.highbird.orm.core.Column;
import com.hyman.highbird.orm.core.DataRow;
import com.hyman.highbird.orm.core.HTableFactory;
import com.hyman.highbird.orm.core.ORMConfigContext;
import com.hyman.highbird.orm.core.TableMapping;
import com.hyman.highbird.orm.util.Page;


public abstract class BaseCRUD<T> implements CRUD<T>{
	
	private HTable table;
	private Class<T> clazz = null;
	private TableMapping mapping = null;
	private HTableFactory tableFactory = null;
	
	@SuppressWarnings("unchecked")
	public BaseCRUD(){
		tableFactory = HTableFactory.getInstance();
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		mapping = ORMConfigContext.getInstance().getConfiguration().get(clazz);
		table = tableFactory.createHTable(clazz);
	}
	
	@Override
	public List<T> list(List<Get> gets){
		List<T> ret = new ArrayList<T>();
		try {
		    Result[] results = table.get(gets);
		    for(Result result: results){
		    	ret.add(this.rowToClass(turnToRow(result)));
		    }
		} catch (IOException e) {
			
		}
		return ret;
	}
	
	@Override
	public Page<T> scanPage(String startRow, int limit){
		Page<T> page = new Page<>();
		Scan scan = new Scan();
		
		PageFilter pfilter = new PageFilter(limit);
		
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(pfilter);
		FilterList filterList = new FilterList(filters);
		
		scan.setFilter(filterList);
		
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
		} catch (IOException e) {
		}
		List<T> list = new ArrayList<T>();
		for(Result result :scanner){
			T o = this.rowToClass(turnToRow(result));
			if(o!=null){
				list.add(o);
			}
		}
		page.setResultList(list);
		return page;
	}
	
	@Override
	public Page<T> scanPage(String fromRowkey, int fromIndex, int pageIndex, int pageSize){
		Page<T> page = new Page<>();
		Scan scan = new Scan();
		int limit= pageSize;
		int offset = 0;
		PageFilter pageFilter = null;
		// 缓存1000条数据
		scan.setCaching(1000);
		if(pageIndex>=fromIndex){
			limit = (pageIndex - fromIndex+1)*pageSize;
			pageFilter = new PageFilter(limit);
			if(StringUtils.isNotBlank(fromRowkey)){
				scan.setStartRow(fromRowkey.getBytes());
			}
			offset = (pageIndex - fromIndex)*pageSize;
		}else{
			limit = pageIndex*pageSize;
			pageFilter = new PageFilter(limit);
			if(StringUtils.isNotBlank(fromRowkey)){
				scan.setStopRow(fromRowkey.getBytes());
			}
			offset = (pageIndex-1) * pageSize;
		}
		scan.setFilter(pageFilter);
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
		} catch (IOException e) {
		
		}
		
		List<T> list = new ArrayList<T>();
		int i = 0 ;
		for(Result result :scanner){
			if(i>=offset && list.size()<pageSize){
				T o = this.rowToClass(turnToRow(result));
				if(o!=null){
					list.add(o);
				}
			}
			i++;
		}
		page.setCurrentPage(pageIndex);
		page.setResultList(list);
		return page;
	}
	
	@Override
	public T get(String id){
		Get get = this.buildGet(id);
		T ret = null;
		try {
			Result result = table.get(get);
			ret = this.rowToClass(turnToRow(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public void put(T o){
		Put put = this.turnToPut(this.classToRow(o));
		if(put==null) return;
		try {
			table.put(put);
		} catch (RetriesExhaustedWithDetailsException | InterruptedIOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(String rowId){
		try {
			Delete delete = new Delete(Bytes.toBytes(rowId));
			table.delete(delete);
		} catch (IOException e) {
			
		}
	}
	
	private Get buildGet(String id){
		Get get = new Get(Bytes.toBytes(id));
		get.addFamily(Bytes.toBytes("info"));
		return get;
	}

	private T rowToClass(DataRow row){
		if(row==null) return null;
		T t = null;
		try {
			t = clazz.newInstance();
			Map<Column,String> columns = row.getCols();
			for(Column key : columns.keySet()){
				Field field = mapping.getField(key);
				field.setAccessible(true);
				field.set(t, columns.get(key));
			}
			Field rowKeyField = mapping.getRowKeyField();
			rowKeyField.setAccessible(true);
			rowKeyField.set(t, row.getId());
		} catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		return t;
	}
	
	private DataRow classToRow(T o){
		if(o==null) return null;
		
		DataRow row = new DataRow();
		
		Field rowKeyField = mapping.getRowKeyField();
		rowKeyField.setAccessible(true);
		try {
			row.setId((String) rowKeyField.get(o));
			
			for(Column column : mapping.getColumns()){
				Field field = mapping.getField(column);
				field.setAccessible(true);
				row.putColumn(column, (String) field.get(o));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	private DataRow turnToRow(Result result){
		if(result==null || result.isEmpty()) return null;
		
		DataRow row = new DataRow();

		for(String family:mapping.getFamilies()){
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
	
	
	private Put turnToPut(DataRow row){
		if(row ==null) return null;
		Put put = new Put(Bytes.toBytes(row.getId()));
		Map<Column,String> columnMap = row.getCols();
		for(Column key:columnMap.keySet()){
			put.add(key.getFamily().getBytes(), Bytes.toBytes(key.getQualifier()), Bytes.toBytes(columnMap.get(key)));
		}
		return put;
	}
}
