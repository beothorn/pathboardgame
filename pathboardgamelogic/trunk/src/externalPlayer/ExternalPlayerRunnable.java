package externalPlayer;

import gameLogic.Board;
import gameLogic.Game;
import gameLogic.Play;
import gameLogic.PlaySequence;
import gameLogic.gameFlow.PlayResult;
import utils.Logger;

public class ExternalPlayerRunnable implements Runnable {

	private final Game game;
	private boolean stopPlaying = false;
	private final boolean stopPlayingOnGameEnd;
	private final boolean isTopPlayer;
	private final PathAI player;

	private static Logger logger = Logger
			.getLogger(ExternalPlayerRunnable.class);

	public ExternalPlayerRunnable(final Game game, final PathAI player,
			final boolean isTopPlayer) {
		this(game,player,isTopPlayer,false);
	}
	
	public ExternalPlayerRunnable(final Game game, final PathAI player,
			final boolean isTopPlayer, final boolean stopPlayingOnGameEnd) {
		this.game = game;
		this.player = player;
		this.isTopPlayer = isTopPlayer;
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
				game.setLocked(true);
				final String board = game.getBoard().toString();
				final Board boardCopy = new Board(board);
				final boolean isTopPlayerTurn = game.isTopPlayerTurn();
				if(isTopPlayerTurn){
					boardCopy.switchSides();
				}
				logger.debug("Quering External player play.");
				logger.debug("\n" + boardCopy);
				final String aiPlay = player.play(boardCopy.toString());
				final PlaySequence playSequence = new PlaySequence(aiPlay);
				final PlaySequence aiPlayFixed = isTopPlayerTurn ? Board.invertPlay(playSequence) : playSequence;
				logger.debug("External player played");
				logger.debug(aiPlayFixed);
				play(aiPlayFixed);
				game.setLocked(false);
			}
			if(isTopPlayer){
				game.waitTopTurnOrGameEnd();
			}else{
				game.waitBottomTurnOrGameEnd();
			}
		}
	}


	private boolean isBrainTurn() {
		return (game.isTopPlayerTurn() && isTopPlayer) || (game.isBottomPlayerTurn()
				&& !isTopPlayer) ;
	}
	
	private void play(final PlaySequence playSequence) {
		for (final Play play : playSequence.getPlays()) {
			if (!isBrainTurn()) {
				aiErrorPlayingWhenIsNotAITurn(play);
			}
			final PlayResult forcePlayResult = game.forcePlay(play);
			if (!forcePlayResult.isSuccessful()) {
				externalPlayerErrorInvalidPlay(play,forcePlayResult);
			}
		}
	}

	private void externalPlayerErrorInvalidPlay(final Play play,
			final PlayResult forcePlayResult) {
		logger.error("External player played WRONG,if external is ai, something must be wrong with ai engine"
						+ "\nwrong play: "
						+ play
						+ " message: "
						+ forcePlayResult.getErrorMessage()
						+ " state: "
						+ game.getCurrentState().asStateUniqueName());
	}

	private void aiErrorPlayingWhenIsNotAITurn(final Play play) {
		logger.error("External player tried to in other player turn"
				+ "\nwrong play: " + play + " state: "
				+ game.getCurrentState().asStateUniqueName());
	}
}
