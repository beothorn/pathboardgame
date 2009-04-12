package gameLogic.gameFlow;

import java.util.ArrayList;
import java.util.List;

public class BoardListeners {

	private final List<BoardListener> boardListeners = new ArrayList<BoardListener>();

	public void addBoardListener(final BoardListener bCL){
		boardListeners.add(bCL);
	}

	public void callGameStateChangeListeners(final GameState gs){
		final ArrayList<BoardListener> clone = cloneListenersList();
		for (final BoardListener bL : clone) {
			bL.gameStateChanged(gs.isTopPlayerTurn(),gs.isBottomPlayerTurn(), gs);
		}
	}

	private ArrayList<BoardListener> cloneListenersList() {
		return new ArrayList<BoardListener>(boardListeners);
	}
}
