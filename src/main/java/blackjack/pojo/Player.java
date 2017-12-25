package blackjack.pojo;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import blackjack.Template.CardTemplate;
public class Player {
	private int point = 0;// 点数
	private int blackCard = 0;// 是否存在A
	private ArrayList<Integer> cardShape = new ArrayList<Integer>();// 牌

	public void init() {
		// 初始化
		point = 0;
		blackCard = 0;
		cardShape.clear();
	}

	public int getPoint() {
		// 获得玩家当前状态的点数
		if (point + blackCard <= 21)
			return point + blackCard;
		return point;
	}

	public void addCard(int card) {
		// 玩家摸一张牌
		int pointToNumber = CardTemplate.pointToNumber(card);
		if (pointToNumber == 1) // A可当做11使用
			blackCard = 10;
		point += pointToNumber;
		cardShape.add(card);
	}

	public int getHideCard() {
		// 结算的时候获得玩家的暗牌
		return cardShape.get(0);
	}
}
