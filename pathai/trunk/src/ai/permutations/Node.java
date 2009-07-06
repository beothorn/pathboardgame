package ai.permutations;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.PlaySequence;
import gameLogic.board.ValidPlay;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node superNode;
	private final List<Node> childNodes = new ArrayList<Node>();
	private final Play play;
	private final PlayEvaluator evaluator;
	private Board board;
	
	public Node(final int nodeValue,final boolean isMovePlay,final PlayEvaluator evaluator) {
		this.evaluator = evaluator;
		if(isMovePlay){
			char c;
			final int direction = ((nodeValue%4)+1);
			switch(direction){
			case 1:
				c = Play.UP;
				break;
			case 2:
				c = Play.DOWN;
				break;
			case 3:
				c = Play.LEFT;
				break;
			case 4:
				c = Play.RIGHT;
				break;
			default:
				throw new RuntimeException("Char "+direction);
			}
			final int pieceNumber = (nodeValue/4)+1;
			play = new Play(pieceNumber,c);
		}else{
			play = new Play(nodeValue);
		}		
	}

	public void addNode(final Node n){
		n.superNode = this;
		childNodes.add(n);
	}
	
	@Override
	public String toString() {
		return play.toString();
	}
	
	public PlaySequence getPlaySequence(){
		if(isRoot()){
			return new PlaySequence();
		}
		final PlaySequence superPlaySequence = superNode.getPlaySequence();
		superPlaySequence.addPlay(play);
		return superPlaySequence;
	}
	
	private boolean isRoot() {
		return superNode == null;
	}

	public void setBoard(final Board board) {
		this.board = board;
	}
	
	public void sendAllPlaysToEvaluator(){
		if(stopCalculating()){
			return;
		}
		if(!isRoot()){
			copySuperBoard();
			final ValidPlay validPlay;
			try {
				final boolean isTopPlay = false;
				//TODO: too heavy here
				validPlay = board.validatePlay(play, isTopPlay);
			} catch (final InvalidPlayException e) {
				return;
			}
			board.play(validPlay, false);
			evaluator.evaluatePlay(getPlaySequence(), board);
		}		
		// TODO: faster method to find winner (too heavy here)
//		if(board.isGameEnded()){
//			return;
//		}		
		for (final Node n : childNodes) {
			n.sendAllPlaysToEvaluator();
		}
	}

	private boolean stopCalculating() {
		return evaluator.stopEvaluatingAndUseBestPlay();
	}

	private void copySuperBoard() {
		if(board == null){
			board = new Board();
		}
		board.copyFrom(superNode.board);
	}
}
