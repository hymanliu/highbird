package com.hyman.highbird.orm.annation.processor;

import java.lang.reflect.Field;

import com.hyman.highbird.orm.annation.RowKey;
import com.hyman.highbird.orm.core.TableMapping;


public class RowKeyProcessor implements Processor {

	@Override
	public void process(TableMapping mapping,Class<?> clazz){
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(RowKey.class)){
				RowKey rowKey = field.getAnnotation(RowKey.class);
				mapping.setRowkey(rowKey.name());
				mapping.setRowKeyField(field);
				break;
			}
		}
	}
}
