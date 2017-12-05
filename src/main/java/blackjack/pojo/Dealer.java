package blackjack.pojo;

import java.util.ArrayList;
/*庄家类*/
public class Dealer {
	
	/**
	 * 点数 point
	 * 牌数量cardNumber
	 * 牌形cardShape
	 */
	private int point=0;
	private int cardNumber=0;
	private ArrayList<String> cardShape=new ArrayList<String>();
	public void addCard(String card,int cardpoint) {
		point+=cardpoint;
		cardNumber++;
		cardShape.add(card);
	}
}
