package main;
import java.io.IOException;
import java.net.UnknownHostException;

import playerTypes.PlayerTypes;
import utils.Logger;


public class Main{

	//TODO: depois q termina um jogo ai ainda tenta jogar
	//TODO: clicando bastante dá pra atrapalhar ai
	//TODO: escrever help e colocar botão no jogo :)
	//TODO: deixar Maingamepanel e game entities decente
	//TODO: listar ias para escolher

	private static MainGameFrame frame;

	private static final Logger logger = Logger.getLogger(Main.class);


	private static void defaultOptions() {
		frame.setTopPlayerType(PlayerTypes.AI);
		frame.setBottomPlayerType(PlayerTypes.HUMAN);
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(final String[] args){
		//		BasicConfigurator.configure(); if i ever use log4j again
		String commandLine = "";
		for (final String string : args) {
			commandLine += string;
		}
		logger.debug("Arguments: " + commandLine);

		frame = new MainGameFrame();
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
		frame.setVisible(true);
	}


	private static void processArguments(final CommandLineParser commandLineParser) {
		if(commandLineParser.isSetted(CommandLineParser.TOPAI)){
			frame.setTopPlayerType(PlayerTypes.AI);
		}else{
			frame.setTopPlayerType(PlayerTypes.HUMAN);
		}
		if(commandLineParser.isSetted(CommandLineParser.BOTTOMAI)){
			frame.setBottomPlayerType(PlayerTypes.AI);
		}else{
			frame.setBottomPlayerType(PlayerTypes.HUMAN);
		}
		if(commandLineParser.isSetted(CommandLineParser.LOCALGAME)){
			frame.setBottomPlayerType(PlayerTypes.HUMAN);
			frame.setTopPlayerType(PlayerTypes.HUMAN);
		}
	}
}
