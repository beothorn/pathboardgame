package gameLogic;
import gameLogic.gameFlow.PlayResult;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;

import java.awt.Point;

import junit.framework.Assert;

import org.junit.Test;

public class FunctionalLogicTests{

	String bottomPlayerMoveStrong_B1 =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"24042400\n";
	String bottomPlayerMoveStrong_B2 =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"24424000\n";

	String bottomPlayerMoveStrong_B3 =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"40424002\n";
	String bottomPlayerMoveStrongsAndPass_E1 =
		"13113111\n" +
		"00000001\n" +
		"00000031\n" +
		"00000000\n" +
		"00000000\n" +
		"00000002\n" +
		"20400002\n" +
		"42024222\n";

	String bottomPlayerPutStrongs_A =
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"04400400\n";

	String bottomPlayerPutWeaks_B =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"24420400\n";

	String bottomPlayerPutWeaks_F1 =
		"13113111\n" +
		"00000001\n" +
		"00000031\n" +
		"00000000\n" +
		"00000000\n" +
		"00000002\n" +
		"20400002\n" +
		"42224222\n";

	String bottomPlayerPutWeaksAndPass_C =
		"30030010\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"00000002\n" +
		"20000002\n" +
		"40424002\n";

	String bottomPlayerPutWeaksAndPass_D =
		"11301013\n" +
		"00000000\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"20000002\n" +
		"42424222\n";

	private Game currentGame;

	String testeHookCase1 =
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00032000\n" +
		"00041000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n";

	String testeHookCase1BottomMoveStrong =
		"00000100\n" +
		"00000000\n" +
		"00000000\n" +
		"00032000\n" +
		"00004000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n";

	String testeHookCase2 =
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000200\n" +
		"00000100\n" +
		"00000200\n";

	String testeHookCase2TopPlayerPutsWeak1 =
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000200\n" +
		"00000100\n";

	String testeHookCase2TopPlayerPutsWeak2 =
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000200\n";

	String testeHookCase2TopPlayerPutsWeak3 =
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n" +
		"00000100\n";

	String topPlayerMoveStrong_B1 =
		"30030013\n" +
		"00000001\n" +
		"00000001\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"40424002\n";

	String topPlayerMoveStrongAndPass_B2 =
		"30030010\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"40424002\n";

	String topPlayerMoveStrongs_C1 =
		"31131010\n" +
		"00000000\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"20000002\n" +
		"40424002\n";

	String topPlayerMoveStrongs_D1 =
		"31131111\n" +
		"00000000\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"20000002\n" +
		"42424222\n";

	String topPlayerMoveStrongs_D2 =
		"13113111\n" +
		"00000000\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"20000002\n" +
		"42424222\n";

	String topPlayerMoveStrongs_D3 =
		"13113111\n" +
		"00000001\n" +
		"00000031\n" +
		"00000000\n" +
		"00000000\n" +
		"00000002\n" +
		"20000002\n" +
		"42424222\n";

	String topPlayerMoveStrongsAndPass_C2 =
		"11301013\n" +
		"00000000\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"20000002\n" +
		"40424002\n";

	String topPlayerPutStrongs_A =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"04400400\n";

	String topPlayerPutWeaks_B =
		"33030001\n" +
		"00000001\n" +
		"00000001\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"40424002\n";

	String topPlayerPutWeaks_C =
		"31131010\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"00000002\n" +
		"20000002\n" +
		"40424002\n";


	String topPlayerPutWeaksAndPass_D =
		"11311113\n" +
		"00000000\n" +
		"00000003\n" +
		"00000001\n" +
		"00000001\n" +
		"00000002\n" +
		"20000002\n" +
		"42424222\n";

	private PlayResult addPiece(final int pieceColumn){
		final Play p = new Play(0,pieceColumn);
		return currentGame.play(p);
	}

	private void addPieceWithoutProblems(final int pieceColumn){
		final Play p = new Play(0,pieceColumn);
		final PlayResult playResult = currentGame.play(p);
		if(!playResult.isSuccessful()) {
			throw new RuntimeException("add failed");
		}
	}

	private PlayResult moveStrongPiece(final int lineFrom,final int columnFrom,final int lineTo,final int columnTo){
		final Play pFrom = new Play(lineFrom, columnFrom);
		final Play pTo = new Play(lineTo, columnTo);

		final PlayResult playFrom = currentGame.play(pFrom);
		if(!playFrom.isSuccessful()){
			return playFrom;
		}

		final PlayResult playTo = currentGame.play(pTo);
		if(!playTo.isSuccessful()){
			return playTo;
		}
		final boolean successful = true;
		return new PlayResult(successful);
	}

	private void moveStrongPieceByIdWithoutProblems(final int id,final char direction){
		final Play p = new Play(id, direction);
		final PlayResult playResult = currentGame.play(p);
		if(!playResult.isSuccessful()){
			throw new RuntimeException("move strong by id failed");
		}
	}

