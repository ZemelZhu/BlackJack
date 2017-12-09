package blackjack.socket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import blackjack.core.GameRoom;
import blackjack.handler.GameHandler;

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
	private GameHandler gameHandler;
	@Autowired
	GameRoom gameRoom;
	private static final Logger logger = Logger.getLogger(WebSocketHander.class);
	private  HashMap<WebSocketSession, GameRoom> sessionIndexRoom = new HashMap<WebSocketSession, GameRoom>();
	private  ArrayList<WebSocketSession> waitingSession = new ArrayList<WebSocketSession>();
//	游戏大厅
	// private HashSet<WebSocketSession> set= new HashSet<WebSocketSession>();
	// 初次链接成功执行
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("链接成功......");
		System.out.println(session+"begin***");
		matching(session);
	
	}

	// 接受消息处理消息

	public void handleMessage(WebSocketSession webSocketSession, 
			WebSocketMessage<?> webSocketMessage)throws Exception {
		GameRoom room = sessionIndexRoom.get(webSocketSession);
		if(room==null) return ;
		room.receive(webSocketSession,webSocketMessage.getPayload()+"");
	}

	public void matching(WebSocketSession session) throws IOException, InterruptedException {
		if(waitingSession.size()>0) {
//			获得等待的session
			WebSocketSession waitedSession = waitingSession.get(0);
			gameRoom.setameRoom(waitedSession,session);
//			记录session进入的房间号
			sessionIndexRoom.put(waitedSession, gameRoom);
			sessionIndexRoom.put(session, gameRoom);
//			房间2人了，房间号+1
//			等待的session为0了
//			gameRoom.beginGame();
			waitingSession.remove(0);
			
		}
		else {
			waitingSession.add(session);
		}
		for (WebSocketSession webSocketSession : waitingSession) {
			System.out.println(webSocketSession+"@@@");
		}
	
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
		GameRoom room = sessionIndexRoom.get(session);
		System.out.println("错误session==>"+room);
		if(room==null) {
//			matching(session);
			return ;
		}
		else {
			WebSocketSession livingSession = room.getLivingSession(session);
			System.out.println("错误session=1=>"+livingSession);
			livingSession.sendMessage(new TextMessage("8"));
			matching(livingSession);
			sessionIndexRoom.remove(livingSession);
			sessionIndexRoom.remove(session);
		}
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