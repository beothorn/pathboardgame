package main;
import java.io.IOException;
import java.net.UnknownHostException;

import utils.Printer;


public class Main{

	//TODO: depois q termina um jogo ai ainda tenta jogar
	//TODO: clicando bastante dá pra atrapalhar ai
	//TODO: escrever help e colocar botão no jogo :)
	//TODO: deixar Maingamepanel e game entities decente
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
		//		BasicConfigurator.configure(); if i ever use log4j again
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
