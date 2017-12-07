package blackjack.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardRoom {
	/**
	 * 房间类
	 * 创建庄家和玩家
	*/
	@Autowired
	private Dealer dealer;
	@Autowired
	private Player player;
//	@Autowired
//	private Card cards;
//	
//	
//	public void ganme() {
//		playerAddCard();
//		playerAddCard();
//		dealerAddCard();
//		dealerAddCard();
//	}
//	
//
//	public int work() {
//		// TODO Auto-generated method stub
//		int playerAddCard = playerAddCard();
//		System.out.println(player.getPoint());
//		if(player.getPoint()>21) {
//			return 0;
//		}
//		return playerAddCard;
//	}
//	public int work2() {
//		// TODO Auto-generated method stub
//		int playerAddCard = dealerAddCard();
//		
//		return playerAddCard;
//	}
//
//	public String work3() {
//		// TODO Auto-generated method stub
//		String dealerAddCard ="";
//		while(dealer.getPoint()<=21) {
//			if(dealer.getPoint()>=17) break;
//			dealerAddCard= dealerAddCard+dealerAddCard();
//			
//		}
//		if(dealer.getPoint()>21) return "0";
//		return dealerAddCard;
//	}
}
