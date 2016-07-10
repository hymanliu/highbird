package hyman.highbird.orm.converter;

public interface Converter {
	Object convert(Object value,Class<?> toType);
	String reconvert(Object value);
}
