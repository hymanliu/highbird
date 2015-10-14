package com.hyman.highbird.orm.test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hyman.highbird.crud.UserCRUD;
import com.hyman.highbird.entity.User;
import com.hyman.highbird.orm.util.Page;

public class UserCRUDTest {

	private static UserCRUD crud = null;
	
	@BeforeClass
	public static void init(){
		crud = new UserCRUD();
	}
	
	@Test
	public void testList() throws IOException{
		
		List<Get> gets = new ArrayList<Get>(); 
		    
	    Get get1 = new Get("1".getBytes());
	    gets.add(get1);
		
		List<User> list = crud.list(gets);
		
		for(User u : list){
			System.out.println(u);
		}
	}
	
	@Test
	public void testPageScan(){
		Page<User> page = crud.scanPage("", 3);
		
		for(User u :page.getResultList()){
			System.out.println(u);
		}
	}
	
	@Test
	public void testPut(){
		for(int i=100;i<200;i++){
			User u = new User();
			DecimalFormat df = new DecimalFormat("0000");
			u.setId(df.format(i));
			u.setName("hyman-"+i);
			u.setPhone("1868882"+df.format(i));
			crud.put(u);
		}
	}
	
	@Test
	public void testPageScan2(){
		Page<User> page = crud.scanPage("0096",20,20,5);
		for(User u :page.getResultList()){
			System.out.println(u);
		}
	}
	
	@Test
	public void delete(){
		crud.delete("0101");
	}
	
	@Test
	public void testGet(){
		User user = crud.get("0103");
		System.out.println(user);
	}
}
