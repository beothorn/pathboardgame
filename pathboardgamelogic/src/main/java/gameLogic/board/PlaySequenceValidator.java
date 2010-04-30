package gameLogic.board;

import gameLogic.Game;

import java.util.List;

public class PlaySequenceValidator {

	final Game game;
	
	public PlaySequenceValidator(final Game game) {
		this.game = game;
	}
	
	public ValidPlaySequence validatePlays(final PlaySequence playSequence,final boolean isTopPlayerPlay) throws InvalidPlayException{
		final ValidPlaySequence validPlaySequence = new ValidPlaySequence();
		final Game gameCopy = game.copy();
		List<Play> plays = playSequence.getPlays();
		for (int i = 0; i < plays.size(); i++) {
			final Play play = plays.get(i);
			final ValidPlay validPlay = gameCopy.validatePlay(play, isTopPlayerPlay);
			validPlaySequence.addValidPlay(validPlay);
			gameCopy.play(validPlay);
		}
		return validPlaySequence;
	}

	public void play(ValidPlaySequence validPlaySequence) {
		for (ValidPlay validPlay : validPlaySequence.validPlays()) {
			game.play(validPlay);
		}
	}

	public ValidPlaySequence validatePlays(final String plays,final boolean isTopPlayerPlay) throws InvalidPlayException, InvalidPlayStringException {
		return validatePlays(new PlaySequence(plays),isTopPlayerPlay);
	}
}
