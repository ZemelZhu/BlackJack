package inter;

import org.springframework.beans.factory.annotation.Lookup;

import blackjack.core.GameRoom;

public abstract class GameRoomInterface {
	public GameRoom gameRoom;
	@Lookup("GameRoom")
	protected abstract GameRoom getGameRoom();
}
