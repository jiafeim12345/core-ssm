package me.cloudcat.develop.websocket.config;

import me.cloudcat.develop.Constant;
import me.cloudcat.develop.utils.BusinessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


/**
 * 握手拦截器
 *
 * @author ZZWang
 * @Time 2017年3月20日  上午11:07:40
 *
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static Logger logger = LoggerFactory.getLogger("socket");

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
			throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            String username = BusinessUtils.getUser().getUsername();
            // 向socket session中设置username属性，作为其唯一标识
            attributes.put(Constant.SESSION_SOCKET, username);
            logger.info("socket connect success : " + username);
        }

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	
	}

}
