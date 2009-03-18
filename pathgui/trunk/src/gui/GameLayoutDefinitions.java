package gui;

import gameEngine.gameMath.Point;

import java.awt.Dimension;

public class GameLayoutDefinitions {


	//Constants
	public static final String gameName = "Path";
	private static final int WIDTH = 730;
	private static final int HEIGHT = 505;
	public static final Dimension screenSize = new Dimension(WIDTH,HEIGHT);


	private static final int BUTTON_SIZE = 30;
	private static final int BUTTON_RESTART_SIZE = 46;
	private static final int PIECE_SIZE = 46;
	private static final int TRANSPARENT_PIECE_SIZE = PIECE_SIZE/2;
	private static final int AVATAR_SIZE = 46;

	private static final int OFFSET = 5;
	private static final int ALL_BOX_X = OFFSET;
	private static final int BOARD_SIZE = PIECE_SIZE * 8;

	private static final int BUTTONS_TOP_BOX_X = ALL_BOX_X+BUTTON_RESTART_SIZE;
	private static final int BUTTONS_TOP_BOX_Y = OFFSET;

	private static final int BOARD_BOX_X = ALL_BOX_X;
	private static final int BOARD_BOX_Y = BUTTONS_TOP_BOX_Y+AVATAR_SIZE+TRANSPARENT_PIECE_SIZE;

	private static final int BUTTONS_BOTTOM_BOX_X = ALL_BOX_X+BUTTON_RESTART_SIZE;
	private static final int BUTTONS_BOTTOM_BOX_Y = BOARD_BOX_Y+BOARD_SIZE+TRANSPARENT_PIECE_SIZE;

	public static final int gridSize = PIECE_SIZE;



	//Positions
	public static final Point avatarTopPosition = new Point(BUTTONS_TOP_BOX_X,BUTTONS_TOP_BOX_Y);

	//	public static final Point buttomTopPlayerTypeHumanPosition = new Point(BUTTONS_TOP_BOX_X+AVATAR_SIZE+AVATAR_SIZE, BUTTONS_TOP_BOX_Y);
	//	public static final Point buttomTopPlayerTypeNetPosition = new Point(buttomTopPlayerTypeHumanPosition.getX()+BUTTON_SIZE, BUTTONS_TOP_BOX_Y);
	//	public static final Point buttomTopPlayerTypeAIPosition = new Point(buttomTopPlayerTypeNetPosition.getX()+BUTTON_SIZE, BUTTONS_TOP_BOX_Y);
	//
	public static final java.awt.Point buttomNextStagePosition = new java.awt.Point(BOARD_BOX_X, BOARD_BOX_Y);
	public static final Point boardPosition = new Point(28, 68);
	public static final java.awt.Point buttomRestartPosition = new java.awt.Point((int) (boardPosition.getX()+BOARD_SIZE), BOARD_BOX_Y);

	public static final Point avatarBottomPosition = new Point(BUTTONS_BOTTOM_BOX_X,BUTTONS_BOTTOM_BOX_Y);
	//	public static final Point buttomBottomPlayerTypeHumanPosition = new Point(BUTTONS_BOTTOM_BOX_X+AVATAR_SIZE+AVATAR_SIZE, BUTTONS_BOTTOM_BOX_Y);
	//	public static final Point buttomBottomPlayerTypeNetPosition = new Point(buttomBottomPlayerTypeHumanPosition.getX()+BUTTON_SIZE, BUTTONS_BOTTOM_BOX_Y);
	//	public static final Point buttomBottomPlayerTypeAIPosition = new Point(buttomBottomPlayerTypeNetPosition.getX()+BUTTON_SIZE, BUTTONS_BOTTOM_BOX_Y);


	//Sprites
	public static final String avatarPCPlaying = "sprites/pc.png";
	public static final String avatarPCWaiting = "sprites/pc.png";
	public static final String avatarHumanPlaying = "sprites/human.png";
	public static final String avatarHumanWaiting = "sprites/human.png";
	public static final String avatarNetPlaying = "sprites/net.png";
	public static final String avatarNetWaiting = "sprites/net.png";

