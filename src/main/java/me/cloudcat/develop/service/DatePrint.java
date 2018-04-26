package me.cloudcat.develop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZZWang
 * @Time 2017年8月1日  下午3:06:33
 */
@Service
@Transactional(readOnly = true)
public class DatePrint {

  public void printDate1() {
    System.out.println("printDate1");
  }

  public void printDate2() {
    System.out.println("printDate2");
  }

}
