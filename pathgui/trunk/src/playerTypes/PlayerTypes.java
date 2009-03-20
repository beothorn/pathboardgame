package playerTypes;

public class PlayerTypes {

	public static final int AI = 1;
	public static final Integer HUMAN = 6;
	public static final Integer NETWORK = 7;

	public static boolean isAiType(final int type) {
		return type == AI;
	}
}
