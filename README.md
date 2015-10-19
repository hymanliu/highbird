# highbird
highbird is a hbase orm framework, it works like hibernate


Hello,

thanks for using my highbird framework project.

why i named the framework as highbird?
because the framework like the bird.
Although the framework is small,but the functions are fully equipped.(麻雀虽小，五脏俱全嘛)，
and I hope this framework will fly very high.

thanks for using highbird framework, if you find some bugs or have some requirements please send email to me. I will fix or finish it as soon as possible.

thanks. 
hyman.liu
mail:zhiquanliu@foxmail.com


Functions:

1	auto create table
2	auto mapping field
3	support any primitive types,java.lang.String,java.util.Date
4	supply pagination fuctions for access hbase table
...

Features:

highbird framework is easy to use.
eg：
step1
//create a java bean
package com.hyman.highbird.example;
@Table(name="h_user",families={"cf1","cf2"})
public class User{
	@RowKey
	private String rowkey;
	@Qualifier(family="cf1",qualifier = "id")
	private int id;
	@Qualifier(family="cf1",qualifier = "name")
	private String name;
	@Qualifier(family="cf2",qualifier = "birth")
	private String birth;
	....
}

step2
//configured the bean 
add com.hyman.highbird.example.User in "highbird-orm.persist" file

step3

//create a UserCRUD
public class UserCRUD extends GenericCRUD<User> {

}

step4
//use userCRUD to access hbase table h_user

UserCRUD crud = new UserCRUD();
User user = new User();
user.setId(..)
user.setXXX(..)
crud.add(user);


crud.delete(rowId);

crud.scanPage(.....)

.......


 