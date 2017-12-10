package blackjack.rooms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import blackjack.core.GameRoom;
@Component
public class RoomHandler {
	private int RoomNumber=0;
	@Autowired
	GameRoom gameRoom;
	private  HashMap<String, GameRoom> NumberIndexRoom = new HashMap<String, GameRoom>();
	private  HashMap<WebSocketSession, String> sessionNumber = new HashMap<WebSocketSession, String>();
	private  ArrayList<WebSocketSession> allSession = new ArrayList<WebSocketSession>();
	public String creatRoom(WebSocketSession session) throws IOException, InterruptedException {
		int number = creatRoomNumber();
		gameRoom.setGameNumber(number+"",session);
		NumberIndexRoom.put(number+"", gameRoom);
		sessionNumber.put(session, number+"");
		return number+"";
	}
	public int creatRoomNumber() {
		Random random = new Random();
		int nextInt;
		while(true){
			nextInt=random.nextInt(900000) + 100000;
			if(NumberIndexRoom.get(nextInt)==null) {
				return nextInt;
			}
		}
	}
	public void receiveMessage(WebSocketSession session, String request) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String substring = request.substring(0,1);
		if(request.equals("7")) {
			String roomnumber = creatRoom(session);
		}
		else if(substring.equals("8")) {
			joinRoom(session,request.substring(1));
		}
		else {
			gameRoom.receive(session, request);
		}
	}
	private void joinRoom(WebSocketSession session, String number) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		GameRoom gameRoom2 = NumberIndexRoom.get(number);
		System.out.println(gameRoom2+"房间");
		if(gameRoom2==null) {
			session.sendMessage(new TextMessage("b房间不存在"));
		}
		else {
			if(gameRoom2.getRoomOnline()>=2) {
				session.sendMessage(new TextMessage("b房间人数已满"));
			}
			else {
				gameRoom2.joinRoom(session);
				sessionNumber.put(session, number);
				session.sendMessage(new TextMessage("a"+number));
			}
		}
	}
	public void error(WebSocketSession session) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String number = sessionNumber.get(session);
		if(number==null) return ;
		GameRoom gameRoom2 = NumberIndexRoom.get(number);
		if(gameRoom2.getRoomOnline()==1) {
			NumberIndexRoom.remove(number);
		}
		else {
			gameRoom2.removeSession(session);
		}
		sessionNumber.remove(session);
	}
	
}
