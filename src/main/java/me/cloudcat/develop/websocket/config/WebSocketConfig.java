package me.cloudcat.develop.websocket.config;

import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 
 * @author ZZWang
 * @Time 2017年3月17日 下午3:47:11
 *
 */
@Configuration
@EnableWebSocket // 启动websocket服务
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		// 控制允许连接的域名
		/* String[] allowsOrigins = { "http:" }; */

		// WebIM WebSocket通道
		registry.addHandler(chatWebSocketHandler(), "/webSocketServer").addInterceptors(myInterceptor()); // 支持websocket的访问链接
		registry.addHandler(chatWebSocketHandler(), "/sockjs/webSocketServer").addInterceptors(myInterceptor()).withSockJS(); // 不支持websocket的访问链接

	}

	@Bean
	public ChatWebSocketHandler chatWebSocketHandler() {
		return new ChatWebSocketHandler();
	}

	@Bean
	public WebSocketHandshakeInterceptor myInterceptor() {
		return new WebSocketHandshakeInterceptor();
	}

}
