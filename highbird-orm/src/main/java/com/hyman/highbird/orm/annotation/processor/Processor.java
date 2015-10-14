package com.hyman.highbird.orm.annotation.processor;

import com.hyman.highbird.orm.core.TableMapping;

public interface Processor {

	void process(TableMapping mapping,Class<?> clazz);
	
}
