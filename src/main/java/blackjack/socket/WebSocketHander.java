package blackjack.socket;

import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import blackjack.rooms.RoomHandler;

import java.io.IOException;
import java.util.ArrayList;
@Component
public class WebSocketHander implements WebSocketHandler {
	@Autowired
	private RoomHandler roomHandler;
	private static final Logger logger = Logger.getLogger(WebSocketHander.class);
	private ArrayList<WebSocketSession> allSession = new ArrayList<WebSocketSession>();

	// 初次链接成功执行
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("链接成功......");
		allSession.add(session);
		// 发送在线人数
		sendMessageToAllSession(new TextMessage("o" + allSession.size()));
	}

	private void sendMessageToAllSession(TextMessage message) throws IOException {
		// TODO Auto-generated method stub
		//发送信息给全部在线用户
		for (WebSocketSession session : allSession) {
			session.sendMessage(message);
		}
	}

	// 接受消息处理消息

	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
			throws Exception {
		//处理用户请求
		roomHandler.receiveMessage(webSocketSession, webSocketMessage.getPayload() + "");
	}

	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		logger.debug("链接出错，关闭链接......");
		error(webSocketSession);
	}

	private void error(WebSocketSession session) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		allSession.remove(session);
		// 发送在线人数
		sendMessageToAllSession(new TextMessage("o" + allSession.size()));
		roomHandler.error(session);//处理异常用户

	}

	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		logger.debug("链接关闭......" + closeStatus.toString());
		error(webSocketSession);
	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}