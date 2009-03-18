package main;
import java.io.IOException;
import java.net.UnknownHostException;

import playerTypes.PlayerTypes;
import utils.Logger;


public class Main{

	//TODO: depois q termina um jogo ai ainda tenta jogar
	//TODO: clicando bastante dá pra atrapalhar ai
	//TODO: escrever help e colocar botão no jogo :)
	//TODO: bug Too much strong pieces in the game. What are you doing? qnd clica um monte na hora de colocar as fortes
	//TODO: deixar Maingamepanel e game entities decente, usar um layout e eliminar posiçoes absolutas do layout definitions

	private static String argAiTimeBetweenPlays = "-aiInterval";
	private static String argBottomIsAI = "-aiBottom";
	private static final String argTopIsAI = "-aiTop";
	private static final String argTwoPlayerLocalGame = "-localGame";
	private static MainGameFrame frame;

	private static final Logger logger = Logger.getLogger(Main.class);


	private static void defaultOptions() {
		frame.setTopPlayerType(PlayerTypes.AI_MEDIUM);
		start();
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

		if(args.length == 0) {
			defaultOptions();
		}else{
			int currentArg = 0;

			while(currentArg< args.length){
				final String arg = args[currentArg];
				if(arg.equals(argTwoPlayerLocalGame)){
					frame.setBottomPlayerType(PlayerTypes.HUMAN);
					frame.setTopPlayerType(PlayerTypes.HUMAN);
				}
				if(arg.equals(argTopIsAI)){
					currentArg++;
					try{
						final int topLevelAI = Integer.parseInt(args[currentArg]);
						if(PlayerTypes.isAiType(topLevelAI)) {
							frame.setTopPlayerType(topLevelAI);
						} else {
							throw new RuntimeException("Invalid Top AI Value");
						}
					}catch(final NumberFormatException nF){
						printCommandLineManAndExit();
					}
				}
				if(arg.equals(argBottomIsAI)){
					currentArg++;
					try{
						final int bottomLevelAI = Integer.parseInt(args[currentArg]);
						if(PlayerTypes.isAiType(bottomLevelAI)) {
							frame.setBottomPlayerType(bottomLevelAI);
						} else {
							throw new RuntimeException("Invalid Bottom AI Value");
						}
					}catch(final NumberFormatException nF){
						printCommandLineManAndExit();
					}
				}
				if(arg.equals(argAiTimeBetweenPlays)){
					currentArg++;
					try{
						//						final int interval = Integer.parseInt(args[currentArg]);
					}catch(final NumberFormatException nF){
						printCommandLineManAndExit();
					}
				}
				currentArg++;
			}
			start();
		}
	}

	private static void printCommandLineMan(){
		System.out.println("["+argTopIsAI+" 0-5]"+" ["+argBottomIsAI+"]"+" 0-5]"+" ["+argAiTimeBetweenPlays+" miliseconds]");
	}

	private static void printCommandLineManAndExit(){
		printCommandLineMan();
		System.exit(0);
	}

	private static void start() {
		frame.setVisible(true);
	}
}
