package blackjack.core;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class GameRoom {
	private Game game= new Game();
	private String gameNumber;// 房间号
	private int roomOnline = 0;
	private WebSocketSession playerSession;
	private WebSocketSession dealerSession;
	private int waitSessionNumber = 0;

	public void setGameNumber(String number, WebSocketSession session) throws IOException, InterruptedException {
		this.gameNumber = number;
		this.roomOnline = 1;
		dealerSession = session;
		playerToDealer("a" + gameNumber);
	}

	public void joinRoom(WebSocketSession session) throws IOException, InterruptedException {
		// 第二个人加入房间，可以开始游戏
		roomOnline++;
		playerSession = session;
		broadCast("p房间人数2人，可以开始游戏");
	}

	public int getRoomOnline() {// 返回房间人数
		return roomOnline;
	}

	private void broadCast(String message) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// 房间消息广播
		Thread.sleep(100);
		playerSession.sendMessage(new TextMessage(message));
		dealerSession.sendMessage(new TextMessage(message));
	}

	private void beginGame() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// 游戏开始，先轮换身份
		WebSocketSession flat = playerSession;
		playerSession = dealerSession;
		dealerSession = flat;
		game.init();// 游戏初始化
		int playerAddCard = game.playerAddCard();
		dealerToPlayer("g2" + playerAddCard);// 发送一张暗牌给玩家
		int dealerAddCard = game.dealerAddCard();
		playerToDealer("g1" + dealerAddCard);// 发送一张暗牌给庄家
		playerAddCard = game.playerAddCard();
		Thread.sleep(1000);
		broadCast("g3" + playerAddCard);// 玩家获得一张牌
		Thread.sleep(500);
		dealerAddCard = game.dealerAddCard();
		broadCast("g4" + dealerAddCard);// 庄家获得一张牌

	}

	private void playerToDealer(String message) throws IOException, InterruptedException {
		// 发送消息给庄家
		Thread.sleep(100);
		dealerSession.sendMessage(new TextMessage(message));
	}

	private void dealerToPlayer(String message) throws IOException, InterruptedException {
		// 发送信息给玩家
		Thread.sleep(100);
		playerSession.sendMessage(new TextMessage(message));
	}

	public void receive(WebSocketSession session, String request) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if (request.equals("0")) {//玩家请求获得一张牌
			playerGetCard();
		} else if (request.equals("1")) {
			// playerFlopCard();
			playerToDealer("p庄家回合");//玩家处于结束状态，等待庄家操作
		} else if (request.equals("2")) {//游戏开始
			beginGame();
		} else if (request.equals("3")) {//进行准备
			if (session != playerSession)
				dealerToPlayer("z6");//告诉对方已经准备好
			else {
				playerToDealer("z6");
			}
			if (waitSessionNumber > 0) {
				waitSessionNumber = 0;
				beginGame();
			} else {
				waitSessionNumber++;
			}
		} else if (request.equals("4")) {//取消准备
			if (session != playerSession)
				dealerToPlayer("z7");
			else {
				playerToDealer("z7");
			}
			if (waitSessionNumber > 0) {
				waitSessionNumber--;
			}
		} else if (request.equals("5")) {
			/**
			 * 庄家摸牌
			 */
			dealerGetCard();
		} else if (request.equals("6")) {
			/**
			 * 庄家摸牌
			 */
			if (game.getDealerPoint() < 17) {
				playerToDealer("p点数小于17点,必须摸牌");//点数小于17点，不能翻牌
				return;
			}

			playerFlopCard();
		}

	}

	private void dealerGetCard() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int dealerAddCard = game.dealerAddCard();
		broadCast("g4" + dealerAddCard);// 广播庄家摸牌
		if (!game.dealerMaxStatus()) {// 如果大于21点，直接表示庄家输
			playerToDealer("g50" + game.getPlayerHideCard());
			dealerToPlayer("g51" + game.getDealerHideCard());
		}
	}

	private void playerFlopCard() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		/**
		 * 游戏结束判定 g表示游戏中的判定  5表示为游戏结果输出   0表示输   1表示赢
		 */
		int status = game.gameStatus();// 获得双方点数的差值
		if (status > 0) {
			playerToDealer("g50" + game.getPlayerHideCard());
			dealerToPlayer("g51" + game.getDealerHideCard());
		} else if (status < 0) {
			playerToDealer("g51" + game.getPlayerHideCard());
			dealerToPlayer("g50" + game.getDealerHideCard());
		} else {
			playerToDealer("g52" + game.getPlayerHideCard());
			dealerToPlayer("g52" + game.getDealerHideCard());
		}
	}

	private void playerGetCard() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//玩家获得一张牌
		int playerAddCard = game.playerAddCard();
		broadCast("g3" + playerAddCard);
		if (game.playerStatus()) {//判断是否大于21点
			return;
		}
		//已经大于21点，直接表示该玩家为输
		playerToDealer("g51" + game.getPlayerHideCard());
		dealerToPlayer("g50" + game.getDealerHideCard());
	}

	public WebSocketSession getLivingSession(WebSocketSession session) {
		// TODO Auto-generated method stub
		//获得还在房间的session
		if (playerSession != session)
			return playerSession;
		return dealerSession;

	}

	public void removeSession(WebSocketSession session) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//移除异常的session
		roomOnline--;
		waitSessionNumber = 0;
		if (session == dealerSession)
			dealerSession = playerSession;
		playerSession = null;
		playerToDealer("b对方已经退出房间");//中途异常退出模块
	}
}
