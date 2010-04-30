package main;
import java.io.IOException;
import java.net.UnknownHostException;

import utils.Printer;


public class Main{

	//TODO: escrever help e colocar botão no jogo :)
	//TODO: listar ias para escolher

	private static MainGameFrame frame;
	private static boolean isTopAi;
	private static boolean isBottomAi;

	private static void defaultOptions() {
		isTopAi = true;
		isBottomAi = false;
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(final String[] args){
		String commandLine = "";
		for (final String string : args) {
			commandLine += string;
		}
		Printer.debug("Arguments: " + commandLine);

		final CommandLineParser commandLineParser = new CommandLineParser();
		commandLineParser.parse(args);

		if(args.length == 0) {
			defaultOptions();
		}else{
			if(commandLineParser.isSetted(CommandLineParser.HELP)){
				commandLineParser.showHelp();
			}else{
				processArguments(commandLineParser);
			}
		}
		frame = new MainGameFrame(isTopAi,isBottomAi);
		frame.setVisible(true);
	}


	private static void processArguments(final CommandLineParser commandLineParser) {
		if(commandLineParser.isSetted(CommandLineParser.TOPAI)){
			isTopAi = true;
		}else{
			isTopAi = false;
		}
		if(commandLineParser.isSetted(CommandLineParser.BOTTOMAI)){
			isBottomAi = true;
		}else{
			isBottomAi = false;
		}
		if(commandLineParser.isSetted(CommandLineParser.LOCALGAME)){
			isTopAi = false;
			isBottomAi = false;
		}
	}
}
