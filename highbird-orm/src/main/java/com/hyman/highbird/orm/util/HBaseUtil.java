package com.hyman.highbird.orm.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseUtil {
	
	private static Configuration conf;
	private static HBaseAdmin hbaseAdmin;

	public synchronized static Configuration getConfiguration(){
		if(conf==null){
			conf = HBaseConfiguration.create();
		}
		return conf;
	}
	
	public synchronized static HBaseAdmin getHBaseAdmin(){
		
		if(hbaseAdmin==null){
			try {
				hbaseAdmin = new HBaseAdmin(getConfiguration());
			} catch (MasterNotRunningException e) {
				e.printStackTrace();
			} catch (ZooKeeperConnectionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hbaseAdmin;
	}
	
}
