package hyman.highbird.orm.test;

import hyman.highbird.orm.converter.ConverterUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.junit.Test;


public class ConverterUtilTest {

	@Test
	public void testDate() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		String date = ConverterUtil.convert(new Date());
		System.out.println(date);
		Date d = (Date) ConverterUtil.convert("20150101000000",Date.class);
		System.out.println(d);
		Integer i = (Integer) ConverterUtil.convert("1000000",Integer.class);
		System.out.println(i);
		System.out.println(int.class == Integer.class);
		
	}
	
	
}
