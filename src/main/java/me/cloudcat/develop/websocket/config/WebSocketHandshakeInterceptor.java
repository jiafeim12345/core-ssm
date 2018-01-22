package me.cloudcat.develop.websocket.config;

import me.cloudcat.develop.Constant;
import me.cloudcat.develop.entity.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * 
 * @author ZZWang
 * @Time 2017年3月20日  上午11:07:40
 *
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
			throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			// 获取用户名并封装
			HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
//			User user = (User) session.getAttribute(Constant.SESSION_USER);
			String userName = (String) session.getAttribute(Constant.SESSION_USER);
			// 向socketsession中设置username属性，作为其唯一标识
			attributes.put(Constant.SESSION_SOCKET, userName);
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	
	}

}
