package com.hyman.highbird.orm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyman.highbird.entity.Student;
import com.hyman.highbird.entity.User;
import com.hyman.highbird.orm.annation.processor.QualifierProcessor;
import com.hyman.highbird.orm.annation.processor.RowKeyProcessor;
import com.hyman.highbird.orm.annation.processor.TableProcessor;


public class ORMConfigContext {
	
	private static ORMConfigContext instance;
	
	private final Map<Class<?>,TableMapping> configuration = new HashMap<>();
	private final List<Class<?>> clazzes = new ArrayList<Class<?>>();
	
	public final Map<Class<?>, TableMapping> getConfiguration() {
		return configuration;
	}

	private ORMConfigContext(){
		//TODO
		//load table Class
		clazzes.add(User.class);
		clazzes.add(Student.class);
		////
	}
	
	
	
	public static ORMConfigContext getInstance(){
		if(instance==null){
			instance = new ORMConfigContext();
			TableProcessor TableProcessor = new TableProcessor();
			RowKeyProcessor rowKeyProcessor = new RowKeyProcessor();
			QualifierProcessor columnProcessor = new QualifierProcessor();
			
			for(Class<?> clazz : instance.clazzes){
				TableMapping mapping = new TableMapping();
				TableProcessor.process(mapping, clazz);
				rowKeyProcessor.process(mapping, clazz);
				columnProcessor.process(mapping, clazz);
				instance.configuration.put(clazz, mapping);
			}
		}
		return instance;
	}

	
	
}
