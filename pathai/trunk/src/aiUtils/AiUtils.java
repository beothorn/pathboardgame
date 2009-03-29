package aiUtils;

import gameLogic.Board;
import gameLogic.Piece;
import gameLogic.Play;

import java.awt.Point;

public class AiUtils {
	
	public static boolean simulatePlayOnBoard(final Board b, final Play play,final boolean isTopPlayer) {
		if(play.isMoveDirection()){
			Point strong;
			if(isTopPlayer) {
				strong = b.getStrongTopById(play.getPieceId());
			} else {
				strong = b.getStrongBottomId(play.getPieceId());
			}
			final Point strongTo = (Point) strong.clone();
			switch (play.getDirection()) {
			case Play.UP:
				strongTo.y -= 1;
				break;
			case Play.DOWN:
				strongTo.y += 1;
				break;
			case Play.LEFT:
				strongTo.x -= 1;
				break;
			case Play.RIGHT:
				strongTo.x += 1;
				break;
			default:
				break;
			}
			return b.moveStrongPiece(strong.y, strong.x, strongTo.y, strongTo.x);
		}else{
			if(isTopPlayer) {
				return b.addPiece(Piece.getTopWeakPiece(), play.getColumn());
			}
			return b.addPiece(Piece.getBottomWeakPiece(), play.getColumn());
		}
	}

}
