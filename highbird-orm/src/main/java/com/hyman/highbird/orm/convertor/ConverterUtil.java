package com.hyman.highbird.orm.convertor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConverterUtil {
	
	public static Map<Class<?>,Converter<?>> convertContext = new HashMap<Class<?>,Converter<?>>();
	static{
		convertContext.put(Integer.class, new IntegerConverter());
		convertContext.put(Date.class, new DateConverter());
	}
	

	public static Object convert(Class<?> clazz,String value){
		Converter<?> converter = convertContext.get(clazz);
		return converter.convert(value);
	}
	
	public static String convert(Object value){
		if(value==null ) return null;
		Converter<?> converter = convertContext.get(value.getClass());
		return converter.reconvert(value);
	}
}
