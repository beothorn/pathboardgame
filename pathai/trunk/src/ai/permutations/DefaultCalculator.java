package ai.permutations;

import gameLogic.board.Board;

import java.awt.Point;

public class DefaultCalculator implements BoardScoreCalculator {
	
	private static final int CONTINUOUS_BONUS = 3;
	private static final int DISTANCE_BONUS = 3;
	private static final int WIN_BONUS_SCORE = 99999;
	private static final int TOO_CLOSE_TO_LOSE_BONUS_SCORE = 800;
	private static final int TOO_CLOSE_TO_WIN_BONUS_SCORE = 100;
	
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
	
	//TODO: redo this (does this belong to board?) 
	private int distanceFromColumnToStrong(final boolean isDistancefromTopStrong ,final Board board, final int col){
		final Point strong1 = board.getStrongPiecePosition(1, !isDistancefromTopStrong);
		final Point strong2 = board.getStrongPiecePosition(2, !isDistancefromTopStrong);
		final Point strong3 = board.getStrongPiecePosition(3, !isDistancefromTopStrong);
		int d1 = Math.abs(col-strong1.x);
		int d2 = Math.abs(col-strong2.x);
		int d3 = Math.abs(col-strong3.x);
		final int teleportPosition = Board.BOARD_SIZE-1;
		final boolean isMyPlay = isDistancefromTopStrong;
		/**
		 * teleport rule only for the enemy because i'm lazy to verify if enemy or self can teleport a strong (it's too complex)
		 * so i calculate a preventive teleport value
		 */
		d1 = d1>=teleportPosition && isMyPlay?1:d1;
		d2 = d2>=teleportPosition && isMyPlay?1:d2;
		d3 = d3>=teleportPosition && isMyPlay?1:d3;

		int distance = d1;
		distance = d2 < distance? d2 : distance;
		distance = d3 < distance? d3 : distance;
		return distance;
	}
	
	@Override
	public int getScoreForBoard(final Board board) {
		final Board gameBoard = board;
		return calculateScore(gameBoard,false) - calculateScore(gameBoard,true);
	}

	@Override
	public int scoreGoodEnoughToPlay() {
		return WIN_BONUS_SCORE;
	}
}