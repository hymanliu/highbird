package hyman.highbird.orm.annotation.processor;

import hyman.highbird.orm.annotation.Qualifier;
import hyman.highbird.orm.core.Column;
import hyman.highbird.orm.core.TableMapping;
import hyman.highbird.orm.exception.TableMappingException;

import java.lang.reflect.Field;


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
