package blackjack.pojo;

import java.util.ArrayList;
import java.util.Random;

public class Card {
	/**
	 *扑克牌类 
	 *方块♦diamond
	 *梅花♣club
	 *红心♥heart
	 *黑桃♠spade
	 */
	private int cardNumber;
	private ArrayList<String> cards = new ArrayList<String>();
	String[] cardTemplate = { "heartA", "heart2", "heart3", "heart4", "heart5", "heart6", "heart7", "heart8", "heart9",
			"heart10", "heartJ", "heartQ", "heartK", "SpadesA", "Spades2", "Spades3", "Spades4", "Spades5", "Spades6",
			"Spades7", "Spades8", "Spades9", "Spades10", "SpadesJ", "SpadesQ", "SpadesK", "clubA", "club2", "club3",
			"club4", "club5", "club6", "club7", "club8", "club9", "club10", "clubJ", "clubQ", "clubK", "diamondA",
			"diamond2", "diamond3", "diamond4", "diamond5", "diamond6", "diamond7", "diamond8", "diamond9", "diamond10",
			"diamondJ", "diamondQ", "diamondK" };

	public Card() {
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
			cards.add(cardTemplate[random]);
		}

	}
	public int cardToPoint(String card) {
		for(int index=0;index<52;index++) {
			if(cardTemplate[index]==card) {
				int i= (index+1)%13;
				if(i==0||i==11||i==12) i=10;
				return i;
			}
		}
		return 0;
		
	}
	public String getNextCard() {

		return cards.get(--cardNumber);

	}
	public static void main(String[] args) {
		Card card = new Card();
		for(int i=0;i<52;i++)
		{
			String nextCard = card.getNextCard();
			int cardToPoint = card.cardToPoint(nextCard);
			System.out.println(nextCard+"==>"+cardToPoint);
		}
	}
}
