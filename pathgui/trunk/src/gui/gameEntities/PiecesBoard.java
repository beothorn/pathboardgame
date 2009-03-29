package gui.gameEntities;
import gameEngine.GameElement;
import gameEngine.gameMath.Point;
import gameLogic.Board;
import gameLogic.Game;
import gameLogic.Piece;
import gameLogic.Play;
import gameLogic.gameFlow.BoardListener;
import gui.GameLayoutDefinitions;
import gui.entityPiece.EntityPiece;
import gui.entityPiece.EntityPieceFactory;
import gui.entityPiece.EntityPieceStrong;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class PiecesBoard implements GameElement,MouseListener{

	//	private final Sprite board;
	private BoardGamePanel currentGame;
	final static private double gridHeight = GameLayoutDefinitions.gridSize;
	final static private double gridWidth = GameLayoutDefinitions.gridSize;
	private final List<EntityPiece> pieces;
	private Point position = new Point();
	private boolean pressedOnStrong;


	public PiecesBoard(final BoardGamePanel game) {
		//		board = SpriteStore.get().getSprite(GameLayoutDefinitions.board);
		game.addMouseListener(this);
		pieces = new ArrayList<EntityPiece>();
		setCurrentGame(game);
	}

	public void addBoardListener(final BoardListener b) {
		getCurrentGame().addBoardListener(b);
	}

	public void boardChanged() {
		refreshBoard();
	}

	public void cleanBoard(){
		final ArrayList<EntityPiece> piecesCopy = clonePieces();
		for (final EntityPiece p : piecesCopy) {
			if(!correspondToAPiece(p)) {
				p.setMarkedToBeDestroyed(true);
				pieces.remove(p);
			}
		}
	}

	private ArrayList<EntityPiece> clonePieces() {
		return new ArrayList<EntityPiece>(pieces);
	}

	private boolean correspondToAPiece(final EntityPiece p) {
		final Board board = getBoard();
		for (int i = 0; i< board.getBoardLineNumber(); i++) {
			for (int j = 0; j < board.getBoardColumnNumber(); j++) {
				if(p.ownsPiece(board.getPieceAt(i, j))) {
					return true;
				}
			}
		}
		return false;
	}

	private void createNewEntityPieceForPieceIn(final Piece piece, final int line,final int column) {
		final EntityPiece newPiece = EntityPieceFactory.giveMeAPiece(piece);
		pieces.add(newPiece);
		currentGame.addGameElement(newPiece);
		currentGame.addStepAction(newPiece.getStepAction());
		int createAboveOrBelowLine = line;
		if(line == 0) {
			createAboveOrBelowLine = -1;
		}
		if(line == getBoard().getBoardLineNumber()-1) {
			createAboveOrBelowLine = getBoard().getBoardLineNumber();
		}
		final double newPieceX = getXForPieceAt(column);
		final double newPieceY = getYForPieceAt(createAboveOrBelowLine);
		newPiece.setPosition(new Point(newPieceX,newPieceY));
		setNewPositionToGoFor(newPiece,line,column);
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
		for (final EntityPiece p : clonePieces()) {
			p.reset();
		}
	}

	private Board getBoard(){
		return getCurrentGame().getBoard();
	}


	public double getBoardHeight(){
		return getBoard().getBoardLineNumber()*gridHeight;
	}

	public double getBoardWidth(){
		return getBoard().getBoardColumnNumber()*gridWidth;
	}

	public Game getCurrentGame() {
		return currentGame.getGame();
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
		if(mouseX<boardX1) {
			return;
		}
		if(mouseX>boardX2) {
			return;
		}
		if(mouseY<boardY1) {
			return;
		}
		if(mouseY>boardY2) {
			return;
		}
		final int pieceColumn = (int) ((mouseX - boardX1)/ gridWidth);
		final int pieceLine = (int) ((mouseY - boardY1)/ gridHeight);

		if(getCurrentGame().getBoard().getPieceAt(pieceLine, pieceColumn).isStrong()){
			pressedOnStrong = true;
		}else{
			pressedOnStrong = false;
		}

		play(pieceColumn, pieceLine);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if(pressedOnStrong){
			final int mouseX = e.getX();
			final int mouseY = e.getY();
			final double boardX1 = getX();
			final double boardX2 = boardX1+getBoardWidth();
			final double boardY1 = getY();
			final double boardY2 = boardY1+getBoardHeight();
			if(mouseX<boardX1) {
				return;
			}
			if(mouseX>boardX2) {
				return;
			}
			if(mouseY<boardY1) {
				return;
			}
			if(mouseY>boardY2) {
				return;
			}
			final int pieceColumn = (int) ((mouseX - boardX1)/ gridWidth);
			final int pieceLine = (int) ((mouseY - boardY1)/ gridHeight);

			play(pieceColumn, pieceLine);
		}
		pressedOnStrong = false;
	}

	public void movedStrong(final Piece movedPiece) {
		refreshBoard();
		for (final EntityPiece p : pieces) {
			if(p.ownsPiece(movedPiece)) {
				final boolean moved = true;
				((EntityPieceStrong)p).setMoved(moved);
			}
		}
	}

	private void play(final int pieceColumn, final int pieceLine) {
		getCurrentGame().play(new Play(pieceLine, pieceColumn));
	}

	public void refreshBoard(){
		final Board board = getBoard();
		for (int i = 0; i< board.getBoardLineNumber(); i++) {
			for (int j = 0; j < board.getBoardColumnNumber(); j++) {
				refreshPieceAt(board.getPieceAt(i, j), i, j);
			}
		}
		cleanBoard();
	}

	private void refreshPieceAt(final Piece piece, final int line, final int column) {
		if(!piece.isEmpty()){
			for (final EntityPiece p : pieces) {
				if(p.ownsPiece(piece)){
					setNewPositionToGoFor(p,line,column);
					return;
				}
			}
			createNewEntityPieceForPieceIn(piece, line, column);
		}
	}

	public void selectedStrong(final Piece selectedPiece) {
		for (final EntityPiece p : pieces) {
			if(p.ownsPiece(selectedPiece)) {
				((EntityPieceStrong)p).setSelected(true);
			}
		}
	}

	private void setCurrentGame(final BoardGamePanel currentGame){
		this.currentGame = currentGame;
		refreshBoard();
	}

	private void setNewPositionToGoFor(final EntityPiece p, final int line, final int column) {
		p.setPointToGo(new Point(getXForPieceAt(column), getYForPieceAt(line)));
	}

	public void setPositon(final Point boardPosition) {
		position = boardPosition.copy();
	}

	public void unselectedStrong(final Piece unselectedPiece) {
		for (final EntityPiece p : pieces) {
			if(p.ownsPiece(unselectedPiece)) {
				((EntityPieceStrong)p).setSelected(false);
			}
		}
	}
}
