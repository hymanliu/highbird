package com.hyman.highbird.orm.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.junit.Test;

import com.hyman.highbird.orm.convertor.ConverterUtil;



public class ConverterUtilTest {

	@Test
	public void testDate() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		String date = ConverterUtil.convert(new Date());
		System.out.println(date);
		
		Date d = (Date) ConverterUtil.convert(Date.class,"20150101000000");
		System.out.println(d);
		
		Integer i = (Integer) ConverterUtil.convert(Integer.class,"1000000");
		System.out.println(i);
	}
}
