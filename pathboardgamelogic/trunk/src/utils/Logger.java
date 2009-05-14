package utils;



public class Logger {

	private static boolean DEBUG = true;
	private static boolean INFO = true;
	private static boolean ERROR = true;
	
	public static Logger getLogger(final Class<? extends Object> clazz) {
		return new Logger(clazz);
	}

	private final Class<? extends Object> clazz;

	private Logger(final Class<? extends Object> clazz) {
		this.clazz = clazz;
	}

	public void debug(final Object message) {
		if(DEBUG)
			System.out.println("[DEBUG]"+System.currentTimeMillis()+" "+clazz.getName()+": "+message);
	}

	public void info(final String message) {
		if(INFO)
			System.out.println("[INFO]"+System.currentTimeMillis()+" "+clazz.getName()+": "+message);
	}

	public void error(final String message) {
		if(ERROR)
			System.out.println("[ERROR]"+System.currentTimeMillis()+" "+clazz.getName()+": "+message);
	}

}
