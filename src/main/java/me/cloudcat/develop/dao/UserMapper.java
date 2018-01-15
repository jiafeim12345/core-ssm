package me.cloudcat.develop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import me.cloudcat.develop.entity.User;

public interface UserMapper {
	
	public User getUser(String username);
	
	public List<User> getUserTest(String username);
	
	@Select(value="select * from tbl_user")
	public List<User> findAllUser();

	public User getUserByType(@Param("type") int type);

}
