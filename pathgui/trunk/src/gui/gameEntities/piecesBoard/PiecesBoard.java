package gui.gameEntities.piecesBoard;
import gameEngine.GameElement;
import gameEngine.JGamePanel;
import gameEngine.gameMath.Point;
import gameLogic.board.Board;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;
import gui.gameEntities.piecesBoard.entityPiece.EntityPiece;
import gui.gameEntities.piecesBoard.entityPiece.EntityPieceFactory;
import gui.gameEntities.piecesBoard.entityPiece.EntityPieceStrong;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PiecesBoard implements GameElement{

	final static private double gridHeight = GameLayoutDefinitions.gridSize;
	final static private double gridWidth = GameLayoutDefinitions.gridSize;
	private final JGamePanel panel;
	private final List<EntityPiece> entityPieces;
	private final Point position;

	public PiecesBoard(final Board board,final JGamePanel panel,final Point position) {
		this(board,new LinkedHashSet<Piece>(),panel,position);
	}

	public PiecesBoard(final Board board,final Set<Piece> alreadyMoved,final JGamePanel panel,final Point position) {
		this.panel = panel;
		this.position = position;
		entityPieces = new ArrayList<EntityPiece>();
		refreshBoard(board, alreadyMoved);
	}

	private ArrayList<EntityPiece> cloneEntityPieces() {
		return new ArrayList<EntityPiece>(entityPieces);
	}

	private boolean correspondToALogicPiece(final EntityPiece p,final Board board) {
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

	private void destroyDiscardedPieces(final Board board){
		final ArrayList<EntityPiece> piecesCopy = cloneEntityPieces();
		for (final EntityPiece entityPiece : piecesCopy) {
			if(!correspondToALogicPiece(entityPiece, board)) {
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
		//Currently, board is drawn on background
	}

	public double getBoardHeight(){
		return Board.BOARD_SIZE*getGridHeight();
	}

	public double getBoardWidth(){
		return Board.BOARD_SIZE*getGridWidth();
	}

	public double getGridHeight() {
		return gridHeight;
	}

	public double getGridWidth() {
		return gridWidth;
	}

	public double getX() {
		return position.getX();
	}

	private double getXForPieceAt(final int column){
		return getX()+column*getGridWidth();
	}

	public double getY() {
		return position.getY();
	}

	private double getYForPieceAt(final int line){
		return getY()+line*getGridHeight();
	}

	@Override
	public boolean markedToBeDestroyed() {
		return false;
	}

	private EntityPiece newPieceOwning(final Piece logicPiece) {
		final EntityPiece newPiece = EntityPieceFactory.entityPieceOwningThis(logicPiece);
		entityPieces.add(newPiece);
		panel.addGameElement(newPiece);
		panel.addStepAction(newPiece.getStepAction());
		return newPiece;
	}

	public void refreshBoard(final Board board,final Set<Piece> alreadyMoved){
		for (int i = 0; i< Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				refreshPieceAt(board.getPieceAt(i, j),alreadyMoved, i, j);
			}
		}
		destroyDiscardedPieces(board);
	}

	private void refreshPieceAt(final Piece logicPiece,final Set<Piece> alreadyMoved, final int line, final int column) {
		if(!logicPiece.isEmpty()){
			for (final EntityPiece entityPiece : entityPieces) {
				if(entityPiece.ownsPiece(logicPiece)){
					if(logicPiece.isStrong()){
						//TODO: CLassCast??? Something is wrong here
						final EntityPieceStrong entityPieceStrong = (EntityPieceStrong)entityPiece;
						final boolean pieceAlreadyMoved = alreadyMoved.contains(logicPiece);
						if(pieceAlreadyMoved){
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

	private void setNewPositionToEntityPieceGo(final EntityPiece p, final int line, final int column) {
		p.setPointToGo(new Point(getXForPieceAt(column), getYForPieceAt(line)));
	}
}
