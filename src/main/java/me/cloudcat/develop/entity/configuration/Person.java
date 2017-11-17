package me.cloudcat.develop.entity.configuration;

/**
 * @author ZZWang
 *
 *         2017年7月9日 下午1:16:53
 */
public class Person {
	
	private Long id;

	private String name;

	private String gender;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
