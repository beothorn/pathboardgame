package main;

import gameLogic.Game;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.PlaySequence;
import gameLogic.board.PlaySequenceValidator;
import gameLogic.board.ValidPlaySequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import utils.BoardUtils;

public class Main {
	
	private final Game game;
	private final PlaySequenceValidator playSequenceValidator;

	public Main() {
		game = new Game();
		playSequenceValidator = new PlaySequenceValidator(game);
		printGameState();
	}

	public boolean isGameEnded(){
		return game.isGameEnded();
	}
	
	public void play(final String line){
		final PlaySequence playSequence;
		try {
			playSequence = new PlaySequence(line);
		} catch (InvalidPlayStringException i) {
			System.out.println(i.getMessage());
			return;
		}
		final ValidPlaySequence validPlay;
		try{
			validPlay = playSequenceValidator.validatePlays(playSequence,game.isTopPlayerTurn());
		}catch(InvalidPlayException i){
			System.out.println(i.getMessage());
			return;
		}
		playSequenceValidator.play(validPlay);
		if(game.stateChanged()){
			printGameState();
		}
		
	}

	private void printGameState() {
		System.out.println(game.getStateDescription());
	}
	
	public static void main(String[] args) throws IOException {
		
		final Main main = new Main();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		while (!main.isGameEnded() && line != null && !line.equals("q")){
			main.printBoard();
			line = reader.readLine();
			if(line != null)
				main.play(line);
		}
		main.printBoard();
		if(line == null || line.equals("q"))
			System.out.println("Quitted game");
	}

	public void printBoard() {
		String printBoardWithCoordinates = BoardUtils.printBoardWithCoordinates(game);
		Set<Integer> alreadyMovedPieces = game.getAlreadyMovedPieces();
		for (Integer id : alreadyMovedPieces) {
			String player = (game.isTopPlayerTurn())?"T":"B";
			printBoardWithCoordinates = printBoardWithCoordinates.replace(player+"S"+id, player+"XX");
		}
		System.out.println(printBoardWithCoordinates);
	}
}
