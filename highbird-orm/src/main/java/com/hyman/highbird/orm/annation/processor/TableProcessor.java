package com.hyman.highbird.orm.annation.processor;

import com.hyman.highbird.orm.annation.Table;
import com.hyman.highbird.orm.core.TableMapping;

public class TableProcessor implements Processor {

	@Override
	public void process(TableMapping mapping,Class<?> clazz){
		if(clazz.isAnnotationPresent(Table.class)){
			Table table = clazz.getAnnotation(Table.class);
			mapping.setName(table.name());
			String[] families = table.families();
			mapping.setFamilies(families);
		}
	}
}
