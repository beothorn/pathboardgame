package gameLogic.gameFlow;

import gameLogic.Piece;

import java.util.ArrayList;
import java.util.List;

public class BoardListeners {

	private final List<BoardListener> boardListeners = new ArrayList<BoardListener>();

	public void addBoardListener(final BoardListener bCL){
		boardListeners.add(bCL);
	}

	public void callBoardChangedListeners(){
		final ArrayList<BoardListener> clone = cloneListenersList();
		for (final BoardListener bL : clone) {
			bL.boardChanged();
		}
	}

	public void callBoardMovedStrongListeners(final Piece movedStrongPiece){
		final ArrayList<BoardListener> clone = cloneListenersList();
		for (final BoardListener bL : clone) {
			bL.movedStrong(movedStrongPiece);
		}
	}

	public void callGameStateChangeListeners(final GameState gs){
		final ArrayList<BoardListener> clone = cloneListenersList();
		for (final BoardListener bL : clone) {
			bL.gameStateChanged(gs.isTopPlayerTurn(),gs.isBottomPlayerTurn(), gs);
		}
	}

	public void callSelectedStrongListeners(final Piece selected){
		final ArrayList<BoardListener> clone = cloneListenersList();
		for (final BoardListener bL : clone) {
			bL.selectedStrong(selected);
		}
	}

	public void callUnselectedStrongListeners(final Piece unselected){
		final ArrayList<BoardListener> clone = cloneListenersList();
		for (final BoardListener bL : clone) {
			bL.unselectedStrong(unselected);
		}
	}

	private ArrayList<BoardListener> cloneListenersList() {
		return new ArrayList<BoardListener>(boardListeners);
	}
}
