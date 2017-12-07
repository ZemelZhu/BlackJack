package blackjack.core;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import blackjack.socket.WebSocketHander;
@Scope("prototype")  
@Component
public class GameRoom {
	@Autowired
	private Game game;
	private WebSocketSession playerSession;
	private WebSocketSession dealerSession;
	private WebSocketHander webSocketHander=new WebSocketHander();
	private ArrayList<HttpSession> sessions = new ArrayList<HttpSession>();
	
	public void setameRoom(WebSocketSession playerSession, WebSocketSession dealerSession) {
		// TODO Auto-generated constructor stub
		this.playerSession=playerSession;
		this.dealerSession=dealerSession;
	}
	
	public void joinRoom(HttpSession session) throws IOException {
//		WebSocketSession a=(WebSocketSession) session;
		sessions.add(session);
		if(sessions.size()>=2) {
			game.init();
//			webSocketHander.sendMessageToUser(sessions,
//					new TextMessage("人数足够,开始游戏"));
			
		}
	
	
	}
	public void playerAction() {
		int card = game.playerAddCard();
		game.Status();
	}
	public void playerDone() {
		
	}
	public void dealerAction() {
		
	}
	
	public void broadCast(String string) throws IOException {
		// TODO Auto-generated method stub
		playerSession.sendMessage(new TextMessage(string));
		dealerSession.sendMessage(new TextMessage(string));
	}
	public void beginGame() throws IOException {
		// TODO Auto-generated method stub
		broadCast("5游戏开始");
		game.init();
		int playerAddCard = game.playerAddCard();
		dealerToPlayer("2"+playerAddCard);
		int dealerAddCard = game.dealerAddCard();
		playerToDealer("1"+dealerAddCard);
		playerAddCard = game.playerAddCard();
		broadCast("3"+playerAddCard);
		dealerAddCard = game.dealerAddCard();
		broadCast("4"+dealerAddCard);
		
	}
	public void playerToDealer(String message) throws IOException {
		dealerSession.sendMessage(new TextMessage(message));
	}
	public void dealerToPlayer(String message) throws IOException {
		playerSession.sendMessage(new TextMessage(message));
	}

	public void receive(String request) {
		// TODO Auto-generated method stub
		if(request.equals("0")) {
			playerGetCard();
			
		}
		else if (request.equals("1")) {
			playerFlopCard();
		}
	}

	private void playerFlopCard() {
		// TODO Auto-generated method stub
		
	}

	private void playerGetCard() throws IOException {
		// TODO Auto-generated method stub
		int playerAddCard = game.playerAddCard();
		broadCast("3"+playerAddCard);
		gameStatus();
	}

	private void gameStatus() {
		// TODO Auto-generated method stub
		
	}
}
