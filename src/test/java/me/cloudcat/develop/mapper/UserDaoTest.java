package me.cloudcat.develop.mapper;

import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring-context.xml")
public class UserDaoTest {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void testGetUser() {
		SqlSession session = sqlSessionFactory.openSession();
		UserDao mapper = session.getMapper(UserDao.class);
		
		List<User> user = mapper.getUserTest(null);
		System.out.println(user.sconstantize());
	}

/*	@SQLGenerator
	public void testInsertUser() {
		User user = new User(); 
		user.setUsername("test");
		user.setId(2L);
		SqlSession session = sqlSessionFactory.openSession();
		UserDao mapper = session.getMapper(UserDao.class);
		mapper.insertUser(user);
	}

	@SQLGenerator
	public void testDeleteUser() {
		SqlSession session = sqlSessionFactory.openSession();
		UserDao mapper = session.getMapper(UserDao.class);
		mapper.delete("%te%",2L);
	}*/
}
