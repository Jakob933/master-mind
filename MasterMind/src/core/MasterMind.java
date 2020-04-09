package core;

import java.io.IOException;
import java.util.Arrays;

public class MasterMind {

	public static void main(String[] args) throws IOException {

		int codeSize = 4;
		
		System.out.println("-- Willkommen zu Master Mind --- \n");

		GameRound gameRound = new GameRound(GameStoneColor.getRandomColorSequence(codeSize));

		gameRound.printRules();
		
		gameRound.setNumberOfTrialsByUserInput();

		while (!gameRound.isFinished()) {
		
			gameRound.nextCodeTry();
			
		}
		
		System.out.println(gameRound.isSolved() ? "--- Gewonnen! ---" : "--- Verloren :( ---");
		
		if (gameRound.isSolved() == false) {
			System.out.println(" Die richtige Lösung war: " + Arrays.toString(gameRound.getCode().toArray()));
		}
	}

}
