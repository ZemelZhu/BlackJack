package blackjack.pojo;

import java.util.ArrayList;


import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
public class Poker {

	private int cardNumber;//牌的数量
	private ArrayList<Integer> cards = new ArrayList<Integer>();

	public Poker() {
		shakeout();
	}

	public void shakeout() {
		/**
		 * 进行洗牌
		 */
		cards.clear();
		cardNumber = 52;
		ArrayList<Integer> setTemplate = new ArrayList<Integer>();
		for (int i = 0; i < 52; i++)
			setTemplate.add(i);
		for (int card = 52; card > 0; card--) {
			int randomNumber = new Random().nextInt(card);
			int random = setTemplate.get(randomNumber);
			setTemplate.remove(randomNumber);
			cards.add(random);
		}
	}

	public int getNextCard() {
		// 获得下一张牌
		cardNumber--;
		return cards.get(cardNumber);
	}

}
