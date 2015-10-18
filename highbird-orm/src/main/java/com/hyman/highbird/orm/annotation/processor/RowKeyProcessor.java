package com.hyman.highbird.orm.annotation.processor;

import java.lang.reflect.Field;

import com.hyman.highbird.orm.annotation.RowKey;
import com.hyman.highbird.orm.core.TableMapping;
import com.hyman.highbird.orm.exception.TableMappingException;


public class RowKeyProcessor implements Processor {

	@Override
	public void process(TableMapping tableMapping,Class<?> clazz){
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(RowKey.class)){
				tableMapping.setRowkey(field);
				break;
			}
		}
		checkRowkey(tableMapping,clazz);
	}
	
	private void checkRowkey(TableMapping mapping,Class<?> clazz){
		if(mapping.getRowkey()==null){
			throw new TableMappingException("Rowkey must specified in table "+mapping.getName(),clazz);
		}
	}
}
