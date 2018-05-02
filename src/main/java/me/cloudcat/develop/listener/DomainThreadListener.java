package me.cloudcat.develop.listener;

import me.cloudcat.develop.thread.DomainThread;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DomainThreadListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    // 通过WebApplicationContextUtils 得到Spring容器的实例。
    ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
    DomainThread thread = (DomainThread) application.getBean("domainThread");
//    thread.execute();

  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }
}
