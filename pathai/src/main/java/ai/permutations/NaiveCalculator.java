package ai.permutations;

import gameLogic.board.Board;

public class NaiveCalculator implements BoardScoreCalculator{

	private static int WIN_BONUS_SCORE = 99999;
	
	private int calculateScore(final Board board){
		int finalScore = 0;

		for (int col = 0; col < Board.BOARD_SIZE ; col++) {
			int countWeaks = 0;
			int countNotWeaks = 0;
			boolean isThereAnyStrong = false;
			boolean isYetAWeaksSequence = true;
			boolean isContinuous = true;
			final int start = Board.BOARD_SIZE - 1;
			final int end = -1;
			
			int line = start;
			while(line != end){
				final boolean isMyWeakPiece = board.getPieceAt(line, col).isBottomPlayerWeakPiece();
				if(isMyWeakPiece){
					countWeaks ++;
					if(!isYetAWeaksSequence) {
						isContinuous = false;
					}
				} else {
					countNotWeaks ++;
					isYetAWeaksSequence = false;
				}
				if(board.getPieceAt(line, col).isStrong()) {
					isThereAnyStrong = true;
				}
				line--;
			}

			if(countWeaks >= Board.BOARD_SIZE ){
				return WIN_BONUS_SCORE;
			}
			
			finalScore += (isContinuous)?countWeaks*countWeaks:countWeaks;
			finalScore += (!isThereAnyStrong)?countWeaks*countWeaks:countWeaks;
		}

		return finalScore;
	}
	
	@Override
	public String toString() {
		return "Silly";
	}

	@Override
	public int getScoreForBoard(final Board board) {
		return calculateScore(board);
	}

	@Override
	public int scoreGoodEnoughToPlay() {
		return WIN_BONUS_SCORE;
	}
}
