package me.cloudcat.develop.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * 测试Filter Two
 *
 * @Author: jiafeim12345@163.com
 * @Date: 2018/4/6/006 19:51
 */
public class FilterThree implements Filter {

    Logger logger = LoggerFactory.getLogger("Filter");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Filter three init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Filter three doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Filter three destroy...");
    }
}
