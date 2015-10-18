package com.hyman.highbird.orm.exception;

/**
 * 
 * @ClassName: ColumnFamilyNotFindException 
 * @Description: when assign a undefined column family to a qualifier, a ColumnFamilyNotFindException will occur
 * @author hyman.liu <zhiquanliu@foxmail.com>
 * @date 2015年10月18日 下午1:08:02 
 *
 */
public class ColumnFamilyNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6815282216100217975L;
	
	public ColumnFamilyNotFoundException(String message){   
		super(message);
	} 

	public ColumnFamilyNotFoundException(String message, Throwable cause){   
		super(message, cause);
	} 
}
