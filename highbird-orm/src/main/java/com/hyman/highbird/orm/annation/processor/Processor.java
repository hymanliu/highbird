package com.hyman.highbird.orm.annation.processor;

import com.hyman.highbird.orm.core.TableMapping;

public interface Processor {

	void process(TableMapping mapping,Class<?> clazz);
	
}
