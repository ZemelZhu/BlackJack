package blackjack.pojo;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import blackjack.Template.CardTemplate;

/*庄家类*/
@Component
public class Dealer {

	/**
	 * 点数 point 
	 * 牌数量cardNumber 
	 * 牌形cardShape
	 */
	private int point = 0;
	private int cardNumber = 0;
	private ArrayList<Integer> cardShape = new ArrayList<Integer>();

	public void addCard(int card) {
		point += CardTemplate.pointToNumber(card);
		cardNumber++;
		cardShape.add(card);
	}

	public void init() {
		point = 0;
		cardNumber = 0;
		cardShape.clear();
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public ArrayList<Integer> getCardShape() {
		return cardShape;
	}

	public void setCardShape(ArrayList<Integer> cardShape) {
		this.cardShape = cardShape;
	}
	public int getHideCard() {
		return cardShape.get(0);
	}
}
