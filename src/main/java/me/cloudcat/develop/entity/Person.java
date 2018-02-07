package me.cloudcat.develop.entity;

/**
 * 人员表
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/2/7 18:28
 */
public class Person extends BaseEntity<Person> {
	
	private String name;      // 姓名
	private String gender;    // 性别
	private Integer age;      // 年龄


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
