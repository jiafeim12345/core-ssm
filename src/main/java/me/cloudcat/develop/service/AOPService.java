package me.cloudcat.develop.service;

import org.springframework.stereotype.Service;

/**
 * AOP测试类
 *
 * @author ZZWang
 * @Time 2017年8月1日  下午3:02:09
 */
@Service
public class AOPService {
	
	public void printOne() {
		System.out.println("this is printOne");
	}
	
	public void printTwo() {
		System.out.println("this is printTwo");
	}
	
}
