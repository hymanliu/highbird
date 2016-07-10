package hyman.highbird.orm.test.entity;

import hyman.highbird.orm.annotation.Qualifier;
import hyman.highbird.orm.annotation.RowKey;
import hyman.highbird.orm.annotation.Table;

import java.util.Date;

@Table(name="student",families={"info"})
public class Student {
	@RowKey
	private String rowKey;
	@Qualifier(family="info",qualifier="id")
	private String id;
	@Qualifier(family="info",qualifier="name")
	private String name;
	@Qualifier(family="info",qualifier="birthday")
	private Date birthday;
	@Qualifier(family="info",qualifier="number")
	private String number;
	
	
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
