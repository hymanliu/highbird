package com.hyman.highbird.entity;

import java.util.Date;

import com.hyman.highbird.orm.annotation.Qualifier;
import com.hyman.highbird.orm.annotation.RowKey;
import com.hyman.highbird.orm.annotation.Table;

@Table(name="user",families={"info"})
public class User {
	
	@RowKey
	private String id;
	@Qualifier(family="info",qualifier = "name")
	private String name;
	@Qualifier(family="info",qualifier = "phone")
	private String phone;
	@Qualifier(family="info",qualifier = "age")
	private int age;
	@Qualifier(family="info",qualifier = "birth")
	private Date birth;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "id:"+id+"\tname:"+name+"\tphone:"+phone +"\tage:"+age+"\tbith:"+birth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
}
