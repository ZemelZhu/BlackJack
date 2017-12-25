package blackjack.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import blackjack.pojo.Dealer;
import blackjack.pojo.Player;
import blackjack.pojo.Poker;
public class Game {
	private Dealer dealer = new Dealer();
	private Player player = new Player();
	private Poker poker = new Poker();

	public void init() {
		poker.shakeout();
		player.init();
		dealer.init();
	}

	public int playerAddCard() {
		/**
		 * 玩家获得一张牌
		 */
		int nextCard = poker.getNextCard();
		player.addCard(nextCard);
		return nextCard;
	}

	public int dealerAddCard() {
		/**
		 * 庄家获得一张牌
		 */
		int nextCard = poker.getNextCard();
		dealer.addCard(nextCard);
		return nextCard;
	}

	public int gameStatus() {
		// TODO Auto-generated method stub
		// 判断输赢
		return player.getPoint() - dealer.getPoint();
	}

	public boolean playerStatus() {
		if (player.getPoint() > 21)
			return false;
		return true;
	}

	public boolean dealerMaxStatus() {
		if (dealer.getPoint() > 21)
			return false;
		return true;
	}

	public boolean dealerMinStatus() {
		// TODO Auto-generated method stub
		if (dealer.getPoint() < 17)
			return true;
		return false;
	}

	public int getPlayerHideCard() {
		return player.getHideCard();
	}

	public int getDealerHideCard() {
		return dealer.getHideCard();
	}

	public int getDealerPoint() {
		return dealer.getPoint();
	}
}
