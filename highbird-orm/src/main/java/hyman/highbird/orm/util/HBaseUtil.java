package hyman.highbird.orm.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class HBaseUtil {
	
	private static Configuration conf;
	private static Connection connection;
	private static Admin admin;

	public synchronized static Configuration getConfiguration(){
		if(conf==null){
			conf = HBaseConfiguration.create();
		}
		return conf;
	}
	
	public synchronized static Connection getConnection(){
		if(connection==null){
			try {
				connection = ConnectionFactory.createConnection(getConfiguration());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public synchronized static Admin getAdmin(){
		if(admin==null){
			try {
				admin = getConnection().getAdmin();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return admin;
	}
	
}
