package com.hyman.highbird.orm.convertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateConverter implements Converter<Date>{
	   private static Log log = LogFactory.getLog(DateConverter.class);
	   private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");


	   public Date convert(String value) {
		   try {
               return df.parse(value);
           } catch (Exception pe) {
           }
		return null;
//	       throw new ConversionException("Could not convert " +
//	                                     value.getClass().getName() + " to " +
//	                                     type.getName());
	   }

	   public String reconvert(Object value) {
		   try {
        	   return df.format(value);
           } catch (Exception e) {
//               throw new ConversionException("Error converting Date to String");
           }
		return null;
	   }
	}