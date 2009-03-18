package gameLogic;

import junit.framework.Assert;

import org.junit.Test;

import utils.Logger;

public class PlayTests {

	private final Logger logger = Logger.getLogger(PlayTests.class);

	@Test
	public void testPlayFromStringMoveById(){
		final Play playNexState = new Play(1, Play.DOWN);
		final Play playNexStateFromString = new Play("mov1d");
		logger.info(playNexState.toString()+" = "+ playNexStateFromString.toString());
		Assert.assertEquals(playNexState, playNexStateFromString);
	}

	@Test
	public void testPlayFromStringNewPlay(){
		final Play play57 = new Play(5,7);
		final Play play57String = new Play("(5,7)");
		logger.info(play57.toString()+" = "+ play57String.toString());
		Assert.assertEquals(play57, play57String);
	}

	@Test
	public void testPlayFromStringNextState(){
		final Play playNexState = new Play(Play.NEXT_STATE);
		final Play playNexStateFromString = new Play("PASS_PLAY");
		logger.info(playNexState.toString()+" = "+ playNexStateFromString.toString());
		Assert.assertEquals(playNexState, playNexStateFromString);
	}
}