	private void moveStrongPieceQuietly(final int lineFrom,final int columnFrom,final int lineTo,final int columnTo){
		final Play pFrom = new Play(lineFrom, columnFrom);
		final Play pTo = new Play(lineTo, columnTo);
		currentGame.forcePlay(pFrom);
		currentGame.forcePlay(pTo);
	}

	private void moveStrongPieceWithoutProblems(final int lineFrom,final int columnFrom,final int lineTo,final int columnTo){
		final PlayResult moveStrongPiecePlayResult = moveStrongPiece(lineFrom,columnFrom,lineTo,columnTo);
		if(!moveStrongPiecePlayResult.isSuccessful()) {
			throw new RuntimeException("move failed");
		}
	}

	private void nextState(){
		currentGame.play(new Play(Play.NEXT_STATE));
	}

	private String printBoard() {
		return currentGame.getBoard().toString();
	}

	@Test
	public void testChangeStateWithLockedGame(){
		final Board board = new Board();
		board.fromString(testeHookCase1);
		final boolean bottomPlayer = false;
		currentGame = new Game(board, new GameStateMovingStrongs(bottomPlayer));
		final String stateDescription = currentGame.getStateDescription();
		currentGame.setLocked(true);
		final Play play = new Play(Play.NEXT_STATE);
		currentGame.play(play);
		Assert.assertEquals(stateDescription,currentGame.getStateDescription());
	}

	@Test
	public void testHookCaseAndSetPiece(){
		final Board board = new Board();
		board.fromString(testeHookCase1);
		final boolean bottomPlayer = false;
		currentGame = new Game(board, new GameStateMovingStrongs(bottomPlayer));
		Assert.assertEquals(testeHookCase1, printBoard());
		moveStrongPiece(4, 3, 4, 4);
		Assert.assertEquals(testeHookCase1BottomMoveStrong, printBoard());

		board.fromString(testeHookCase2);
		final boolean topPlayer = true;
		currentGame = new Game(board,new GameStatePuttingWeaks(topPlayer));

		Assert.assertEquals(testeHookCase2, printBoard());
		addPiece(5);
		Assert.assertEquals(testeHookCase2TopPlayerPutsWeak1, printBoard());
		addPiece(5);
		Assert.assertEquals(testeHookCase2TopPlayerPutsWeak2, printBoard());
		addPiece(5);
		Assert.assertEquals(testeHookCase2TopPlayerPutsWeak3, printBoard());
	}

	@Test
	public void testPlayWithLockedGame(){
		final Board board = new Board();
		board.fromString(testeHookCase1);
		final boolean bottomPlayer = false;
		currentGame = new Game(board, new GameStateMovingStrongs(bottomPlayer));
		Assert.assertEquals(testeHookCase1, printBoard());
		currentGame.setLocked(true);
		moveStrongPiece(4, 3, 4, 4);
		Assert.assertEquals(testeHookCase1, printBoard());
		moveStrongPieceQuietly(4, 3, 4, 4);
		Assert.assertEquals(testeHookCase1BottomMoveStrong, printBoard());
	}

	@Test
	public void testBoardStringInOut(){
		final Board board = new Board();
		board.fromString(testeHookCase1);
		Assert.assertEquals(testeHookCase1, board.toString());
	}
	
	@Test
	public void testPuttingStrongsIdBug(){
		currentGame = new Game();
		addPiece(1);
		addPiece(2);
		addPiece(2);
		addPiece(2);
		addPiece(3);

		final Point strongBottom = currentGame.getBoard().getStrongBottomByPositionInSequence(3);
		strongBottom.toString();

	}

