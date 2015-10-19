package com.hyman.highbird.orm.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.configuration.ConversionException;

public class DateConverter implements Converter{
	   private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");


	   public Object convert(Object value, Class<?> toType) {
		   if(value==null) return  null;
		   try {
               return df.parse(value.toString());
           } catch (Exception e) {
        	   throw new ConversionException("Could not convert " +
                       value.getClass().getName() + " to " +
                       toType.getName());
           }
	   }

	   public String reconvert(Object value) {
		   if(value==null) return null;
		   try {
        	   return df.format(value);
           } catch (Exception e) {
               throw new ConversionException("Error converting Date to String");
           }
	   }
	}