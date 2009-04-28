package main;

import gameLogic.Game;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import utils.BoardUtils;

public class Main {
	
	public static void main(String[] args) throws IOException {
		final Game game = new Game();
		
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String line = "";
		while (!game.isGameEnded() && !line.equals("q")){
			printBoard(game);
			line = reader.readLine();
			Play play;
			try {
				play = new Play(line);
			} catch (InvalidPlayStringException i) {
				System.out.println(i.getMessage());
				continue;
			}
			boolean isValidPlay;
			ValidPlay validPlay = null;
			isValidPlay = true;
			try{
				validPlay = game.validatePlay(play);
			}catch(InvalidPlayException i){
				isValidPlay = false;
				System.out.println(i.getMessage());
			}
			if(isValidPlay){
				game.play(validPlay);
			}
			if(game.stateChanged()){
				System.out.println(game.getStateDescription());
			}
		}
		
		if(game.isBottomTheWinner()){
			System.out.println("Bottom player wins");
		}
		if(game.isTopTheWinner()){
			System.out.println("Top player wins");
		}
		if(game.isGameDraw()){
			System.out.println("Game Draw");
		}
		printBoard(game);
	}

	private static void printBoard(final Game game) {
		String printBoardWithCoordinates = BoardUtils.printBoardWithCoordinates(game.getBoard());
		Set<Integer> alreadyMovedPieces = game.getAlreadyMovedPieces();
		for (Integer id : alreadyMovedPieces) {
			String player = (game.isTopPlayerTurn())?"T":"B";
			printBoardWithCoordinates = printBoardWithCoordinates.replace(player+"S"+id, player+"XX");
		}
		System.out.println(printBoardWithCoordinates);
	}
}
