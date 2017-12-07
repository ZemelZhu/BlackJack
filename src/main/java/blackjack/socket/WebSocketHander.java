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
	private int  roomNumber=0; //房间号
	private int waitSession=0; //等待加入房间的session
	private static final Logger logger = Logger.getLogger(WebSocketHander.class);
	private  HashMap<WebSocketSession, Integer> sessionRoomNumber = new HashMap<WebSocketSession, Integer>();
	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
//	游戏大厅
	private HashMap<Integer,GameRoom> GameHall = new HashMap<Integer,GameRoom>();
	// private HashSet<WebSocketSession> set= new HashSet<WebSocketSession>();
	// 初次链接成功执行
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("链接成功......");
		
		waitSession++;
		if(waitSession==2) {
//			获得等待的session
			WebSocketSession waitedSession = users.get(users.size()-1);
			
			gameRoom.setameRoom(waitedSession,session);
//			记录session进入的房间号
			sessionRoomNumber.put(waitedSession, roomNumber);
			sessionRoomNumber.put(session, roomNumber);
//			等待的session加入刚在大厅创建的房间
			GameHall.put(roomNumber, gameRoom);
//			房间2人了，房间号+1
			roomNumber++;
//			等待的session为0了
			waitSession=0;
//			gameHandler.handlingMessage(gameRoom,"游戏开始");
			gameRoom.beginGame();
			
		}
		users.add(session);
		for(WebSocketSession session2:sessionRoomNumber.keySet()) {
			System.out.println(session2+"==》"+sessionRoomNumber.get(session2));
		}
	}

	// 接受消息处理消息

	public void handleMessage(WebSocketSession webSocketSession, 
			WebSocketMessage<?> webSocketMessage)throws Exception {
		Integer integer = sessionRoomNumber.get(webSocketSession);
		if(integer==null) return ;
		GameRoom gameRoom = GameHall.get(integer);
		gameRoom.receive(webSocketMessage.getPayload()+"");
//		gameHandler.handleMessage(webSocketSession,webSocketMessage.getPayload()+"");
	}

	
	
	
	
	
	
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		logger.debug("链接出错，关闭链接......");
		users.remove(webSocketSession);
	}

	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		logger.debug("链接关闭......" + closeStatus.toString());
		users.remove(webSocketSession);
	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}