	@Test
	public void testSimpleGame(){
		currentGame = new Game();
		final String topPlayerPuttingStrongDescription = "Black player: add 3 strong pieces to the first line.";
		final String bottomPlayerPuttingWeaksDescription = "White player: Put three weak pieces in first line or pass to moving strongs.";
		final String bottomPlayerMovingStrongsDesription = "White player: Move your strong pieces one time each or pass our turn.";
		final String messageYouCantDoThis = "You can't do this.";
		final String messageYouCantMoveThisPiece = "You can't move this piece";

		addPieceWithoutProblems(1);
		//Should not have a problem putting a piece on a copied game
		currentGame.getBoard().copy().addPiece(Piece.getBottomStrongPiece(), 2);
		addPieceWithoutProblems(2);
		addPieceWithoutProblems(5);
		Assert.assertEquals(bottomPlayerPutStrongs_A, printBoard());
		Assert.assertEquals(topPlayerPuttingStrongDescription, currentGame.getStateDescription());

		addPieceWithoutProblems(0);
		addPieceWithoutProblems(1);
		addPieceWithoutProblems(3);
		Assert.assertEquals(topPlayerPutStrongs_A, printBoard());
		Assert.assertEquals(bottomPlayerPuttingWeaksDescription, currentGame.getStateDescription());

		addPieceWithoutProblems(0);
		addPieceWithoutProblems(0);
		final PlayResult addPiece1PlayResult = addPiece(1);
		Assert.assertTrue("Add Piece should fail", !addPiece1PlayResult.isSuccessful());
		Assert.assertEquals(messageYouCantDoThis+"\n"+bottomPlayerPuttingWeaksDescription,addPiece1PlayResult.getErrorMessage());

		addPieceWithoutProblems(3);
		Assert.assertEquals(bottomPlayerPutWeaks_B, printBoard());

		moveStrongPieceByIdWithoutProblems(2, Play.RIGHT);

		Assert.assertEquals(bottomPlayerMoveStrong_B1, printBoard());
		final PlayResult moveStrongPiece7475PlayResult = moveStrongPiece(7, 4, 7, 5);
		Assert.assertTrue("Play result should be failure", !moveStrongPiece7475PlayResult.isSuccessful());
		Assert.assertEquals(messageYouCantMoveThisPiece+"\n"+bottomPlayerMovingStrongsDesription,moveStrongPiece7475PlayResult.getErrorMessage());

		moveStrongPieceWithoutProblems(7, 5, 7, 4);
		Assert.assertEquals(bottomPlayerMoveStrong_B2, printBoard());

		final PlayResult moveStrongPiece7172PlayResult = moveStrongPiece(7, 1, 7, 2);
		Assert.assertTrue("Play result should be failure", !moveStrongPiece7172PlayResult.isSuccessful());
		Assert.assertEquals(messageYouCantMoveThisPiece+"\n"+bottomPlayerMovingStrongsDesription,moveStrongPiece7172PlayResult.getErrorMessage());

		currentGame.play(new Play(7,1));

		moveStrongPieceWithoutProblems(7, 1, 7, 0);
		Assert.assertEquals(bottomPlayerMoveStrong_B3, printBoard());

		for(int i=0;i<3;i++) {
			addPiece(7);
		}
		Assert.assertEquals(topPlayerPutWeaks_B, printBoard());
		moveStrongPieceWithoutProblems(0, 1, 0, 0);
		Assert.assertEquals(topPlayerMoveStrong_B1, printBoard());
		moveStrongPieceWithoutProblems(0, 7, 1, 7);
		Assert.assertEquals(topPlayerMoveStrongAndPass_B2, printBoard());
		nextState();

		for(int i=0;i<3;i++) {
			addPieceWithoutProblems(7);
		}
		Assert.assertEquals(bottomPlayerPutWeaksAndPass_C, printBoard());
		nextState();

		addPieceWithoutProblems(1);
		addPieceWithoutProblems(2);
		addPieceWithoutProblems(4);
		Assert.assertEquals(topPlayerPutWeaks_C, printBoard());
		moveStrongPieceWithoutProblems(1, 7, 2, 7);
		Assert.assertEquals(topPlayerMoveStrongs_C1, printBoard());
		moveStrongPieceWithoutProblems(0, 3, 0, 2);
		Assert.assertEquals(topPlayerMoveStrongsAndPass_C2, printBoard());
		nextState();

		final PlayResult addPiece7 = addPiece(7);
		Assert.assertTrue("Play result should be failure", !addPiece7.isSuccessful());
		Assert.assertEquals(messageYouCantDoThis+"\n"+bottomPlayerPuttingWeaksDescription, addPiece7.getErrorMessage());

		addPieceWithoutProblems(1);
		addPieceWithoutProblems(5);
		addPieceWithoutProblems(6);

		final PlayResult moveStrongPiece7273PlayResult = moveStrongPiece(7,2,7,3);
		Assert.assertTrue("Play result should be failure", !moveStrongPiece7273PlayResult.isSuccessful());
		Assert.assertEquals(messageYouCantMoveThisPiece+"\n"+bottomPlayerMovingStrongsDesription, moveStrongPiece7273PlayResult.getErrorMessage());

		nextState();
		Assert.assertEquals(bottomPlayerPutWeaksAndPass_D, printBoard());


		addPieceWithoutProblems(3);
		addPieceWithoutProblems(5);
		nextState();
		Assert.assertEquals(topPlayerPutWeaksAndPass_D, printBoard());

		moveStrongPieceWithoutProblems(0, 2, 0, 3);
		Assert.assertEquals(topPlayerMoveStrongs_D1, printBoard());
		moveStrongPieceWithoutProblems(0, 0, 0, 1);
		Assert.assertEquals(topPlayerMoveStrongs_D2, printBoard());
		moveStrongPieceWithoutProblems(2, 7, 2, 6);
		Assert.assertEquals(topPlayerMoveStrongs_D3, printBoard());
		nextState();
		moveStrongPieceWithoutProblems(7, 2, 6, 2);
		Assert.assertEquals(bottomPlayerMoveStrongsAndPass_E1, printBoard());
		nextState();
		nextState();
		nextState();
		addPieceWithoutProblems(2);
		Assert.assertEquals(bottomPlayerPutWeaks_F1, printBoard());
	}

}
