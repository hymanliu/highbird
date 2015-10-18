package com.hyman.highbird.orm.annotation.processor;

import org.apache.commons.lang.StringUtils;

import com.hyman.highbird.orm.annotation.Table;
import com.hyman.highbird.orm.core.TableMapping;
import com.hyman.highbird.orm.exception.TableMappingException;

public class TableProcessor implements Processor {

	@Override
	public void process(TableMapping mapping,Class<?> clazz){
		if(clazz.isAnnotationPresent(Table.class)){
			Table table = clazz.getAnnotation(Table.class);
			mapping.setName(table.name());
			String[] families = table.families();
			mapping.setFamilies(families);
		}
		checkTable(mapping,clazz);
	}
	
	private void checkTable(TableMapping mapping,Class<?> clazz){
		String tableName = mapping.getName();
		if(StringUtils.isBlank(tableName) || tableName.contains(" ")){
			throw new TableMappingException("Table is invalid",clazz);
		}
		String[] families = mapping.getFamilies();
		if(families==null || families.length<1){
			throw new TableMappingException("Column families should specified in table "+ tableName,clazz);
		}
		for(String family : families){
			if(StringUtils.isBlank(family) || family.contains(" ")){
				throw new TableMappingException("Column family "+family+" must not be blank or contains blank charactors in table: "+ tableName,clazz);
			}
		}
	}
}
