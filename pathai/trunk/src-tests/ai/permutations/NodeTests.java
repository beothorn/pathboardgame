package ai.permutations;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import utils.BoardUtils;

public class NodeTests {

	
	
	@Test
	public void testTreeNodes(){
		final ArrayList<String> expected = new ArrayList<String>();
		final DummyCalculators dummyCalculators = new DummyCalculators();
		final PlayEvaluator playEvaluator = new PlayEvaluator(dummyCalculators);
		final Node nRoot = new Node(-1,false,playEvaluator);
		final String initialBoard =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- BS1 BS2 BS3";
		nRoot.setBoard(BoardUtils.newBoardFromString(initialBoard));
		
		final Node n0 = new Node(0,false,playEvaluator);
		nRoot.addNode(n0);
		final String boardn0 =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- BS1 BS2 BS3";
		expected.add(boardn0);
		final Node n00 = new Node(0,false,playEvaluator);
		n0.addNode(n00);
		final String boardn00 =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- BS1 BS2 BS3";
		expected.add(boardn00);
		final Node n01 = new Node(1,false,playEvaluator);
		n0.addNode(n01);
		final String boardn01 =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"BWK BWK --- --- --- BS1 BS2 BS3";
		expected.add(boardn01);
		final Node n000 = new Node(0,false,playEvaluator);
		n00.addNode(n000);
		final String boardn000 =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- BS1 BS2 BS3";
		expected.add(boardn000);
		final Node n002 = new Node(2,false,playEvaluator);
		n00.addNode(n002);
		final String boardn002 =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"BWK --- --- --- --- --- --- ---\n" +
			"BWK --- BWK --- --- BS1 BS2 BS3";
		expected.add(boardn002);
		final Node n012 = new Node(2,false,playEvaluator);
		n01.addNode(n012);
		final String boardn012 =
			"--- --- --- --- --- TS1 TS2 TS3\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"BWK BWK BWK --- --- BS1 BS2 BS3";
		expected.add(boardn012);		
		dummyCalculators.expectedBoards = expected;
		
		nRoot.sendAllPlaysToEvaluator();
		Assert.assertEquals(expected.size(), dummyCalculators.getBoardCount());
	}	
}
