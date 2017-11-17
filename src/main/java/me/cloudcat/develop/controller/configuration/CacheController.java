package me.cloudcat.develop.controller.configuration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.cloudcat.develop.entity.configuration.User;
import me.cloudcat.develop.dao.UserMapper;

/**
 *
 * @author ZZWang
 * @Time 2017年7月13日 上午9:47:39
 */
@Controller
@RequestMapping(value = "/cache")
public class CacheController {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	SqlSessionFactory factory;
	
	/**
	 * 测试一级缓存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testOneLevel", method = RequestMethod.GET)
	public String test1(HttpServletRequest request, Model model) {
		//同一session进行查询操作测试
		/*SqlSession session = factory.openSession();
		List<Object> list = session.selectList("me.cloudcat.develop.dao.UserMapper.getUserTest");
		System.out.println(list.size());
		//此处会调用一级缓存
		list = session.selectList("me.cloudcat.develop.dao.UserMapper.getUserTest");
		System.out.println(list.size());*/
		
		SqlSession session = factory.openSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<User> list = mapper.getUserTest("admin");
		System.out.println(list.size());
		//此处调用一级缓存
		List<User> list2 = mapper.getUserTest("admin");
		System.out.println(list2.size());
		//此处会清除一级缓存
		session.commit();
		//一级缓存被清除，重新查询，如果开启了二级缓存，此处会从二级缓存中查询
		List<User> list3 = mapper.getUserTest("admin");
		System.out.println(list3.size());
		
		return null;
	}
	
	/**
	 * 测试二级缓存（spring注入情况）
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testTwoLevel", method = RequestMethod.GET)
	public String test2(HttpServletRequest request, Model model) {
		List<User> list = userMapper.getUserTest("admin");
		System.out.println(list.size());
		//此处会调用二级缓存
		list = userMapper.getUserTest("admin");
		System.out.println(list.size());
		return null;
	}
	/**
	 * 测试二级缓存（session获取mapper）
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testTwoLevel2", method = RequestMethod.GET)
	public String test3(HttpServletRequest request, Model model) {
		SqlSession session = factory.openSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<User> list = mapper.getUserTest("admin");
		System.out.println(list.size());
		session.commit();
		session.close();
		SqlSession session2 = factory.openSession();
		UserMapper mapper2 = session2.getMapper(UserMapper.class);
		list = mapper2.getUserTest("admin");
		System.out.println(list.size());
		System.out.println("124323");
		return null;
	}

}
