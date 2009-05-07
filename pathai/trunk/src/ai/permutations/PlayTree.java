package ai.permutations;

import gameLogic.board.Board;
import gameLogic.board.PlaySequence;
import gameLogic.gameFlow.gameStates.GameState;
import utils.Logger;

public class PlayTree {

	private static final int DIRECTIONS = 4;
	private static final int TOTAL = DIRECTIONS*GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE;

	private final Node playTree;
	private final PlayEvaluator evaluator;
	private final Logger logger = Logger.getLogger(PlayTree.class);
	
	public PlayTree(final BoardScoreCalculator calculator) {
		this.evaluator = new PlayEvaluator(calculator);
		playTree = new Node();
		logger.debug("Calculating tree...");
		addNodesNodesAndThenAddMoveNodes(playTree, 0, 0);
		logger.debug("Tree calculated");
	}
	
	public PlaySequence bestPlayFor(final Board board){
		evaluator.reset();
		playTree.setBoard(board);
		playTree.sendAllPlaysToEvaluator();
		return evaluator.getBestPlay();
	}
	
	private void addNodesNodesAndThenAddMoveNodes(final Node superNode,final int start,final int level){
		if(level >= GameState.NUMBER_OF_WEAK_PIECES_TO_PUT){
			moveNodes(superNode, new int[]{0,0,0});
			return;
		}
		for (int i = start; i < Board.BOARD_SIZE; i++) {
			final Node node = new Node(i,false,superNode,evaluator);
			superNode.addNode(node);
			addNodesNodesAndThenAddMoveNodes(node, i, level+1);
		}
	}
	
	private void moveNodes(final Node superNode, final int[] remainsAlreadyCalculated){
		if(allRemaindersCalculated(remainsAlreadyCalculated)){
			return;
		}
		for(int i = 0; i < TOTAL;i++){
			final int pieceNumber = (i/4) +1;
			if(!arrayContains(remainsAlreadyCalculated,pieceNumber)){
				final Node node = new Node(i,true,superNode,evaluator);
				superNode.addNode(node);
				final int[] newRemainders = addIn(pieceNumber, remainsAlreadyCalculated);
				moveNodes(node, newRemainders);
			}
		}
	}

	private boolean allRemaindersCalculated(final int[] remainsAlreadyCalculated) {
		for (int i = 0; i < remainsAlreadyCalculated.length; i++) {
			if(remainsAlreadyCalculated[i]==0){
				return false;
			}
		};
		return true;
	}

	private int[] addIn(final int pieceNumber, final int[] remainsAlreadyCalculated) {
		for (int i = 0; i < remainsAlreadyCalculated.length; i++) {
			if(remainsAlreadyCalculated[i]==0){
				final int[] clone = remainsAlreadyCalculated.clone();
				clone[i] = pieceNumber;
				return clone;
			}
		}
		throw new RuntimeException("Did you messed with some constants?",new Throwable());
	}

	private boolean arrayContains(final int[] remainsAlreadyCalculated, final int pieceNumber) {
		for (int i = 0; i < remainsAlreadyCalculated.length; i++) {
			if(remainsAlreadyCalculated[i] == pieceNumber ){
				return true;
			}
		};
		return false;
	}
}