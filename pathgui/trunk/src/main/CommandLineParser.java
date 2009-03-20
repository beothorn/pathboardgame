package main;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandLineParser {

	private Map<String, Boolean> booleanArguments;
	private Map<String, String> valueArguments;
	private Map<String, String> argumentDefinition;

	public final static String HELP = "help" ;
	public final static String TOPAI = "topai" ;
	public final static String BOTTOMAI = "bottomai" ;
	public final static String LOCALGAME = "localgame" ;

	public CommandLineParser() {
		reset();
	}

	private void addNewBooleanArgument(final String argument,final String argumentDesc) {
		booleanArguments.put(argument, false);
		argumentDefinition.put(argument, argumentDesc);
	}

	public boolean isSetted(final String argument){
		if(booleanArguments.containsKey(argument)){
			return booleanArguments.get(argument);
		}
		throw new IllegalArgumentException("unknown argument: "+argument);
	}

	public void parse(final String[] args){
		reset();
		if(args.length == 0) {
			return;
		}
		int count = 0;
		do{
			final String argument = args[count].replace('-', ' ').trim().toLowerCase();

			if(booleanArguments.containsKey(argument)){
				booleanArguments.put(argument, true);
			}else if(valueArguments.containsKey(argument)){
				count++;
				final String value = args[count];
				valueArguments.put(argument, value);
			}else{
				showHelp();
				throw new IllegalArgumentException("unknown argument: "+argument);
			}
			count++;
		}while(count<args.length);
	}

	private void reset() {
		booleanArguments = new LinkedHashMap<String, Boolean>();
		valueArguments = new LinkedHashMap<String, String>();
		argumentDefinition = new LinkedHashMap<String, String>();

		addNewBooleanArgument(HELP,"Show this help");
		addNewBooleanArgument(LOCALGAME,"Set a game with two humans in the same machine");
		addNewBooleanArgument(TOPAI, "Set top player as ai, if no AI name is set for top player, it will use defaul ai");
		addNewBooleanArgument(BOTTOMAI, "Set bottom player as ai, if no AI name is set for bottom player, it will use defaul ai");
	}

	public void showHelp(){
		System.out.println("Valid arguments:");
		for (final String key : booleanArguments.keySet()) {
			System.out.println(key+" :"+argumentDefinition.get(key));
		}
		for (final String key : valueArguments.keySet()) {
			System.out.println(key+" :"+argumentDefinition.get(key));
		}
	}
}
