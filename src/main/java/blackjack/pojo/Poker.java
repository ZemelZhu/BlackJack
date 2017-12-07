package blackjack.pojo;

import java.util.ArrayList;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Poker {
	/**
	 *扑克牌类 
	 *方块♦diamond
	 *梅花♣club
	 *红心♥heart
	 *黑桃♠spade
	 */
	private int cardNumber;
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
		cardNumber--;
		System.out.println(cards.size() + "***");
		return cards.get(cardNumber);

	}

}
