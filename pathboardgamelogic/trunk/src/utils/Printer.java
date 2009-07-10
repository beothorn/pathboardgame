package utils;



public class Printer {

	private static boolean DEBUG = true;
	private static boolean INFO = true;
	private static boolean ERROR = true;

	public static void debug(final Object message) {
		if(DEBUG)
			System.out.println("[DEBUG]"+System.currentTimeMillis()+": "+message);
	}

	public static void writeln(final String message) {
		if(INFO)
			System.out.println(message);
	}

	public static void error(final String message) {
		if(ERROR)
			System.out.println("[ERROR]"+System.currentTimeMillis()+": "+message);
	}

}
