package gameEngine.gameMath;

public class Eval {
	private static double mathPrecision = 0.0000001;

	public static boolean equals(final double a, final double b){
		return equals(a, b, mathPrecision);
	}

	public static boolean equals(final double a, final double b,final double precision){
		return Math.abs(a-b) < precision;
	}

	public static double getPrecision() {
		return mathPrecision;
	}

	public static boolean greaterOrEqualsTo(final double a, final double b){
		return greaterOrEqualsTo(a,b,mathPrecision);
	}

	public static boolean greaterOrEqualsTo(final double a, final double b, final double precision){
		if(equals(a, b, precision)) {
			return true;
		}
		return a>b;
	}

	public static boolean greaterThan(final double a, final double b){
		return greaterThan(a,b,mathPrecision);
	}

	public static boolean greaterThan(final double a, final double b,final double precision){
		if(equals(a, b, precision)) {
			return false;
		}
		return a>b;
	}

	public static boolean lessOrEqualsTo(final double a, final double b){
		return lessOrEqualsTo(a, b,mathPrecision);

	}

	public static boolean lessOrEqualsTo(final double a, final double b,final double precision){
		if(equals(a, b, precision)) {
			return true;
		}
		return a<b;
	}

	public static boolean lessThan(final double a, final double b){
		return lessThan(a,b,mathPrecision);
	}

	public static boolean lessThan(final double a, final double b, final double precision){
		if(equals(a, b,precision)) {
			return false;
		}
		return a<b;
	}

	public static void setPrecision(final double precision) {
		mathPrecision = precision;
	}
}
