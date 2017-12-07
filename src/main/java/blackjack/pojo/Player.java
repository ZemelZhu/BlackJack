package blackjack.pojo;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import blackjack.Template.CardTemplate;

@Component
public class Player {
	private int point = 0;
	private int cardNumber = 0;
	private ArrayList<Integer> cardShape = new ArrayList<Integer>();

	public void init() {
		point = 0;
		cardNumber = 0;
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

	public void addCard(int card) {
		point += CardTemplate.pointToNumber(card);
		cardNumber++;
		cardShape.add(card);
	}

}
