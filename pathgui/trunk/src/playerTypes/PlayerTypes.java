package playerTypes;

public class PlayerTypes {

	public static final int AI_EASY = 2;
	public static final int AI_HARD = 4;
	public static final int AI_EASIEST = 0;
	public static final int AI_MEDIUM = 3;
	public static final int AI_VERY_EASY = 1;
	public static final int AI_VERY_HARD = 5;
	public static final Integer HUMAN = 6;
	public static final Integer NETWORK = 7;

	public static boolean isAiType(final int type) {
		return type == AI_EASIEST || type == AI_VERY_EASY
		|| type == AI_EASY || type == AI_MEDIUM
		|| type == AI_HARD || type == AI_VERY_HARD;
	}
}
