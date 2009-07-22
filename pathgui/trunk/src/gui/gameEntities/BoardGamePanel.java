package gui.gameEntities;

import externalPlayer.AiControl;
import gameEngine.JGamePanel;
import gameLogic.Game;
import gui.GameDefinitions;
import gui.gameEntities.piecesBoard.BoardPlayInputDecoder;
import gui.gameEntities.piecesBoard.PiecesBoard;
import ai.AIPlayer;

public class BoardGamePanel extends JGamePanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoardGamePanel(final boolean isTopAi, final boolean isBottomAi, final GameDefinitions gameDefinitions) {
		super(gameDefinitions.getBackground());
		final Game game = new Game();

		final PiecesBoard piecesBoard = new PiecesBoard(game.getBoard(),game.getCurrentState(),this,gameDefinitions);
		final boolean processForTop = !isTopAi;
		final boolean processForBottom = !isBottomAi;
		final BoardPlayInputDecoder processMouseEventForBoard = new BoardPlayInputDecoder(processForTop,processForBottom,game,piecesBoard);
		addMouseListener(processMouseEventForBoard);
		if(isTopAi){
			game.addTurnListener(new AiControl(new AIPlayer(), true));
		}
		if(isBottomAi){
			game.addTurnListener(new AiControl(new AIPlayer(), false));
		}
		final NextStageButton nextStageButton = new NextStageButton(processMouseEventForBoard);
		nextStageButton.setLocation(gameDefinitions.getButtomNextStagePosition());

		final RestartButton restartButton = new RestartButton(processMouseEventForBoard);
		restartButton.setLocation(gameDefinitions.getButtomRestartPosition());

		final ErrorMessage errorMessageShower = new ErrorMessage(this);
		addGameElement(errorMessageShower);
		processMouseEventForBoard.addErrorListener(errorMessageShower);

		if(isTopAi){
			addGameElement(new Avatar(gameDefinitions.getAvatarTopPosition(), true, gameDefinitions.getAvatarPCPlaying(), gameDefinitions.getAvatarPCWaiting()));
		}else{
			addGameElement(new Avatar(gameDefinitions.getAvatarTopPosition(), true, gameDefinitions.getAvatarHumanPlaying(), gameDefinitions.getAvatarHumanWaiting()));
		}

		if(isBottomAi){
			addGameElement(new Avatar(gameDefinitions.getAvatarBottomPosition(), false, gameDefinitions.getAvatarPCPlaying(), gameDefinitions.getAvatarPCWaiting()));
		}else{
			addGameElement(new Avatar(gameDefinitions.getAvatarBottomPosition(), false, gameDefinitions.getAvatarHumanPlaying(), gameDefinitions.getAvatarHumanWaiting()));
		}

		final PuttingPiecesDisplay topPuttingPiecesDisplay = new PuttingPiecesDisplay(this, gameDefinitions.getTopPuttingPreviewPosition(), gameDefinitions.getPieceTop(), gameDefinitions.getPieceStrongTop(),true,gameDefinitions.getBoardSize(),gameDefinitions.getGridWidth());
		game.addPhaseChangeListener(topPuttingPiecesDisplay);

		final PuttingPiecesDisplay bottomPuttingPiecesDisplay = new PuttingPiecesDisplay(this, gameDefinitions.getBottomPuttingPreviewPosition(), gameDefinitions.getPieceBottom(), gameDefinitions.getPieceStrongBottom(),false,gameDefinitions.getBoardSize(),gameDefinitions.getGridWidth());
		game.addPhaseChangeListener(bottomPuttingPiecesDisplay);

		add(nextStageButton);
		add(restartButton);
	}
}
