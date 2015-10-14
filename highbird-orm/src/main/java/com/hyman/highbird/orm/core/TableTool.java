package com.hyman.highbird.orm.core;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import com.hyman.highbird.orm.util.HBaseUtil;


public class TableTool {
	
	private static HBaseAdmin admin = HBaseUtil.getHBaseAdmin();
	
	public static void createTable(TableMapping mapping){
		
		final TableName tn = TableName.valueOf(mapping.getName());
		final HTableDescriptor tableDesc = new HTableDescriptor(tn);
		
		for(String family:mapping.getFamilies()){
			HColumnDescriptor familyDesc = new HColumnDescriptor(family);
			tableDesc.addFamily(familyDesc);
		}
		HTableDescriptor desc = new HTableDescriptor(tableDesc);
		try {
			if(!admin.tableExists(mapping.getName())){
				admin.createTable(desc);
			}
		} catch (IOException e) {
		
		}
	}
	
	
	public static void dropTable(TableMapping mapping){
		try {
			String tableName = mapping.getName();
			if(!admin.tableExists(tableName)){
				return;
			}
			if(!admin.isTableDisabled(tableName)){
				admin.disableTable(tableName);
			}
			admin.deleteTable(tableName);
		} catch (IOException e) {
			
		}
	}
}
