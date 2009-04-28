package gameLogic;

import gameLogic.board.Play;

import java.util.ArrayList;
import java.util.List;

public class PlaySequence {

	private List<Play> plays;
	public static final String PLAYS_SEPARATOR = "\\s";
	public static final int MAX_PLAYS = 6;

	public PlaySequence() {
		plays = new ArrayList<Play>();
	}

	public PlaySequence(final String plays) {
		this();
		addPlays(plays);
	}

	public PlaySequence(final Play... plays) {
		this();
		addPlays(plays);
	}

	public void addPlays(final Play... plays) {
		for (final Play play : plays) {
			addPlay(play);
		}
	}

	public void addPlay(final Play p){
		getPlays().add(p);
		explodeIfThereIsTooMuchPlays();
	}
	
	public void addPlays(final String plays){
		final String[] playsArray = plays.split(PLAYS_SEPARATOR);
		for (final String play : playsArray) {
//			addPlay(new Play(play));
		}
	}

	private void explodeIfThereIsTooMuchPlays() {
		if(plays.size()>MAX_PLAYS)
			throw new RuntimeException("Just "+MAX_PLAYS+" allowed. Tried to play:\n"+toString());
		
	}

	public PlaySequence copy() {
		final PlaySequence playResultCopy = new PlaySequence();
		playResultCopy.plays = new ArrayList<Play>(plays);
		return playResultCopy;
	}

	@Override
	public boolean equals(final Object obj) {
		if(!(obj instanceof PlaySequence)) {
			return false;
		}
		return toString().equals(obj.toString());
	}

	public List<Play> getPlays() {
		return plays;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		String result = "";
		boolean first = true;
		for (final Play p : plays) {
			if(!first)
				result += String.valueOf(PLAYS_SEPARATOR)+p;
			else
				result += p;
			first = false;
		}
		return result;
	}

	public void addPlays(final PlaySequence playSequence) {
		for (final Play play : playSequence.getPlays()) {
			addPlay(play);
		}
	}

	public int size() {
		return plays.size();
	}
}