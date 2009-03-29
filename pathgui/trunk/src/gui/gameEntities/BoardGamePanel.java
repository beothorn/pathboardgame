package gui.gameEntities;

import gameEngine.JGamePanel;
import gameLogic.Game;
import gameLogic.Piece;
import gameLogic.gameFlow.BoardListener;
import gameLogic.gameFlow.GameState;
import gui.GameLayoutDefinitions;
import gui.externalPlayer.ExternalPlayerController;

public class BoardGamePanel extends JGamePanel implements BoardListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Avatar avatarBottom;
	private final Avatar avatarTop;

	private final ExternalPlayerController externalPlayerController;
	private final Game game = new Game();
	private final NextStageButton nextStageButton;
	private final RestartButton restartButton;
	private final PiecesBoard piecesBoard;

	public BoardGamePanel() {
		super(GameLayoutDefinitions.background);
		reset();

		externalPlayerController = new ExternalPlayerController(game);

		nextStageButton = new NextStageButton(game);
		nextStageButton.setLocation(GameLayoutDefinitions.buttomNextStagePosition);
		restartButton = new RestartButton(game);
		restartButton.setLocation(GameLayoutDefinitions.buttomRestartPosition);

		piecesBoard = new PiecesBoard(this);
		piecesBoard.setPositon(GameLayoutDefinitions.boardPosition);

		final boolean isTopPlayer = true;
		avatarTop = new Avatar(GameLayoutDefinitions.avatarTopPosition , GameLayoutDefinitions.avatarTopThinkingPosition, isTopPlayer);
		avatarBottom = new Avatar(GameLayoutDefinitions.avatarBottomPosition, GameLayoutDefinitions.avatarBottomThinkingPosition, !isTopPlayer);

		refreshAvatarsDescription();

		addGameElement(avatarTop);
		addGameElement(avatarBottom);
		addGameElement(piecesBoard);
		add(nextStageButton);
		add(restartButton);

		//		gameFrame.addButton(new PlayerTypeButton(isTopPlayer,PlayerTypes.HUMAN,this,GameLayoutDefinitions.buttomTopPlayerTypeHumanPosition));
		//		gameFrame.addButton(new PlayerTypeButton(isTopPlayer,PlayerTypes.NETWORK,this,GameLayoutDefinitions.buttomTopPlayerTypeNetPosition));
		//		gameFrame.addButton(new PlayerTypeButton(isTopPlayer,PlayerTypes.AI_EASIEST,this,GameLayoutDefinitions.buttomTopPlayerTypeAIPosition));
		//
		//		gameFrame.addButton(new PlayerTypeButton(false,PlayerTypes.HUMAN,this,GameLayoutDefinitions.buttomBottomPlayerTypeHumanPosition));
		//		gameFrame.addButton(new PlayerTypeButton(false,PlayerTypes.NETWORK,this,GameLayoutDefinitions.buttomBottomPlayerTypeNetPosition));
		//		gameFrame.addButton(new PlayerTypeButton(false,PlayerTypes.AI_EASIEST,this,GameLayoutDefinitions.buttomBottomPlayerTypeAIPosition));

		gameStateChanged(game.isTopPlayerTurn(), game.isBottomPlayerTurn(), game.getCurrentState());
	}

	private void addErrorMessageShower() {
		final ErrorMessage err = new ErrorMessage(this);
		getGame().setErrorMessageDisplayer(err);
		addGameElement(err);
	}

	private void addGamesEntitiesController() {
		getGame().addBoardListener(this);
	}

	@Override
	public void boardChanged() {
		piecesBoard.boardChanged();
	}

	private void createElementsInGameFrame() {
		addGamesEntitiesController();
		addErrorMessageShower();
	}

	@Override
	public void gameStateChanged(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn, final GameState gs) {
		nextStageButton.gameStateChanged(isTopPlayerTurn,isBottomPlayerTurn, gs);
		piecesBoard.gameTurnAdvanced();
		avatarTop.gameTurnAdvanced(isTopPlayerTurn,isBottomPlayerTurn);
		avatarBottom.gameTurnAdvanced(isTopPlayerTurn,isBottomPlayerTurn);
	}

	public Game getGame() {
		return game;
	}

	@Override
	public void movedStrong(final Piece movedPiece) {
		piecesBoard.movedStrong(movedPiece);
	}

	private void refreshAvatarsDescription() {
		avatarTop.setPlayerType(externalPlayerController.getTopPlayerType());
		avatarBottom.setPlayerType(externalPlayerController.getBottomPlayerType());
	}

	public void reset() {
		clearElements();
		createElementsInGameFrame();
	}

	@Override
	public void selectedStrong(final Piece selectedPiece) {
		piecesBoard.selectedStrong(selectedPiece);
	}

	public void setBottomPlayerType(final int type) {
		externalPlayerController.setBottomPlayerType(type);
		refreshAvatarsDescription();
	}

	public void setTopPlayerType(final int type) {
		externalPlayerController.setTopPlayerType(type);
		refreshAvatarsDescription();
	}

	@Override
	public void unselectedStrong(final Piece unselectedPiece) {
		piecesBoard.unselectedStrong(unselectedPiece);
	}

}
