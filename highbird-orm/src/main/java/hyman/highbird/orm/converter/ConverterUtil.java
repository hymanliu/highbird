package hyman.highbird.orm.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConverterUtil {
	
	private static final String DEFAULT= "default";
	public static Map<String,Converter> convertContext = new HashMap<String,Converter>();
	static{
		convertContext.put(DEFAULT, new DefaultTypeConverter());
		convertContext.put(Date.class.getName(), new DateConverter());
	}
	

	public static Object convert(String value,Class<?> toType){
		Converter converter = getConverter(toType);
		return converter.convert(value,toType);
	}

	private static Converter getConverter(Class<?> toType) {
		Converter converter = null;
		if(toType.isPrimitive()){
			converter = convertContext.get(DEFAULT);
		}
		else if(toType == Integer.class
				||(toType == Double.class)
				||(toType == Boolean.class)
				||(toType == Byte.class)
				||(toType == Character.class)
				||(toType == Short.class) 
				||(toType == Long.class) 
				||(toType == Float.class)
				||toType == BigInteger.class
				||toType == BigDecimal.class
				||toType == String.class
				||Enum.class.isAssignableFrom(toType))
		{
			converter = convertContext.get(DEFAULT);
		}
		else{
			converter = convertContext.get(toType.getName());
		}
		return converter;
	}
	
	public static String convert(Object value){
		if(value==null ) return null;
		Converter converter = getConverter(value.getClass());
		return converter.reconvert(value);
	}
}
