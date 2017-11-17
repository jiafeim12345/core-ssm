package me.cloudcat.develop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ZZWang
 * @Time 2017年8月1日  下午3:17:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext.xml")
public class AOPServiceTest {

	@Autowired
	AOPService aop;
	
	@Test
	public void test() {
		aop.printOne();
		System.out.println();
	}

}
