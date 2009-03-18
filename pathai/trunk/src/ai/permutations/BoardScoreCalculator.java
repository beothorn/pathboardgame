package ai.permutations;

public interface BoardScoreCalculator {
	public int getScoreForBoard(final String board);
	public int scoreGoodEnoughToPlay();
}
