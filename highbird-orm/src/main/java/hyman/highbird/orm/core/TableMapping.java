package hyman.highbird.orm.core;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
public class TableMapping {

	private String name;
	private Field rowkey;
	private String[] families;
	private Map<Column,Field> columnFieldMap =  new HashMap<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Field getRowkey() {
		return rowkey;
	}
	public void setRowkey(Field rowkey) {
		this.rowkey = rowkey;
	}
	public String[] getFamilies() {
		return families;
	}
	public void setFamilies(String[] families) {
		this.families = families;
	}
	public Map<Column, Field> getColumnFieldMap() {
		return columnFieldMap;
	}
	
	public void putColumnField(Column column,Field field)
	{
		columnFieldMap.put(column, field);
	}
	
	public Collection<Column> getColumns(){
		return columnFieldMap.keySet();
	}
	
	public Field getField(Column column){
		return columnFieldMap.get(column);
	}
	
}
