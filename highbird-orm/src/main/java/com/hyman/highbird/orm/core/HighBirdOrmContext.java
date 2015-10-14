package com.hyman.highbird.orm.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyman.highbird.orm.annation.processor.QualifierProcessor;
import com.hyman.highbird.orm.annation.processor.RowKeyProcessor;
import com.hyman.highbird.orm.annation.processor.TableProcessor;


public class HighBirdOrmContext {
	
	private static HighBirdOrmContext instance;
	
	private final Map<Class<?>,TableMapping> configuration = new HashMap<>();
	private final List<Class<?>> clazzes = new ArrayList<Class<?>>();
	
	public final Map<Class<?>, TableMapping> getConfiguration() {
		return configuration;
	}

	private HighBirdOrmContext(){
		try {
			loadEntityClass();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("please check your highbird-orm.persist file!");
		}
	}
	
	public static HighBirdOrmContext getInstance(){
		if(instance==null){
			instance = new HighBirdOrmContext();
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

	private void loadEntityClass() throws IOException, ClassNotFoundException{
		ClassLoader classLoader = HighBirdOrmContext.class.getClassLoader();
		InputStream in = classLoader.getResourceAsStream("highbird-orm.persist");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while((line = reader.readLine())!=null){
			clazzes.add(classLoader.loadClass(line));
		}
	}
	
}