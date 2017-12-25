package blackjack.rooms;

import java.io.IOException;

import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import blackjack.core.GameRoom;

@Service
public class RoomHandler {
	private HashMap<String, GameRoom> NumberIndexRoom = new HashMap<String, GameRoom>();
	private HashMap<WebSocketSession, String> sessionNumber = new HashMap<WebSocketSession, String>();

	public void creatRoom(WebSocketSession session) throws IOException, InterruptedException {
		// 创建房间
		int number = creatRoomNumber();
		GameRoom gameRoom = new GameRoom();
		System.out.println(gameRoom);
		gameRoom.setGameNumber(number + "", session);
		NumberIndexRoom.put(number + "", gameRoom);
		sessionNumber.put(session, number + "");
	}

	private int creatRoomNumber() {
		// 创建唯一的房间号
		Random random = new Random();
		int nextInt;
		while (true) {
			nextInt = random.nextInt(900000) + 100000;
			if (NumberIndexRoom.get(nextInt) == null) {
				return nextInt;
			}
		}
	}

	public void receiveMessage(WebSocketSession session, String request) throws 
	IOException, InterruptedException {
		// TODO Auto-generated method stub
		// 信息接收，判断是对房间操作还是对游戏对局操作
		String substring = request.substring(0, 1);
		if (request.equals("7")) {// 创建房间请求
			creatRoom(session);
		} else if (substring.equals("8")) {// 加入房间请求
			joinRoom(session, request.substring(1));
		} else {// 对房间内游戏对局进行请求
			GameRoom gameRoom2 = NumberIndexRoom.get(sessionNumber.get(session));
			gameRoom2.receive(session, request);
		}
	}

	private void joinRoom(WebSocketSession session, String number) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// 加入房间
		GameRoom gameRoom = NumberIndexRoom.get(number);
		System.out.println(gameRoom + "房间");
		if (gameRoom == null) {
			session.sendMessage(new TextMessage("b房间不存在"));
		} else {
			if (gameRoom.getRoomOnline() >= 2) {
				session.sendMessage(new TextMessage("b房间人数已满"));
			} else {
				gameRoom.joinRoom(session);
				sessionNumber.put(session, number);
				session.sendMessage(new TextMessage("a" + number));
			}
		}
	}

	public void error(WebSocketSession session) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// session异常
		String number = sessionNumber.get(session);
		if (number == null)// 还未加入房间，不用处理异常
			return;
		GameRoom gameRoom = NumberIndexRoom.get(number);
		if (gameRoom.getRoomOnline() == 1) {
			// 如果房间只有一个人，直接删除房间
			NumberIndexRoom.remove(number);
		} else {
			// 房间两个人，移除异常的session
			gameRoom.removeSession(session);
		}
		sessionNumber.remove(session);// session的房间号移除
	}

}
