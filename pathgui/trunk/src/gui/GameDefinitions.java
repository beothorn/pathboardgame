package gui;

import gameEngine.gameMath.Point;

import java.awt.Dimension;

public class GameDefinitions {

	private static final int PIECE_SIZE = 46;
	private static final int BOARD_SIZE = PIECE_SIZE * 8;

	public Point getAvatarBottomPosition() {
		return new Point(18,454);
	}

	public String getAvatarHumanPlaying() {
		return "sprites/human.png";
	}

	public String getAvatarHumanWaiting() {
		return "sprites/human.png";
	}

	public String getAvatarPCPlaying() {
		return "sprites/pc.png";
	}

	public String getAvatarPCWaiting() {
		return "sprites/pc.png";
	}

	public Point getAvatarTopPosition() {
		return new Point(18,10);
	}

	public String getBackground() {
		return "sprites/background.png";
	}

	public Point getBoardPosition() {
		return new Point(28, 68);
	}

	public int getBoardSize() {
		return BOARD_SIZE;
	}

	public double getBoardX() {
		return 77;
	}

	public double getBoardY() {
		return 51;
	}

	public Point getBottomPuttingPreviewPosition() {
		return new Point(28, 68+BOARD_SIZE);
	}

	public java.awt.Point getButtomNextStagePosition() {
		return new java.awt.Point(0, 0);
	}

	public java.awt.Point getButtomRestartPosition() {
		return new java.awt.Point(0, 20);
	}

	public String getGameName() {
		return "Path";
	}

	public double getGridHeight() {
		return PIECE_SIZE;
	}

	public int getGridWidth() {
		return PIECE_SIZE;
	}

	public String getPieceBottom() {
		return "sprites/whiteWeak.png";
	}

	public String getPieceStrongBottom() {
		return "sprites/whiteStrong.png";
	}

	public String getPieceStrongMovedBottom() {
		return "sprites/whiteStrongMoved.png";
	}

	public String getPieceStrongMovedTop() {
		return "sprites/blackStrongMoved.png";
	}

	public String getPieceStrongPlayingBottom() {
		return "sprites/whiteStrongSelected.png";
	}

	public String getPieceStrongPlayingTop() {
		return "sprites/blackStrongSelected.png";
	}

	public String getPieceStrongTop() {
		return "sprites/blackStrong.png";
	}

	public String getPieceTop() {
		return "sprites/blackWeak.png";
	}

	public Dimension getScreenSize() {
		return new Dimension(500,505);
	}

	public Point getTopPuttingPreviewPosition() {
		return new Point(28, 68-PIECE_SIZE);
	}

}
