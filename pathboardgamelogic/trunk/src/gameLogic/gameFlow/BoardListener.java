package gameLogic.gameFlow;

import gameLogic.Piece;


public interface BoardListener {

	public void boardChanged();

	public void gameStateChanged(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn,final GameState gameState);

	public void movedStrong(Piece movedPiece);

	public void selectedStrong(Piece selectedPiece);

	public void unselectedStrong(Piece unselectedPiece);
}
