package hyman.highbird.orm.test;

import hyman.highbird.orm.core.HighBirdOrmContext;
import hyman.highbird.orm.core.TableMapping;
import hyman.highbird.orm.test.entity.User;

import java.util.Map;

import org.junit.Test;


public class HighBirdOrmContextTest {

	@Test
	public void test(){
		HighBirdOrmContext configContext = HighBirdOrmContext.getInstance();
		Map<Class<?>,TableMapping> context = configContext.getConfiguration();
		
		System.out.println(context.get(User.class).getColumns());
	}
}
