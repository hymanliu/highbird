package com.hyman.highbird.orm.convertor;

public interface Converter<T> {
	T convert(String value);
	String reconvert(Object value);
}
