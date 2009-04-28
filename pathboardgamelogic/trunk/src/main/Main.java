package main;

import gameLogic.Game;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utils.BoardUtils;

public class Main {
	
	public static void main(String[] args) throws IOException {
		final Game game = new Game();
		
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (!game.isGameEnded()){
			System.out.println(BoardUtils.printBoardWithCoordinates(game.getBoard()));
			String line;
			line = reader.readLine();
			Play play = new Play(line);
			boolean isValidPlay;
			ValidPlay validPlay = null;
			isValidPlay = true;
			try{
				validPlay = game.validatePlay(play);
			}catch(InvalidPlayException i){
				isValidPlay = false;
				System.out.println(i.getMessage());
			}
			if(isValidPlay)
				game.play(validPlay);
			if(game.stateChanged())
				System.out.println(game.getStateDescription());
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
		System.out.println(BoardUtils.printBoardWithCoordinates(game.getBoard()));
	}
}
