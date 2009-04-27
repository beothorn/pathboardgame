package gameLogic;

import gameLogic.board.Play;


public interface PlayListener {
	public void bottomPlayed(Play play);
	public void topPlayed(Play play);
}
