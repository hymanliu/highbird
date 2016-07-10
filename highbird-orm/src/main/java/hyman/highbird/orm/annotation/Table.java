package hyman.highbird.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: Table 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author hyman.liu <zhiquanliu@foxmail.com>
 * @date 2015年10月14日 下午8:01:27 
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	String name();
	String[] families() default {"info"};
}
