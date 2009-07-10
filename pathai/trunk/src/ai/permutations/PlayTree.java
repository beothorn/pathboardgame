                                              package ai.permutations;

import gameLogic.board.Board;
import gameLogic.board.PlaySequence;
import gameLogic.gameFlow.gameStates.GameState;
import utils.Printer;

public class PlayTree {

	private static final int DIRECTIONS = 4;
	private static final int TOTAL_MOVE_POSSIBILITIES = DIRECTIONS*GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE;

	private final Node playTree;
	private final PlayEvaluator evaluator;
	
	public PlayTree(final BoardScoreCalculator calculator) {
		this.evaluator = new PlayEvaluator(calculator);
		playTree = new Node(-1,false,evaluator);
		Printer.debug("Calculating tree...");
		addNodesNodesAndThenAddMoveNodes(playTree, 0, 0);
		Printer.debug("Tree calculated");
	}
	
	public PlaySequence bestPlayFor(final Board board){
		evaluator.reset();
		playTree.setBoard(board);
		playTree.sendAllPlaysToEvaluator();
		return evaluator.getBestPlay();
	}
	
	private void addNodesNodesAndThenAddMoveNodes(final Node superNode,final int start,final int level){
		if(level == GameState.NUMBER_OF_WEAK_PIECES_TO_PUT){
			moveNodes(superNode, new int[]{0,0,0});
			return;
		}
		for (int i = start; i < Board.BOARD_SIZE; i++) {
			final Node node = new Node(i,false,evaluator);
			superNode.addNode(node);
			addNodesNodesAndThenAddMoveNodes(node, i, level+1);
		}
	}
	
	/***
	 * 
	 * @param superNode
	 * @param strongPiecesAlreadyCalculated 0 0 0 mean no strong already calculated. I pass the ones
	 * already processed because i want all permutations (ex: 123 321 ... ) but without repetitions (ex: 332 223 ).
	 * So i do all move possibilities skipping the ones already done. The remainer is the piece id and the mod is the piece direction. 
	 */
	private void moveNodes(final Node superNode, final int[] strongPiecesAlreadyCalculated){
		if(allStrongPiecesCalculated(strongPiecesAlreadyCalculated)){
			return;
		}
		for(int pieceAndDirectionBundle = 0; pieceAndDirectionBundle < TOTAL_MOVE_POSSIBILITIES;pieceAndDirectionBundle++){
			final int strongPieceNumber = (pieceAndDirectionBundle/4) +1;
			if(!arrayContains(strongPiecesAlreadyCalculated,strongPieceNumber)){
				final Node node = new Node(pieceAndDirectionBundle,true,evaluator);
				superNode.addNode(node);
				final int[] newStrongPiecesAlreadyCalculated = addIn(strongPieceNumber, strongPiecesAlreadyCalculated);
				moveNodes(node, newStrongPiecesAlreadyCalculated);
			}
		}
	}

	private boolean allStrongPiecesCalculated(final int[] remainsAlreadyCalculated) {
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