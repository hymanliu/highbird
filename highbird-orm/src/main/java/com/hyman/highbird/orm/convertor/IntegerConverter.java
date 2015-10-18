package com.hyman.highbird.orm.convertor;


public class IntegerConverter implements Converter<Integer> {


	public Integer convert(String value) {
		Integer ret = null;
		ret = value ==null ? null : new Integer(value);
		return ret;
	}

	public String reconvert(Object value) {
		String ret = null;
		ret = value ==null ? null : value.toString();
		return ret;
	}

}
