package externalPlayer;

import gameLogic.Game;
import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.PlaySequence;
import gameLogic.board.PlaySequenceValidator;
import gameLogic.board.ValidPlaySequence;
import utils.BoardUtils;
import utils.Logger;

public class ExternalPlayerRunnable implements Runnable {

	private final Game game;
	private boolean stopPlaying = false;
	private final boolean stopPlayingOnGameEnd;
	private final boolean isTopPlayer;
	private final PlaySequenceValidator playSequenceValidator;
	private final PathAI player;

	private static Logger logger = Logger.getLogger(ExternalPlayerRunnable.class);

	public ExternalPlayerRunnable(final Game game, final PathAI player,	final boolean isTopPlayer) {
		this(game,player,isTopPlayer,false);
	}
	
	public ExternalPlayerRunnable(final Game game, final PathAI player,final boolean isTopPlayer, final boolean stopPlayingOnGameEnd) {
		this.game = game;
		this.player = player;
		this.isTopPlayer = isTopPlayer;
		playSequenceValidator = new PlaySequenceValidator(game);
		this.stopPlayingOnGameEnd = stopPlayingOnGameEnd;
	}

	public void stopPlaying() {
		stopPlaying = true;
	}

	@Override
	public void run() {
		while (!stopPlaying) {
			if(game.isGameEnded() && stopPlayingOnGameEnd){
				return;
			}
			if(isBrainTurn()){
				lockGame();
				final boolean isTopPlayerTurn = game.isTopPlayerTurn();
				final Board board;
				if(isTopPlayerTurn){
					board = BoardUtils.newBoardSwitchedSides(game.getBoard());
				}else{
					board = game.getBoard();
				}
				
				logger.debug("Quering External player play.");
				final String aiPlay = player.play(BoardUtils.printBoard(board));
				
				final PlaySequence playSequence;
				try {
					playSequence = new PlaySequence(aiPlay);
				} catch (InvalidPlayStringException e) {
					aiError(e);
					return;
				}
				final PlaySequence playSequenceForGame = isTopPlayerTurn ? BoardUtils.invertPlay(playSequence) : playSequence;
				
				final ValidPlaySequence validPlaySequence;
				try {
					validPlaySequence = playSequenceValidator.validatePlays(playSequenceForGame,isTopPlayerTurn);
				}catch (InvalidPlayException e) {
					aiError(e);
					return;
				}
				logger.debug("External player played");
				logger.debug(aiPlay);
				playSequenceValidator.play(validPlaySequence);
				unLockGame();
			}
			if(isTopPlayer){
				game.waitTopTurnOrGameEnd();
			}else{
				game.waitBottomTurnOrGameEnd();
			}
		}
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
		return (game.isTopPlayerTurn() && isTopPlayer) || (game.isBottomPlayerTurn() && !isTopPlayer) ;
	}
}
