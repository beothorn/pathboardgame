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

import utils.GameUtils;

public class MainCommandLine {
	
	private static final String VERSION = "0.2.010709";
	protected final Game game;
	private final PlaySequenceValidator playSequenceValidator;
	private final GameUtils gameUtils = new GameUtils();

	public MainCommandLine(final String[] args) throws IOException {
		game = new Game();
		addAIPlayers();
		playSequenceValidator = new PlaySequenceValidator(game);
		if(args.length > 0){
			if(isArgument(args[0],"help")){
				printHelp();
				return;
			}
			if(isArgument(args[0],"version")){
				System.out.println(VERSION);
				return;
			}
		}
		printGameState();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		while (!isGameEnded() && line != null && !line.equals("q")){
			printBoard();
			line = reader.readLine();
			if(line != null)
				play(line);
			if(line.equals("help"))
				printHelp();
			if(line.equals("version"))
				System.out.println(VERSION);
		}
		printBoard();
		if(line == null || line.equals("q"))
			System.out.println("Quitted game");
	}

	protected void addAIPlayers() {
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
		gameUtils.printStateDescription(game);
	}
	
	public static void main(String[] args) throws IOException {
		new MainCommandLine(args);		
	}

	private static  boolean isArgument(final String arg,final String argValid) {
		return arg.toLowerCase().equals("-"+argValid.toLowerCase()) || 
		arg.toLowerCase().equals("-"+argValid.toLowerCase().charAt(0)) || 
		arg.toLowerCase().equals(argValid.toLowerCase()) || 
		arg.equals(Character.toString(argValid.toLowerCase().charAt(0)));
	}

	private static void printHelp() {
		System.out.println(
			"Goal of the game\r\n" + 
			"\r\n" + 
			"    The main goal of the game is to make a path of pieces leading to the adversary side.                                         \r\n" +
			"A piece forms a path with every piece in a near square horizontally or vertically.                                               \r\n" + 
			"\r\n" + 
			"Pieces\r\n" + 
			"\r\n" + 
			"    The players have two kinds of pieces, the strongs and the weaks.                                                             \r\n" +
			"The weak pieces are atracted to its owner player side (like a tetris piece).                                                     \r\n" +
			" They can push others weak pieces but are blocked by strong pieces.                                                              \r\n" +
			"Weak pieces can be pushed out of the board in the vertical direction.                                                            \r\n" +
			"If a weak piece is pushed out of the board horizontally it is teleported to the other side of the line.                          \r\n" +
			"Strong pieces aren't atracted. They can be at any position of the board.                                                         \r\n" +
			"The strong pieces can push any number of weak pieces and at least one strong piece.                                              \r\n" +
			"Strong pieces are blocked from moving if there's another two or more strong pieces in the same line that it it's trying to push. \r\n" +
			"(a line is a sequence of pieces horizontally or vertically connected, without any empty space between)                           \r\n" + 
			"\r\n" + 
			"The game\r\n" + 
			"\r\n" + 
			"    All new pieces enter the game in the first line relative to the player who is putting the piece.                             \r\n" +
			"In the first round each player puts 3 strong pieces on board.                                                                    \r\n" +
			"You can't put a strong piece in an already occupied square.                                                                      \r\n" +
			"After this, all plays follow the same sequence.                                                                                  \r\n" +
			"The player put 3 weak pieces at the first line and move each strong one square in the horizontal or vertical direction.          \r\n" +
			"The sequence is always this: put the weaks, move the strong.                                                                     \r\n" +
			"The player can skip any time, except when he is putting the srongs on the board.                                                 \r\n" +
			"If a player skip the putting weaks phase he goes to the moving strongs phase.                                                    \r\n" +
			"The game goes on until someone makes a path of weak pieces to the adversary side.                                                \r\n" + 
			"\r\n" + 
			"Other rules\r\n" + 
			"\r\n" + 
			"    When a piece is pushed in the horizontal direction out of the board,                                                         \r\n" +
			"it is moved to the first square on the other side of the line, this play is called a teleport.                                   \r\n" +
			"The teleport can only bem made when a piece pushes another out of the board. A piece can't teleport itself.                      \r\n" +
			"Strong pieces cannot be pushed outside of the board. There will always be six strong pieces on the board.                        \r\n" +
			"To win the path must have only weak pieces. If the first square of the line is blocked, the piece will not be teleported.        \r\n" +
			"\r\n" +
			"Board\r\n" +
			"BS1 : Bottom strong ID1                                                                                                          \r\n" +
			"BWK : Bottom weak                                                                                                                \r\n" +
			"TS2 : Top strong ID2                                                                                                             \r\n" +
			"TWK : Top weak                                                                                                                   \r\n" +
			"TXX : Already moved top strong                                                                                                   \r\n" +
			"- : Empty space                                                                                                                  \r\n" +
			"Commands\r\n" +
			" To add a piece just type the columns where you want to add the pieces using spaces between them. ex: 0 3 4                      \r\n" +
			" To move the strongs type the piece id and the direction u for up, d for down, l for left and r for right. ex: 1u 2d 3r          \r\n" +
			" To pass your turn type p ex: p ex: 1 2 p (added two pieces them pass to move strongs turn)                                      \r\n" +
			" To quit type q");
	}

	public void printBoard() {
		System.out.println(GameUtils.printBoardWithCoordinates(game));
	}
}
