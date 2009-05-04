package gameLogic.board;

import org.junit.Test;

public class PlayTests {

	private final String addPiece = "0";
	private final String movePiece = "1u";
	private final String nextState = "p";
	
	@Test
	public void createAddPiecePlay() throws InvalidPlayStringException{
		new Play(addPiece);
	}
	
	@Test
	public void createMovePiecePlay() throws InvalidPlayStringException{
		new Play(movePiece);
	}
	
	@Test
	public void createNextStatePlay() throws InvalidPlayStringException{
		new Play(nextState);
	}
}
