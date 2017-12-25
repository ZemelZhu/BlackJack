package blackjack.Template;

import java.util.ArrayList;

public class CardTemplate {
	/**
	 * BlackJack模板类
	 * 用于转换点数与具体的牌
	 * 算法：cardTemplate数组中对应的下标为映射结果
	 *	扑克牌类 
	 *	方块♦diamond
	 *	梅花♣club
	 *	红心♥heart
	 *	黑桃♠spade
	 */
	private static String[] cardTemplate = { "heartA", "heart2", "heart3", "heart4", "heart5", "heart6", "heart7",
			"heart8", "heart9", "heart10", "heartJ", "heartQ", "heartK", "SpadesA", "Spades2", "Spades3", "Spades4",
			"Spades5", "Spades6", "Spades7", "Spades8", "Spades9", "Spades10", "SpadesJ", "SpadesQ", "SpadesK", "clubA",
			"club2", "club3", "club4", "club5", "club6", "club7", "club8", "club9", "club10", "clubJ", "clubQ", "clubK",
			"diamondA", "diamond2", "diamond3", "diamond4", "diamond5", "diamond6", "diamond7", "diamond8", "diamond9",
			"diamond10", "diamondJ", "diamondQ", "diamondK" };
	private static ArrayList<String> cards = new ArrayList<String>();

	static {
		/**
		 * 初始化 把模板映射到
		 */
		for (String card : cardTemplate) {
			cards.add(card);
		}
	}

	public static int cardToPoint(String card) {

		return cards.indexOf(card);
	}

	public static String pointToCard(int point) {
		return cards.get(point);
	}

	public static int pointToNumber(int point) {
		int i = (point + 1) % 13;
		if (i == 0 || i == 11 || i == 12)
			i = 10;
		return i;

	}
}
