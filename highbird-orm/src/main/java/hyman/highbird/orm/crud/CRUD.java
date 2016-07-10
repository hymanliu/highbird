package hyman.highbird.orm.crud;

import hyman.highbird.orm.util.Page;

import java.util.List;


public interface CRUD<T> {

	List<T> list(String... rowkeys);
	
	Page<T> scanPage(String startRow, int limit);

	void add(T o);

	void delete(String rowId);

	T get(String id);

	Page<T> scanPage(String startRow, int fromIndex, int pageIndex, int pageSize);
}
