package blackjack.socket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import blackjack.core.GameRoom;
import blackjack.handler.GameHandler;
import blackjack.rooms.RoomHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * Created by zhuqiang on 2015/6/22 0022.
 */
@Component
public class WebSocketHander implements WebSocketHandler {
	@Autowired
	private RoomHandler roomHandler; 
	private int onLineSession=0;
	private static final Logger logger = Logger.getLogger(WebSocketHander.class);
	private  ArrayList<WebSocketSession> allSession = new ArrayList<WebSocketSession>();
	// 初次链接成功执行
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("链接成功......");
		System.out.println(session+"begin***");
		onLineSession++;
		allSession.add(session);
		sendMessageToAllSession(new TextMessage("0"+allSession.size()));
		
	
	}

	private void sendMessageToAllSession(TextMessage message) throws IOException {
	// TODO Auto-generated method stub
		System.out.println(onLineSession+"@#@");
		for (WebSocketSession session : allSession) {
			session.sendMessage(message);
		}
	}

	// 接受消息处理消息

	public void handleMessage(WebSocketSession webSocketSession, 
			WebSocketMessage<?> webSocketMessage)throws Exception {
		
		roomHandler.receiveMessage(webSocketSession,webSocketMessage.getPayload()+"");
	}

	public void matching(WebSocketSession session) throws IOException, InterruptedException {
		
	
	}
	public void creatRoom(WebSocketSession session) throws IOException, InterruptedException {
		roomHandler.creatRoom(session);
	}

	
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		logger.debug("链接出错，关闭链接......");
		error(webSocketSession);
	}

	private void error(WebSocketSession session) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		onLineSession--;
		allSession.remove(session);
		sendMessageToAllSession(new TextMessage("0"+allSession.size()));
		roomHandler.error(session);
		
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