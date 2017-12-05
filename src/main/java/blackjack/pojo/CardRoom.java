package blackjack.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardRoom {
	/**
	 * 房间类
	 * 创建庄家和玩家
	*/
//	@Autowired
//	private Dealer dealer;
//	@Autowired
//	private Player player;
//	@Autowired
//	private Card cards;
//	public void gameStart() {
//		int card = 1;
//		cards.shakeout();
//	}
//	
//	public String playerAddCard() {
//		/**
//		 * 玩家获得一张牌
//		 */
//		String nextCard = cards.getNextCard();
//		player.addCard(nextCard, cards.cardToPoint(nextCard));
//		return nextCard;
//	}
//	public String dealerAddCard() {
//		/**
//		 * 庄家获得一张牌
//		 */
//		String nextCard = cards.getNextCard();
//		dealer.addCard(nextCard, cards.cardToPoint(nextCard));
//		return nextCard;
//	}
//	public void ganme() {
//		playerAddCard();
//		playerAddCard();
//		dealerAddCard();
//		dealerAddCard();
//	}
//	public static void main(String[] args) {
//		CardRoom cardRoom = new CardRoom();
//		cardRoom.gameStart();
//		System.out.println("是否继续");
//		
//	}
//
//	public void work() {
//		// TODO Auto-generated method stub
//		
//	}
}
