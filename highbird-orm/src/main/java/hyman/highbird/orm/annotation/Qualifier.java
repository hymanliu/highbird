package hyman.highbird.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @ClassName: Qualifier 
* @Description: annotation for htable column field
* @author hyman.liu <zhiquanliu@foxmail.com>
* @date 2015年10月14日 下午7:57:29 
*
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

	String family();
	String qualifier();
}
