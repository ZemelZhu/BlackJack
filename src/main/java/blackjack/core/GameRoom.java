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

import blackjack.pojo.Player;
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
	private int waitSessionNumber=0;
	
	public void setameRoom(WebSocketSession playerSession, WebSocketSession dealerSession) {
		// TODO Auto-generated constructor stub
		this.playerSession=playerSession;
		this.dealerSession=dealerSession;
	}
	
	public void playerAction() {
		int card = game.playerAddCard();
		game.Status();
	}
	
	public void broadCast(String string) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(100);
		playerSession.sendMessage(new TextMessage(string));
		dealerSession.sendMessage(new TextMessage(string));
	}
	public void beginGame() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
//		broadCast("5游戏开始");
		game.init();
		int playerAddCard = game.playerAddCard();
		dealerToPlayer("2"+playerAddCard);
		int dealerAddCard = game.dealerAddCard();
		playerToDealer("1"+dealerAddCard);
		playerAddCard = game.playerAddCard();
		Thread.sleep(1000);
		broadCast("3"+playerAddCard);
		Thread.sleep(1000);
		dealerAddCard = game.dealerAddCard();
		broadCast("4"+dealerAddCard);
		
	}
	public void playerToDealer(String message) throws IOException, InterruptedException {
		Thread.sleep(100);
		dealerSession.sendMessage(new TextMessage(message));
	}
	public void dealerToPlayer(String message) throws IOException, InterruptedException {
		Thread.sleep(100);
		playerSession.sendMessage(new TextMessage(message));
	}

	public void receive(WebSocketSession session, String request) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if(request.equals("0")) {
			playerGetCard();
			
		}
		else if (request.equals("1")) {
//			playerFlopCard();
			playerToDealer("9");
		}
		else if (request.equals("2")) {
			beginGame();
		}
		else if (request.equals("3")) {
			if(session!=playerSession)
				dealerToPlayer("6");
			else {
				playerToDealer("6");
			}
			if(waitSessionNumber>0) {
				waitSessionNumber=0;
				beginGame();
			}
			else {
				waitSessionNumber++;
			}
		}
		else if (request.equals("4")) {
			if(session!=playerSession)
				dealerToPlayer("7");
			else {
				playerToDealer("7");
			}
			if(waitSessionNumber>0) {
				waitSessionNumber--;
			}
		}
		else if(request.equals("5")) {
				/**
				 * 庄家摸牌			
				 */
			dealerGetCard();
		}
		else if(request.equals("6")) {
			/**
			 * 庄家摸牌			
			 */
			playerFlopCard();
	}
	}

	private void dealerGetCard() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int dealerAddCard = game.dealerAddCard();
		broadCast("4"+dealerAddCard);
		if(!game.dealerMaxStatus()) {
			playerToDealer("50"+game.getPlayerHideCard());
			dealerToPlayer("51"+game.getDealerHideCard());
		}
	}

	private void playerFlopCard() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int status =game.gameStatus();
		if(status>0) {
			playerToDealer("50"+game.getPlayerHideCard());
			dealerToPlayer("51"+game.getDealerHideCard());
		}
		else if(status<0)
		{
			playerToDealer("51"+game.getPlayerHideCard());
			dealerToPlayer("50"+game.getDealerHideCard());
		}
		else {
			playerToDealer("52"+game.getPlayerHideCard());
			dealerToPlayer("52"+game.getDealerHideCard());
		}
	}

	private void playerGetCard() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int playerAddCard = game.playerAddCard();
		broadCast("3"+playerAddCard);
		if(game.playerStatus()) {
			return ;
		}
		
		playerToDealer("51"+game.getPlayerHideCard());
		dealerToPlayer("50"+game.getDealerHideCard());
//		broadCast("6游戏结束重新开始");
//		beginGame();
	}

	private void gameStatus() {
		// TODO Auto-generated method stub
//		if(Player.)
	}

	public WebSocketSession getLivingSession(WebSocketSession session) {
		// TODO Auto-generated method stub
		if(playerSession!=session) return playerSession;
		return dealerSession;
		
	}
}
