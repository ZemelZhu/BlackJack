package blackjack.pojo;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import blackjack.Template.CardTemplate;

/*庄家类*/
public class Dealer {

	private int point = 0;// 点数
	private int blackCard;// 是否存在A
	private ArrayList<Integer> cardShape = new ArrayList<Integer>();// 牌

	public void addCard(int card) {
		// 庄家摸一张牌
		int pointToNumber = CardTemplate.pointToNumber(card);
		if (pointToNumber == 1) // A可以当作11使用
			blackCard = 10;
		point += pointToNumber;
		cardShape.add(card);
	}

	public void init() {
		point = 0;
		blackCard = 0;
		cardShape.clear();
	}

	public int getPoint() {
		// 获得庄家当前点数
		if (point + blackCard <= 21)
			return point + blackCard;
		return point;
	}

	public int getHideCard() {
		// 获得庄家的暗牌
		return cardShape.get(0);
	}
}
