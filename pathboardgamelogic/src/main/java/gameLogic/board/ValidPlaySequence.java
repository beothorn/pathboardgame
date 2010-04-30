package gameLogic.board;

import java.util.ArrayList;
import java.util.List;

public class ValidPlaySequence {
	
	private final List<ValidPlay> validPlays;
	
	ValidPlaySequence() {
		validPlays = new ArrayList<ValidPlay>();
	}
	
	void addValidPlay(ValidPlay validPlay){
		validPlays.add(validPlay);
	}
	
	public List<ValidPlay> validPlays(){
		ArrayList<ValidPlay> validPlaysReturn = new ArrayList<ValidPlay>();
		validPlaysReturn.addAll(validPlays);
		return validPlaysReturn;
	}

}
