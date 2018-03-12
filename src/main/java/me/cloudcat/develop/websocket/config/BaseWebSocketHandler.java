package me.cloudcat.develop.websocket.config;

import com.alibaba.fastjson.JSON;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.entity.message.OutputObject;
import me.cloudcat.develop.entity.type.RefreshStatus;
import me.cloudcat.develop.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseWebSocketHandler implements WebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger("socket");

	private static Map<String, WebSocketSession> wsSessions = new HashMap<String, WebSocketSession>();

	// 连接建立后处理
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		try {
			wsSessions.put(session.getAttributes().get(Constant.SESSION_SOCKET).toString(), session);
            ThreadUtils.setObserver(ThreadUtils.getObserver() + 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// 抛出异常时处理
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		wsSessions.remove(session.getAttributes().get(Constant.SESSION_SOCKET));
        ThreadUtils.setObserver(ThreadUtils.getObserver() - 1);
		logger.error("socket error! " + exception.toString());
	}

	// 连接关闭后处理
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		wsSessions.remove(session.getAttributes().get(Constant.SESSION_SOCKET));
        ThreadUtils.setObserver(ThreadUtils.getObserver() - 1);
        logger.info("socket closed.");
        if (ThreadUtils.getObserver() <= 0) {
            ThreadUtils.setRefreshStatus(RefreshStatus.NONE);
        }
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 向用户发送数据
	 * 
	 * @param userName
	 * @param opo
	 */
	public void sendMessageToUser(String userName, OutputObject opo) {
		WebSocketSession wsSession = wsSessions.get(userName);
		if (wsSession != null) {
			if (wsSession.isOpen()) {
				try {
					wsSession.sendMessage(new TextMessage(JSON.toJSONString((opo))));
					logger.info("send message : " + userName + "  " + JSON.toJSONString((opo)));
				} catch (IOException e) {
					logger.info("send message error ！");
				}
			}
		}
	}

	/**
	 * 向用户发送数据(广播)
	 * 
	 * @param opo
	 */
	public void sendMessageToAll(OutputObject opo) {
        for (Object username : wsSessions.keySet()){
            sendMessageToUser(username.toString(), opo);
        }
	}

	/**
	 * 获取socket session
	 * 
	 * @param userName
	 * @return
	 */		
	public WebSocketSession getWebSocketSession(String userName) {
		return wsSessions.get(userName);
	}
}
