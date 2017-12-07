package blackjack.handler;



import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import blackjack.core.GameRoom;

@Component
public class GameHandler {
	private int i=0;
	
	
	public void handleMessage(WebSocketSession webSocketSession, String message) {
		// TODO Auto-generated method stub
		
	}


	public void connectionEstablished(WebSocketSession session) {
		// TODO Auto-generated method stub
		System.out.println(i++);
	}


	public void handlingMessage(GameRoom gameRoom, String string) {
		// TODO Auto-generated method stub
		
	}
}
