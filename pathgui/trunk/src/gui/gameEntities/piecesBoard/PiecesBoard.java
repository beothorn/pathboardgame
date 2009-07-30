package gui.gameEntities.piecesBoard;
import gameEngine.JGamePanel;
import gameEngine.gameMath.Point;
import gameLogic.board.Board;
import gameLogic.board.piece.Piece;
import gameLogic.gameFlow.gameStates.GameState;
import gameLogic.gameFlow.gameStates.GameStateGameEnded;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;
import gameLogic.gameFlow.gameStates.StateVisitor;
import gui.GameDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PiecesBoard implements StateVisitor{

	private final JGamePanel panel;
	private final List<EntityPiece> entityPieces;
	private final Board board;
	private final GameDefinitions gameDefinitions;

	public PiecesBoard(final Board board,final GameState gameState,final JGamePanel panel,final GameDefinitions gameDefinitions) {
		this.gameDefinitions = gameDefinitions;
		this.board = board;
		this.panel = panel;
		entityPieces = new ArrayList<EntityPiece>();
		refreshBoard(gameState);
	}

	private ArrayList<EntityPiece> cloneEntityPieces() {
		return new ArrayList<EntityPiece>(entityPieces);
	}

	private boolean correspondToALogicPiece(final EntityPiece p) {
		for (int i = 0; i< Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				if(p.ownsPiece(board.getPieceAt(i, j))) {
					return true;
				}
			}
		}
		return false;
	}

	private void createNewEntityPieceForLogicPieceIn(final Piece logicPiece, final int line,final int column) {
		final EntityPiece newPiece = newPieceOwning(logicPiece);
		int createAboveOrBelowBoard = 0;
		if(line == 0) {
			createAboveOrBelowBoard = -1;
		}
		if(line == Board.BOARD_SIZE-1) {
			createAboveOrBelowBoard = Board.BOARD_SIZE;
		}
		final double newPieceX = getXForPieceAt(column);
		final double newPieceY = getYForPieceAt(createAboveOrBelowBoard);
		newPiece.setPosition(new Point(newPieceX,newPieceY));
		setNewPositionToEntityPieceGo(newPiece,line,column);
	}

	private void destroyDiscardedPieces(){
		final ArrayList<EntityPiece> piecesCopy = cloneEntityPieces();
		for (final EntityPiece entityPiece : piecesCopy) {
			if(!correspondToALogicPiece(entityPiece)) {
				entityPiece.markToBeDestroyed();
				entityPieces.remove(entityPiece);
			}
		}
	}

	public double getBoardHeight(){
		return Board.BOARD_SIZE*getGridHeight();
	}

	public double getBoardWidth(){
		return Board.BOARD_SIZE*getGridWidth();
	}

	public double getGridHeight() {
		return gameDefinitions.getGridHeight();
	}

	public double getGridWidth() {
		return gameDefinitions.getGridWidth();
	}

	public double getX() {
		return gameDefinitions.getBoardX();
	}

	private double getXForPieceAt(final int column){
		return getX()+column*getGridWidth();
	}

	public double getY() {
		return gameDefinitions.getBoardY();
	}

	private double getYForPieceAt(final int line){
		return getY()+line*getGridHeight();
	}

	private void internalRefreshBoard(final boolean isBottomMovingStrongsTurn,final boolean isTopMovingStrongsTurn,final Set<Piece> alreadyMoved) {
		for (int i = 0; i< Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				refreshPieceAt(board.getPieceAt(i, j),isBottomMovingStrongsTurn,isTopMovingStrongsTurn,alreadyMoved, i, j);
			}
		}
		destroyDiscardedPieces();
	}

	private EntityPiece newPieceOwning(final Piece logicPiece) {
		final EntityPiece newPiece = EntityPieceFactory.entityPieceOwningThis(logicPiece,gameDefinitions);
		entityPieces.add(newPiece);
		panel.addGameElement(newPiece);
		panel.addStepAction(newPiece.getStepAction());
		return newPiece;
	}

	@Override
	public void onGameEnded(final GameStateGameEnded gameStateGameEnded) {
		internalRefreshBoard(false,false,gameStateGameEnded.getAlreadyMovedOrEmptySet());
	}

	@Override
	public void onMovingStrongs(final GameStateMovingStrongs gameStateMovingStrongs) {
		internalRefreshBoard(gameStateMovingStrongs.isBottomPlayerTurn(),gameStateMovingStrongs.isTopPlayerTurn(),gameStateMovingStrongs.getAlreadyMovedOrEmptySet());
	}

	@Override
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs) {
		internalRefreshBoard(false,false,gameStatePuttingStrongs.getAlreadyMovedOrEmptySet());
	}

	@Override
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks) {
		internalRefreshBoard(false,false,gameStatePuttingWeaks.getAlreadyMovedOrEmptySet());
	}

	public void refreshBoard(final GameState gameState){
		gameState.accept(this);
	}

	private void refreshPieceAt(final Piece logicPiece,final boolean isBottomMovingStrongsTurn,final boolean isTopMovingStrongsTurn,final Set<Piece> alreadyMoved, final int line, final int column) {
		if(!logicPiece.isEmpty()){
			for (final EntityPiece entityPiece : entityPieces) {
				if(entityPiece.ownsPiece(logicPiece)){
					if(logicPiece.isStrong()){
						final boolean pieceAlreadyMoved = alreadyMoved.contains(logicPiece);
						if(pieceAlreadyMoved){
							entityPiece.setState(true,true);
						}else{
							final boolean isPlayingBottom = isBottomMovingStrongsTurn && logicPiece.isBottomPlayerStrongPiece();
							final boolean isPlayingTop = isTopMovingStrongsTurn && logicPiece.isTopPlayerStrongPiece();
							if(isPlayingBottom || isPlayingTop) {
								entityPiece.setState(false,true);
							}else{
								entityPiece.setState(false,false);
							}
						}
					}
					setNewPositionToEntityPieceGo(entityPiece,line,column);
					return;
				}
			}
			createNewEntityPieceForLogicPieceIn(logicPiece, line, column);
		}
	}

	private void setNewPositionToEntityPieceGo(final EntityPiece p, final int line, final int column) {
		p.setPointToGo(new Point(getXForPieceAt(column), getYForPieceAt(line)));
	}
}
