package ai.permutations;

import gameLogic.Board;

import java.awt.Point;

public class DefaultCalculator implements BoardScoreCalculator {

//	private static Map<Integer, Integer> aiScoreToLevels(){
//	final Map<Integer, Integer> levels = new LinkedHashMap<Integer, Integer>();
//	levels.put(PlayerTypes.AI_EASIEST, -700);
//	levels.put(PlayerTypes.AI_VERY_EASY, -20);
//	levels.put(PlayerTypes.AI_EASY, 0);
//	levels.put(PlayerTypes.AI_MEDIUM, 20);
//	levels.put(PlayerTypes.AI_HARD, 100);
//	levels.put(PlayerTypes.AI_VERY_HARD, ScoreCalculator.winningPlayScore());
//	return levels;
//}
	
	private static int CONTINUOUS_BONUS = 3;
	private static int DISTANCE_BONUS = 3;

	private static int WIN_BONUS_SCORE = 99999;
	private static int TOO_CLOSE_TO_LOSE_BONUS_SCORE = 800;
	private static int TOO_CLOSE_TO_WIN_BONUS_SCORE = 100;
	
	private int calculateScore(final Board board,final boolean forTopPlayer){
		int finalScore = 0;

		for (int col = 0; col < Board.BOARD_SIZE ; col++) {
			int countWeaks = 0;
			boolean isThereAnyStrong = false;
			boolean isYetAWeaksSequence = true;
			boolean isContinuous = true;
			final int start = (forTopPlayer)?0:Board.BOARD_SIZE - 1;
			final int end = (forTopPlayer)?Board.BOARD_SIZE - 1:-1;
			
			int line = start;
			while(line != end){
				final boolean isMyWeakPiece = board.getPieceAt(line, col).isBottomPlayerWeakPiece() && !forTopPlayer || board.getPieceAt(line, col).isTopPlayerWeakPiece() && forTopPlayer ;
				if(isMyWeakPiece){
					countWeaks ++;
					if(!isYetAWeaksSequence) {
						isContinuous = false;
					}
				} else {
					isYetAWeaksSequence = false;
				}
				if(board.getPieceAt(line, col).isStrong()) {
					isThereAnyStrong = true;
				}
				if(forTopPlayer){
					line++;
				}else{
					line--;
				}
			}

			final int distance = distanceFromColumnToStrong(forTopPlayer, board, col);
			finalScore += rowScore(forTopPlayer, countWeaks, distance, isContinuous, isThereAnyStrong);
		}

		return finalScore;
	}
	
	private final int rowScore(final boolean isEnemy,final  int countWeaks,final int distance,final boolean continuous,final boolean anyStrong ){

		boolean tooCloseToWin1;
		boolean tooCloseToWin2;
		if(isEnemy){
			tooCloseToWin1 = countWeaks >=5 && distance >=1 && continuous && !anyStrong;
			tooCloseToWin2 = countWeaks >=3 && distance >=2 && continuous && !anyStrong;
		}else{
			tooCloseToWin1 = countWeaks >=5 && distance >=2 && continuous && !anyStrong;
			tooCloseToWin2 = countWeaks >=3 && distance >=3 && continuous && !anyStrong;
		}
		final boolean tooCloseToWin= tooCloseToWin1 || tooCloseToWin2;

		int result = countWeaks * (distance + DISTANCE_BONUS);
		if(continuous && countWeaks > 0) {
			result += CONTINUOUS_BONUS;
		}

		if(countWeaks >= Board.BOARD_SIZE ){
			return WIN_BONUS_SCORE + result;
		}
		
		if(tooCloseToWin) {
			if(isEnemy) {
				return TOO_CLOSE_TO_LOSE_BONUS_SCORE + result;
			}
			return TOO_CLOSE_TO_WIN_BONUS_SCORE + result;
		}

		return result;
	}
	
	private int distanceFromColumnToStrong(final boolean isDistancefromBottomStrong ,final Board board, final int col){
		final Point strong1 = isDistancefromBottomStrong?board.getStrongBottomByPositionInSequence(1):board.getStrongTopByPositionInSequence(1);
		final Point strong2 = isDistancefromBottomStrong?board.getStrongBottomByPositionInSequence(2):board.getStrongTopByPositionInSequence(2);
		final Point strong3 = isDistancefromBottomStrong?board.getStrongBottomByPositionInSequence(3):board.getStrongTopByPositionInSequence(3);
		int d1 = Math.abs(col-strong1.x);
		int d2 = Math.abs(col-strong2.x);
		int d3 = Math.abs(col-strong3.x);
		final int teleportPosition = Board.BOARD_SIZE-1;
		final boolean isEnemy = !isDistancefromBottomStrong;
		/**
		 * teleport rule only for the enemy because i'm lazy to verify if enemy or self can teleport a strong (it's too complex)
		 * so i calculate a preventive teleport value
		 */
		d1 = d1>=teleportPosition && isEnemy?1:d1;
		d2 = d2>=teleportPosition && isEnemy?1:d2;
		d3 = d3>=teleportPosition && isEnemy?1:d3;

		int distance = d1;
		distance = d2 < distance? d2 : distance;
		distance = d3 < distance? d3 : distance;
		return distance;
	}

	@Override
	public String toString() {
		return "Default";
	}
	
	@Override
	public int getScoreForBoard(final String board) {
		final Board gameBoard = new Board(board);
		return calculateScore(gameBoard,false) - calculateScore(gameBoard,true);
	}

	@Override
	public int scoreGoodEnoughToPlay() {
		return WIN_BONUS_SCORE;
	}
}