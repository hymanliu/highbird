package com.hyman.highbird.orm.crud;

import java.util.List;

import com.hyman.highbird.orm.util.Page;


public interface CRUD<T> {

	List<T> list(String... rowkeys);
	
	Page<T> scanPage(String startRow, int limit);

	void add(T o);

	void delete(String rowId);

	T get(String id);

	Page<T> scanPage(String startRow, int fromIndex, int pageIndex, int pageSize);
}
