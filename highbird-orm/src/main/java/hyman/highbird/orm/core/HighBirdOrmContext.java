package hyman.highbird.orm.core;

import hyman.highbird.orm.annotation.processor.QualifierProcessor;
import hyman.highbird.orm.annotation.processor.RowKeyProcessor;
import hyman.highbird.orm.annotation.processor.TableProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
		synchronized(HighBirdOrmContext.class){
			if(instance==null){
				instance = new HighBirdOrmContext();
				TableProcessor TableProcessor = new TableProcessor();
				RowKeyProcessor rowKeyProcessor = new RowKeyProcessor();
				QualifierProcessor columnProcessor = new QualifierProcessor();
				
				for(Class<?> clazz : instance.clazzes){
					TableMapping tableMapping = new TableMapping();
					TableProcessor.process(tableMapping, clazz);
					rowKeyProcessor.process(tableMapping, clazz);
					columnProcessor.process(tableMapping, clazz);
					instance.configuration.put(clazz, tableMapping);
				}
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
