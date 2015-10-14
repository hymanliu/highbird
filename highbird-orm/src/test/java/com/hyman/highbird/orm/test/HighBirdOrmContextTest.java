package com.hyman.highbird.orm.test;

import java.util.Map;

import org.junit.Test;

import com.hyman.highbird.entity.User;
import com.hyman.highbird.orm.core.HighBirdOrmContext;
import com.hyman.highbird.orm.core.TableMapping;


public class HighBirdOrmContextTest {

	@Test
	public void test(){
		HighBirdOrmContext configContext = HighBirdOrmContext.getInstance();
		Map<Class<?>,TableMapping> context = configContext.getConfiguration();
		
		System.out.println(context.get(User.class).getColumns());
	}
}
