package hyman.highbird.orm.annotation.processor;

import hyman.highbird.orm.core.TableMapping;

public interface Processor {

	void process(TableMapping mapping,Class<?> clazz);
	
}
