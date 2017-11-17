/**
 * (c) 2006 JOVEN
 * 
 * http://www.joven.com.cn
 */
package me.cloudcat.develop.entity.configuration;

/**
 * 
 * @author ZZWang
 *
 *         2017年7月8日 下午9:51:07
 */
public class User {

	protected Long id;

	private String username;

	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getUsername() {
		return username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
