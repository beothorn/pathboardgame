package gameLogic;

import junit.framework.Assert;

import org.junit.Test;

public class PlaySequenceTests {
	
	@Test
	public void createAPlaySequenceFromAString(){
		final PlaySequence playSequence = new PlaySequence();
		playSequence.addPlay(new Play(1,1));
		playSequence.addPlay(new Play(Play.NEXT_STATE));
		final PlaySequence playSequenceWithString = new PlaySequence("(1,1)"+String.valueOf(PlaySequence.PLAYS_SEPARATOR) +Play.NEXT_STATE);
		Assert.assertEquals(playSequence, playSequenceWithString);
		playSequenceWithString.addPlays(Play.MOVE+"2"+Play.UP+String.valueOf(PlaySequence.PLAYS_SEPARATOR)+"(2,4)");
		playSequence.addPlay(new Play(2,Play.UP));
		playSequence.addPlay(new Play(2,4));
		Assert.assertEquals(playSequence, playSequenceWithString);
	}

}
