package com.hyman.highbird.orm.annotation.processor;

import java.lang.reflect.Field;

import com.hyman.highbird.orm.annotation.Qualifier;
import com.hyman.highbird.orm.core.Column;
import com.hyman.highbird.orm.core.TableMapping;


public class QualifierProcessor implements Processor {

	@Override
	public void process(TableMapping mapping,Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Qualifier.class)){
				Qualifier q = field.getAnnotation(Qualifier.class);
				mapping.putColumnField(new Column(q.family(), q.qualifier()),field);
			}
		}
	}
}
