package com.hyman.highbird.orm.annotation.processor;

import java.lang.reflect.Field;

import com.hyman.highbird.orm.annotation.Qualifier;
import com.hyman.highbird.orm.core.Column;
import com.hyman.highbird.orm.core.TableMapping;
import com.hyman.highbird.orm.exception.TableMappingException;


public class QualifierProcessor implements Processor {

	@Override
	public void process(TableMapping tableMapping,Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Qualifier.class)){
				Qualifier q = field.getAnnotation(Qualifier.class);
				checkQualifier(tableMapping,q,clazz);
				tableMapping.putColumnField(new Column(q.family(), q.qualifier()),field);
			}
		}
	}
	
	private void checkQualifier(TableMapping tableMapping,Qualifier qualifier,Class<?> clazz){
		String[] families = tableMapping.getFamilies();
		String family = qualifier.family();
		boolean exist = false;
		for(String f : families){
			if(family!=null && family.equals(f)) exist = true;
		}
		
		if(!exist){
			String message = "Table "+tableMapping.getName()+" configured error." + "column family "+family+" is not exist" ;
			throw new TableMappingException(message,clazz);
		}
	}
}
