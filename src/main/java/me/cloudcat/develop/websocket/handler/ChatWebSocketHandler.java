package me.cloudcat.develop.websocket.handler;

import me.cloudcat.develop.Constant;
import me.cloudcat.develop.controller.DomainController;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.config.BaseWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


/**
 * @author ZZWang
 * @Time 2017年3月21日  上午10:32:34
 *
 */
public class ChatWebSocketHandler extends BaseWebSocketHandler {

	@Override
	public void handleMessage(WebSocketSession wsSession, WebSocketMessage<?> message) throws Exception {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		super.afterConnectionClosed(session, closeStatus);
		// 中断线程
		ThreadUtils.setInterrupt(true);
	}
}
