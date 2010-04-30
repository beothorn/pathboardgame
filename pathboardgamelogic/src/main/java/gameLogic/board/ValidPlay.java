package gameLogic.board;

public class ValidPlay {
	private final Play validPlay;
	
	ValidPlay(Play validPlay) {
		this.validPlay = validPlay;
	}
	
	public Play unbox(){
		return validPlay;
	}
	
	@Override
	public String toString() {
		return validPlay.toString();
	}
}
