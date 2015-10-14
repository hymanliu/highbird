package com.hyman.highbird.orm;

import java.util.Map;

import org.junit.Test;

import com.hyman.highbird.entity.User;
import com.hyman.highbird.orm.core.ORMConfigContext;
import com.hyman.highbird.orm.core.TableMapping;


public class ORMConfigractionContextTest {

	@Test
	public void test(){
		ORMConfigContext configContext = ORMConfigContext.getInstance();
		Map<Class<?>,TableMapping> context = configContext.getConfiguration();
		
		
		System.out.println(context.get(User.class).getColumns());
	}
}
