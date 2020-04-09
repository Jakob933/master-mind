package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import exceptions.UnsupportedColorIDException;

public enum GameStoneColor {

	ROT(1), GRUEN(2), BLAU(3), GELB(4), BRAUN(5), VIOLETT(6), ORANGE(7), GRAU(8);
	
	private final Integer colorID;
	private static final int COLOR_QUANTITY = 8;
	
	GameStoneColor(int colorID) {this.colorID = colorID;}

	public Integer getColorID() {
		
		return colorID;
	}

	@Override
	public String toString() {
		
		return name() + "(" + getColorID() +")";
	}
	
	public static List<GameStoneColor> getRandomColorSequence (int size) {
		// returns a random sequence of distinct colors
		
		if (size > COLOR_QUANTITY) {
			throw new IllegalArgumentException("Size of Code is bigger than available Colors");
		}
		
		List<Integer> ids = IntStream.rangeClosed(1, COLOR_QUANTITY).boxed().collect(Collectors.toList());
		Collections.shuffle(ids);
		
		List<GameStoneColor> colorSequence= new ArrayList<>();
		
		for (int i=0; i<size; i++) {
			
			try {
			colorSequence.add(getColorFromID(ids.get(i)));
			}
			catch (UnsupportedColorIDException e) {
				e.printStackTrace();
			}
		}
		
		return colorSequence;
	}
	
	public static GameStoneColor getColorFromID(Integer colorID) throws UnsupportedColorIDException {
		
		switch (colorID) {
		
		case 1: return ROT;
		case 2: return GRUEN;
		case 3: return BLAU;
		case 4: return GELB;
		case 5: return BRAUN;
		case 6: return VIOLETT;
		case 7: return ORANGE; 
		case 8: return GRAU; 
		default: throw new UnsupportedColorIDException("Color ID " + colorID + " not supported");
		
		}
	}
}
