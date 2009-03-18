package ai.permutations;

import gameLogic.Board;
import gameLogic.Play;
import gameLogic.PlaySequence;

import java.util.ArrayList;
import java.util.List;

import aiUtils.AiUtils;

public class Node {
	private final int nodeValue;
	private final List<Node> nodes = new ArrayList<Node>();
	private final boolean isMovePlay;
	private boolean isRoot = false;
	private static boolean forcePlay = false;
	private final Node superNode;
	private Board board;
	private PlayEvaluator evaluator;
	
	public Node() {
		this(-1,false,null);
		isRoot = true;
	}	
	
	public void forcePlay(){
		forcePlay = true;
	}
	
	public Node(final int nodeValue,final boolean isMovePlay) {
		this(nodeValue,isMovePlay,null);
	}
	
	public Node(final int nodeValue,final boolean isMovePlay, final Node superNode) {
		this.nodeValue = nodeValue;
		this.isMovePlay = isMovePlay;
		this.superNode = superNode;
	}
	
	public void setEvaluatorForTree(final PlayEvaluator evaluator){
		this.evaluator = evaluator;
		for (final Node n : nodes) {
			n.setEvaluatorForTree(evaluator);
		}
	}
	
	public void addNode(final Node n){
		nodes.add(n);
	}
	
	@Override
	public String toString() {
		if(isMovePlay){
			final String play = getPlayById();
			return superNode.board.getPlaySequenceForMoveByIdPlay(new Play(play),false).toString();
		}
		return "(0,"+nodeValue+")";
	}

	private String getPlayById() {
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
			c = '!';
			break;
		}
		final int pieceNumber = (nodeValue/4)+1;
		
		final String play = Play.MOVE+pieceNumber+c;
		return play;
	}
	
	public String printPlaySequence(){
		if(isRoot){
			return "";
		}
		if(!superNode.isRoot){
			return superNode.printPlaySequence() +PlaySequence.PLAYS_SEPARATOR+ toString();
		}
		return toString();
	}
	
	public String printBoardForThisPlay(){
		if(board == null){
			return "Invalid play";
		}
		return board.toString();
	}

	public void setBoard(final Board board) {
		this.board = board;
	}
	
	public void playTree(){
		boolean playIsValid = true;
		final boolean isTopPlay = false;
		if(!isRoot){
			if(stopCalculating()){
				return;
			}
			final Board gameBoard = copySuperBoardKeepinStrongsIds();
			playIsValid = AiUtils.simulatePlayOnBoard(gameBoard, new Play(toStringForceOnePlayNotation()), isTopPlay);
			board = gameBoard;
		}else{
			forcePlay = false;			
		}
		
		if(playIsValid){
			if(evaluator!=null){
				if(board.isGameEnded()){
					evaluator.evaluatePlay(printPlaySequence(), board.toString());
					return;
				}
				if(isMovePlay){
					final PlaySequence playSequence = new PlaySequence(printPlaySequence());
					playSequence.closeSequence();
					evaluator.evaluatePlay(playSequence.toString(), board.toString());
				}
			}
		}else{
			board = null;
			return;
		}
		
		if(stopCalculating()){
			return;
		}
		for (final Node n : nodes) {
			n.playTree();
		}
	}

	private boolean stopCalculating() {
		return evaluator.stopEvaluatingAndUseBestPlay() || forcePlay;
	}

	private String toStringForceOnePlayNotation() {
		if(isMovePlay){
			return getPlayById();
		}
		return "(0,"+nodeValue+")";
	}

	private Board copySuperBoardKeepinStrongsIds() {
		return superNode.board.copy();
	}

	public void printTree() {
		System.out.println(printPlaySequence());
		System.out.println(printBoardForThisPlay());
		if(board != null){
			for (final Node n : nodes) {
				n.printTree();
			}
		}
	}
}
