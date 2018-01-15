package me.cloudcat.devlelop.testredis;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-context.xml")
public class TestRedis {

	@Test
	public void testOne() {
		Jedis jedis = new Jedis("127.0.0.1", 6379, 10000);
		jedis.auth("123456");
		System.out.println(jedis.get("test"));
		jedis.del("testList");
		jedis.lpush("testList", "测试redis", "测试redis2");
		List<String> list = jedis.lrange("testList", 0, -1);
		for (String l : list) {
			System.out.println(l);
		}
	}

	@Test
	public void testTwo() {
		Jedis jedis = new Jedis("127.0.0.1", 6379, 10000);
		jedis.auth("123456");
	}

	@Test
	public void testThree() {
		String str = "ceshi代码";
		byte b[]={97,65};
		String st[]={"String","123"};
		try {
			System.out.println(new String(b,"gb2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