	public static final Point avatarTopThinkingPosition = avatarTopPosition;
	public static final String avatarTopThinking =  "sprites/thinkingTop82x96.png";


	public static final Point avatarBottomThinkingPosition = avatarBottomPosition;
	public static final String avatarBottomThinking =  "sprites/thinkingBottom82x96.png";

	public static final String background = "sprites/background.png";

	public static final String buttomNextStageBottomPuttingWeaks = "sprites/nextStageWhitePuttingWeaks.png";
	public static final String buttomNextStageBottomPuttingWeaksPressed = "sprites/nextStageWhitePuttingWeaksPressed.png";
	public static final String buttomNextStageBottomMovingStrongs = "sprites/nextStageWhiteMovingStrongs.png";
	public static final String buttomNextStageBottomMovingStrongsPressed = "sprites/nextStageWhiteMovingStrongsPressed.png";

	public static final String buttomNextStageTopPuttingWeaks = "sprites/nextStageBlackPuttingWeaks.png";
	public static final String buttomNextStageTopPuttingWeaksPressed = "sprites/nextStageBlackPuttingWeaksPressed.png";
	public static final String buttomNextStageTopMovingStrongs = "sprites/nextStageBlackMovingStrongs.png";
	public static final String buttomNextStageTopMovingStrongsPressed = "sprites/nextStageBlackMovingStrongsPressed.png";


	public static final String restart = "sprites/restart.png";
	public static final String restartPressed = "sprites/restartPressed.png";

	//	public static final String board = "sprites/board.png";

	public static final String buttomPlayerTypeNothing = "sprites/buttonsAIGray.png";
	public static final String buttomPlayerTypeEasiest = "sprites/buttonsAIEasiest.png";
	public static final String buttomPlayerTypeEasiestPressed = "sprites/buttonsAIEasiest.png";
	public static final String buttomPlayerTypeVeryEasy = "sprites/buttonsAIVeryEasy.png";
	public static final String buttomPlayerTypeVeryEasyPressed = "sprites/buttonsAIVeryEasy.png";
	public static final String buttomPlayerTypeEasy = "sprites/buttonsAIEasy.png";
	public static final String buttomPlayerTypeEasyPressed = "sprites/buttonsAIEasy.png";
	public static final String buttomPlayerTypeMedium = "sprites/buttonsAIMedium.png";
	public static final String buttomPlayerTypeMediumPressed = "sprites/buttonsAIMedium.png";
	public static final String buttomPlayerTypeHard = "sprites/buttonsAIHard.png";
	public static final String buttomPlayerTypeHardPressed = "sprites/buttonsAIHard.png";
	public static final String buttomPlayerTypeVeryHard = "sprites/buttonsAIVeryHard.png";
	public static final String buttomPlayerTypeVeryHardPressed = "sprites/buttonsAIVeryHard.png";
	public static final String buttomPlayerTypeHuman = "sprites/buttonHuman.png";
	public static final String buttomPlayerTypeHumanPressed = "sprites/buttonHumanPressed.png";
	public static final String buttomPlayerTypeNet = "sprites/buttonNet.png";
	public static final String buttomPlayerTypeNetPressed = "sprites/buttonNetPressed.png";

	public static final String pieceBottom = "sprites/whiteWeak.png";
	public static final String pieceTop = "sprites/blackWeak.png";
	//TODO: Show transp antes da jogada de peça fraca
	public static final String pieceBottomTransp = "sprites/bottomTransp46x46.png";
	public static final String pieceTopTransp = "sprites/topTransp46x46.png";

	public static final String pieceMovedStrongBottom = "sprites/whiteStrongMoved.png";
	public static final String pieceMovedStrongTop = "sprites/blackStrongMoved.png";
	public static final String pieceSelectedStrongBottom = "sprites/whiteStrongSelected.png";
	public static final String pieceSelectedStrongTop = "sprites/blackStrongSelected.png";
	public static final String pieceStrongBottom = "sprites/whiteStrong.png";
	public static final String pieceStrongTop = "sprites/blackStrong.png";


}
