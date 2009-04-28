package externalPlayer;

import gameLogic.Game;
import gameLogic.PlaySequence;
import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import utils.BoardUtils;
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
				Board boardCopy = game.getBoard().copy();
				final boolean isTopPlayerTurn = game.isTopPlayerTurn();
				if(isTopPlayerTurn){
					BoardUtils.switchSides(boardCopy);
				}
				logger.debug("Quering External player play.");
				logger.debug("\n" + boardCopy);
				final String aiPlay = player.play(boardCopy.toString());
				final PlaySequence playSequence = new PlaySequence(aiPlay);
				final PlaySequence aiPlayFixed = isTopPlayerTurn ? BoardUtils.invertPlay(playSequence) : playSequence;
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
//				aiErrorPlayingWhenIsNotAITurn(play);
			}
			ValidPlay validPlay;
			try {
				validPlay = game.validatePlay(play);
				game.play(validPlay);
			} catch (InvalidPlayException e) {
				new RuntimeException("Oh noes!!!");
			}
		}
	}
}
