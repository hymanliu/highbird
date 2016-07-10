package hyman.highbird.orm.exception;

public class TableMappingException extends RuntimeException {

	private static final long serialVersionUID = -5951084777798019864L;

	public TableMappingException(String message){   
		super(message);
	} 

	public TableMappingException(String message, Class<?> clazz){   
		super(message+" \nIn class "+clazz.getName());
	} 
}
