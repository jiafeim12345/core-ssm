package me.cloudcat.develop.websocket.config;

import me.cloudcat.develop.Constant;
import net.sf.json.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseWebSocketHandler implements WebSocketHandler {

	private static Logger logger = Logger.getLogger(BaseWebSocketHandler.class);
	private static Map<Object, WebSocketSession> wsSessions = new HashMap<Object, WebSocketSession>();

	// 连接建立后处理
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		try {
			wsSessions.put(session.getAttributes().get(Constant.SESSION_SOCKET), session);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		logger.info("websocket连接成功!");
		System.out.println("websocket连接成功!");
	}

	// 抛出异常时处理
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		wsSessions.remove(session.getAttributes().get(Constant.SESSION_SOCKET));
		logger.info("websocket出现异常！");
		System.out.println("websocket出现异常！");
	}

	// 连接关闭后处理
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		wsSessions.remove(session.getAttributes().get(Constant.SESSION_SOCKET));
		logger.info("websocket已关闭！");
		System.out.println("websocket已关闭！");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 向用户发送数据
	 * 
	 * @param userName
	 * @param messageMap
	 */
	public void sendMessageToUser(String userName, Map<String,Object> messageMap) {
		WebSocketSession wsSession = wsSessions.get(userName);
		if (wsSession != null) {
			if (wsSession.isOpen()) {
				try {
					wsSession.sendMessage(new TextMessage(JSONUtils.valueToString(messageMap)));
				} catch (IOException e) {
					logger.info("发送信息失败！");
					System.out.println("发送信息失败！");
				}
			}
		}
	}

	/**
	 * 向用户发送数据
	 *
	 * @param userName
	 * @param jsonString
	 */
	public void sendMessageToUser(String userName, String jsonString) {
		WebSocketSession wsSession = wsSessions.get(userName);
		if (wsSession != null) {
			if (wsSession.isOpen()) {
				try {
					wsSession.sendMessage(new TextMessage(jsonString));
				} catch (IOException e) {
					logger.info("发送信息失败！");
					System.out.println("发送信息失败！");
				}
			}
		}
	}

	/**
	 * 向用户发送数据(广播)
	 * 
	 * @param userNameList
	 * @param messageMap
	 */
	public void sendMessageToGroupUser(List<String> userNameList, Map<String,Object> messageMap) {
		for (String userName : userNameList) {
			sendMessageToUser(userName, messageMap);
		}
	}

	/**
	 * 获取websocketsession
	 * 
	 * @param userName
	 * @return
	 */		
	public WebSocketSession getWebSocketSession(String userName) {
		return wsSessions.get(userName);
	}
	
	
}
