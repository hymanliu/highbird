package hyman.highbird.orm.core;

import hyman.highbird.orm.util.HBaseUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;
import org.apache.log4j.Logger;


/**
 * @author hyman.liu
 * date: Jul 11, 2015 10:01:01 AM
 */
public class HTableFactory {
	private static final Logger log = Logger.getLogger(HTableFactory.class);
	private static Map<Class<?>,Table> tableMap = new HashMap<Class<?>,Table>();
	
	private static HTableFactory instance;
	
	private Map<Class<?>,TableMapping> configuration = null;
	
	
	private HTableFactory(){
		configuration = HighBirdOrmContext.getInstance().getConfiguration();
		persistAll();
	}
	
	public static synchronized HTableFactory getInstance(){
		if(instance==null){
			instance = new HTableFactory();
		}
		return instance;
	}
	
	private void persistAll(){
		log.info("begin to persist all the tables");
		for(Class<?> clazz :configuration.keySet()){
			TableMapping maping = configuration.get(clazz);
			TableTool.createTable(maping);
			log.info("persist table >>"+maping.getName()+"----"+ clazz.getName() +" \t ok");
		}
	}
	
	public Table createHTable(Class<?> clazz){
		Table table = tableMap.get(clazz);
		TableMapping tableConf = configuration.get(clazz);
		if(table==null){
			try {
				TableName tableName= TableName.valueOf(tableConf.getName());
				table = HBaseUtil.getConnection().getTable(tableName);
				tableMap.put(clazz, table);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return table;
	}

}
