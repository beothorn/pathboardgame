package gui.gameEntities;
import gameEngine.GameElement;
import gameEngine.gameMath.Point;
import gameLogic.Game;
import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.ValidPlay;
import gameLogic.board.piece.Piece;
import gameLogic.gameFlow.gameStates.GameState;
import gui.GameLayoutDefinitions;
import gui.entityPiece.EntityPiece;
import gui.entityPiece.EntityPieceFactory;
import gui.entityPiece.EntityPieceStrong;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

//TODO: This Class is waaaaaaay to long
public class PiecesBoard implements GameElement,MouseListener{

	final static private double gridHeight = GameLayoutDefinitions.gridSize;
	final static private double gridWidth = GameLayoutDefinitions.gridSize;
	private final BoardGamePanel currentGame;
	private final List<EntityPiece> entityPieces;
	private Point position = new Point();
	private final Point startDrag = new Point();

	public PiecesBoard(final BoardGamePanel game) {
		game.addMouseListener(this);
		entityPieces = new ArrayList<EntityPiece>();
		currentGame = game;
		refreshBoard();
	}

	private ArrayList<EntityPiece> cloneEntityPieces() {
		return new ArrayList<EntityPiece>(entityPieces);
	}

	private boolean correspondToALogicPiece(final EntityPiece p) {
		final Board board = getBoard();
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
		final EntityPiece newPiece = EntityPieceFactory.entityPieceOwningThis(logicPiece);
		entityPieces.add(newPiece);
		currentGame.addGameElement(newPiece);
		currentGame.addStepAction(newPiece.getStepAction());
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

	public void destroyDiscardedPieces(){
		final ArrayList<EntityPiece> piecesCopy = cloneEntityPieces();
		for (final EntityPiece entityPiece : piecesCopy) {
			if(!correspondToALogicPiece(entityPiece)) {
				entityPiece.setMarkedToBeDestroyed(true);
				entityPieces.remove(entityPiece);
			}
		}
	}

	@Override
	public void doStep(final long delta) {
		//NOTAISSUE: unused event
	}

	@Override
	public void draw(final Graphics g) {
		//		board.draw(g, (int)getX(), (int)getY());
	}

	public void gameTurnAdvanced() {
		for (final EntityPiece p : cloneEntityPieces()) {
			p.reset();
		}
	}

	private Board getBoard(){
		return getCurrentGame().getBoard();
	}

	public double getBoardHeight(){
		return Board.BOARD_SIZE*gridHeight;
	}

	public double getBoardWidth(){
		return Board.BOARD_SIZE*gridWidth;
	}

	public Game getCurrentGame() {
		return currentGame.getGame();
	}

	private GameState getCurrentState() {
		return getCurrentGame().getCurrentState();
	}

	public double getX() {
		return position.getX();
	}

	private double getXForPieceAt(final int column){
		return getX()+column*gridWidth;
	}

	public double getY() {
		return position.getY();
	}

	private double getYForPieceAt(final int line){
		return getY()+line*gridHeight;
	}

	@Override
	public boolean markedToBeDestroyed() {
		return false;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		//		System.out.println(e);
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		//		System.out.println(e);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		final int mouseX = e.getX();
		final int mouseY = e.getY();
		final double boardX1 = getX();
		final double boardX2 = boardX1+getBoardWidth();
		final double boardY1 = getY();
		final double boardY2 = boardY1+getBoardHeight();
		if(mouseX<boardX1){return;}
		if(mouseX>boardX2){return;}
		if(mouseY<boardY1){return;}
		if(mouseY>boardY2){return;}

		startDrag.setX((int) ((mouseX - boardX1)/ gridWidth));
		startDrag.setY((int) ((mouseY - boardY1)/ gridHeight));
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		final int mouseX = e.getX();
		final int mouseY = e.getY();
		final double boardX1 = getX();
		final double boardX2 = boardX1+getBoardWidth();
		final double boardY1 = getY();
		final double boardY2 = boardY1+getBoardHeight();
		if(mouseX<boardX1) {return;}
		if(mouseX>boardX2) {return;}
		if(mouseY<boardY1) {return;}
		if(mouseY>boardY2) {return;}
		final int pieceColumn = (int) ((mouseX - boardX1)/ gridWidth);
		final int pieceLine = (int) ((mouseY - boardY1)/ gridHeight);
		final Point endDrag = new Point(pieceColumn, pieceLine);
		final ProcessPlay play = new ProcessPlay(startDrag, endDrag,getCurrentGame().isTopPlayerTurn(), getCurrentGame());
		try {
			final ValidPlay validPlay = getCurrentGame().validatePlay(play.getPlayOrNull(), getCurrentGame().isTopPlayerTurn());
			getCurrentGame().play(validPlay);
		} catch (final InvalidPlayException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		refreshBoard();
	}

	public void refreshBoard(){
		final Board board = getBoard();
		for (int i = 0; i< Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				refreshPieceAt(board.getPieceAt(i, j), i, j);
			}
		}
		destroyDiscardedPieces();
	}

	private void refreshPieceAt(final Piece logicPiece, final int line, final int column) {
		if(!logicPiece.isEmpty()){
			for (final EntityPiece entityPiece : entityPieces) {
				if(entityPiece.ownsPiece(logicPiece)){
					if(logicPiece.isStrong()){
						//TODO: CLassCast??? Something is wrong here
						final EntityPieceStrong entityPieceStrong = (EntityPieceStrong)entityPiece;
						final boolean idAlreadyMoved = getCurrentState().getAlreadyMovedOrEmptySet().contains(logicPiece.getId());
						if(idAlreadyMoved){
							entityPieceStrong.setMoved(true);
						}else{
							entityPieceStrong.setMoved(false);
						}
					}
					setNewPositionToEntityPieceGo(entityPiece,line,column);
					return;
				}
			}
			createNewEntityPieceForLogicPieceIn(logicPiece, line, column);
		}
	}

	public void selectedStrong(final Piece selectedPiece) {
		for (final EntityPiece p : entityPieces) {
			if(p.ownsPiece(selectedPiece)) {
				((EntityPieceStrong)p).setSelected(true);
			}
		}
	}

	private void setNewPositionToEntityPieceGo(final EntityPiece p, final int line, final int column) {
		p.setPointToGo(new Point(getXForPieceAt(column), getYForPieceAt(line)));
	}

	public void setPositon(final Point boardPosition) {
		position = boardPosition.copy();
	}

	public void unselectedStrong(final Piece unselectedPiece) {
		for (final EntityPiece p : entityPieces) {
			if(p.ownsPiece(unselectedPiece)) {
				((EntityPieceStrong)p).setSelected(false);
			}
		}
	}
}
