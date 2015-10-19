package com.hyman.highbird.orm.test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
		
		List<User> list = crud.list("0001","0002");
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
		for(int i=0;i<200;i++){
			User u = new User();
			DecimalFormat df = new DecimalFormat("0000");
			u.setId(df.format(i));
			u.setName("hyman-"+i);
			u.setPhone("1868882"+df.format(i));
			u.setAge(i);
			u.setBirth(new Date());
			crud.add(u);
		}
		
	/*	User u = new User();
		DecimalFormat df = new DecimalFormat("0000");
		u.setId(df.format(103));
		u.setName("hyman-"+103);
		u.setPhone("1868882"+df.format(103));
		u.setAge(20);
		crud.add(u);*/
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
