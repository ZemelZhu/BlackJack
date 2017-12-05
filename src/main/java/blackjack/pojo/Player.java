package blackjack.pojo;

import java.util.ArrayList;

public class Player {
	private int point=0;
	private int cardNumber=0;
	private ArrayList<String> cardShape=new ArrayList<String>();
	public Player() {
//		cardShape.add(2);
		
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
	
	public void addCard(String card,int cardpoint) {
		point+=cardpoint;
		cardNumber++;
		cardShape.add(card);
	}
	public static void main(String[] args) {
		Player player = new Player();
//		for (Integer integer : player.cardShape) {
//			System.out.println(integer);
//		}
	}
}
