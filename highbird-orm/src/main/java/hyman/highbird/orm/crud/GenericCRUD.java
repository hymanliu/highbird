package hyman.highbird.orm.crud;

import hyman.highbird.orm.core.EntityRelationConvert;
import hyman.highbird.orm.core.HTableFactory;
import hyman.highbird.orm.util.Page;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;


/**
 * @ClassName: GenericCRUD 
 * @Description:  GenericCRUD class provides the common methods for access the HBase like add,delete,scanPage...and so on.
 * @author hyman.liu <zhiquanliu@foxmail.com>
 * @date 2015年10月16日 下午10:07:01 
 * 
 * @param <H>
 */
public class GenericCRUD<H> extends EntityRelationConvert<H> implements CRUD<H>{
	
	private HTable table;
	private HTableFactory tableFactory = null;
	
	public GenericCRUD(){
		super();
		tableFactory = HTableFactory.getInstance();
		table = tableFactory.createHTable(getEntityClass());
	}
	
	@Override
	public List<H> list(String... rowKeys){
		
		List<Get> gets = new ArrayList<Get>();
		for(String rowKey:rowKeys){
			gets.add(buildGet(rowKey));
		}
		List<H> ret = new ArrayList<H>();
		try {
		    Result[] results = table.get(gets);
		    for(Result result: results){
		    	ret.add(turnToEntity(result));
		    }
		} catch (IOException e) {
			
		}
		return ret;
	}
	
	@Override
	public Page<H> scanPage(String startRow, int limit){
		Page<H> page = new Page<>();
		Scan scan = new Scan();
		
		PageFilter pfilter = new PageFilter(limit);
		
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(pfilter);
		FilterList filterList = new FilterList(filters);
		
		scan.setFilter(filterList);
		
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
		} catch (IOException e) {
		}
		List<H> list = new ArrayList<H>();
		for(Result result :scanner){
			H o = turnToEntity(result);
			if(o!=null){
				list.add(o);
			}
		}
		page.setResultList(list);
		return page;
	}
	
	@Override
	public Page<H> scanPage(String fromRowkey, int fromIndex, int pageIndex, int pageSize){
		Page<H> page = new Page<>();
		Scan scan = new Scan();
		int limit= pageSize;
		int offset = 0;
		PageFilter pageFilter = null;
		// 缓存1000条数据
		scan.setCaching(1000);
		if(pageIndex>=fromIndex){
			limit = (pageIndex - fromIndex+1)*pageSize;
			pageFilter = new PageFilter(limit);
			if(StringUtils.isNotBlank(fromRowkey)){
				scan.setStartRow(fromRowkey.getBytes());
			}
			offset = (pageIndex - fromIndex)*pageSize;
		}else{
			limit = pageIndex*pageSize;
			pageFilter = new PageFilter(limit);
			if(StringUtils.isNotBlank(fromRowkey)){
				scan.setStopRow(fromRowkey.getBytes());
			}
			offset = (pageIndex-1) * pageSize;
		}
		scan.setFilter(pageFilter);
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
		} catch (IOException e) {
		
		}
		
		List<H> list = new ArrayList<H>();
		int i = 0 ;
		for(Result result :scanner){
			if(i>=offset && list.size()<pageSize){
				H o = turnToEntity(result);
				if(o!=null){
					list.add(o);
				}
			}
			i++;
		}
		page.setCurrentPage(pageIndex);
		page.setResultList(list);
		return page;
	}
	
	@Override
	public H get(String id){
		Get get = this.buildGet(id);
		H ret = null;
		try {
			Result result = table.get(get);
			ret = this.turnToEntity(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public void add(H o){
		Put put = this.entityToPut(o);
		if(put==null) return;
		try {
			table.put(put);
		} catch (RetriesExhaustedWithDetailsException | InterruptedIOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(String rowId){
		try {
			Delete delete = new Delete(Bytes.toBytes(rowId));
			table.delete(delete);
		} catch (IOException e) {
			
		}
	}
}
