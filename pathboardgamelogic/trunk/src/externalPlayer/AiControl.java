package externalPlayer;

import gameLogic.Game;
import gameLogic.TurnChangeListener;
import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.PlaySequence;
import gameLogic.board.PlaySequenceValidator;
import gameLogic.board.ValidPlaySequence;
import utils.BoardUtils;
import utils.Logger;

public class AiControl implements TurnChangeListener {

	private final Game game;
	private final boolean isTopPlayer;
	private final PlaySequenceValidator playSequenceValidator;
	private final PathAI player;

	private static Logger logger = Logger.getLogger(AiControl.class);

	public AiControl(final Game game, final PathAI player,	final boolean isTopPlayer) {
		this(game,player,isTopPlayer,false);
	}
	
	public AiControl(final Game game, final PathAI player,final boolean isTopPlayer, final boolean stopPlayingOnGameEnd) {
		this.game = game;
		this.player = player;
		this.isTopPlayer = isTopPlayer;
		playSequenceValidator = new PlaySequenceValidator(game);
		game.addTurnListener(this);
	}	
	
	private void aiError(Exception e) {
		logger.error("Ai error (Ai was turned off): "+e);
		unLockGame();
	}

	private void unLockGame() {
		if(isTopPlayer)
			game.setTopLocked(false);
		else
			game.setBottomLocked(false);
	}

	private void lockGame() {
		if(isTopPlayer)
			game.setTopLocked(true);
		else
			game.setBottomLocked(true);
	}


	private boolean isBrainTurn() {
		return (game.isTopPlayerTurn() && isTopPlayer) || (game.isBottomPlayerTurn() && !isTopPlayer) && !game.isGameEnded();
	}

	public void play() {
		if(isBrainTurn()){
			lockGame();
			final boolean isTopPlayerTurn = game.isTopPlayerTurn();
			final Board board;
			if(isTopPlayerTurn){
				board = BoardUtils.newBoardSwitchedSides(game.getBoard());
			}else{
				board = game.getBoard();
			}
			
			final String boardString = BoardUtils.printBoard(game.getBoard());
			logger.debug("Quering External player play for board:\n"+boardString);
			final String aiPlay = player.play(BoardUtils.printBoard(board));				
			final PlaySequence playSequence;
			try {
				playSequence = new PlaySequence(aiPlay);
			} catch (InvalidPlayStringException e) {
				aiError(e);
				return;
			}
			final PlaySequence playSequenceForGame = isTopPlayerTurn ? BoardUtils.invertPlay(playSequence) : playSequence;
			logger.debug("External player played: "+playSequenceForGame);
			
			final ValidPlaySequence validPlaySequence;
			try {
				validPlaySequence = playSequenceValidator.validatePlays(playSequenceForGame,isTopPlayerTurn);
			}catch (InvalidPlayException e) {
				aiError(e);
				return;
			}
			playSequenceValidator.play(validPlaySequence);
			unLockGame();
		}
	}

	@Override
	public void changedToBottomTurn() {
		play();
	}

	@Override
	public void changedToTopTurn() {
		play();
	}

	public void stopPlaying() {
		game.removeTurnListener(this);
	}
}
