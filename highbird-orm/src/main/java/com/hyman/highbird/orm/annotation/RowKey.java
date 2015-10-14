package com.hyman.highbird.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: RowKey 
 * @Description: annotation for htable rowkey
 * @author hyman.liu <zhiquanliu@foxmail.com>
 * @date 2015年10月14日 下午8:00:54 
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RowKey {
	String name();
}
