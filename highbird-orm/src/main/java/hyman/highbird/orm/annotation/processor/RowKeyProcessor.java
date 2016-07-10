package hyman.highbird.orm.annotation.processor;

import hyman.highbird.orm.annotation.RowKey;
import hyman.highbird.orm.core.TableMapping;
import hyman.highbird.orm.exception.TableMappingException;

import java.lang.reflect.Field;


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
