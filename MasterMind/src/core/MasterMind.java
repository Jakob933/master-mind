package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.UnsupportedColorIDException;

public class MasterMind {

	public static void main(String[] args) throws IOException {

		int codeSize = 4;
		System.out.println("-- Willkommen bei Master Mind --- \n");

		List<GameStoneColor> colorCode = GameStoneColor.getRandomColorSequence(codeSize);

		printWelcome(codeSize);
		
		int numberOfTrials = getNumberOfTrialsFromUser();
		System.out.println("\nOK, du hast " + numberOfTrials + " Versuche!");

		boolean solved = false;

		List<List<GameStoneColor>> historyTrials = new ArrayList<>();
		List<String> historyMatches = new ArrayList<>();

		for (int i = 1; i <= numberOfTrials; i++) {

			List<GameStoneColor> trial = getCodeFromUser(codeSize);

			if (checkTrialMatchesColorCode(colorCode, trial)) {
				solved = true;
				break;
			}

			historyTrials.add(trial);
			historyMatches.add(getMatchingColors(colorCode, trial));

			printHistory(historyTrials, historyMatches);
		}

		System.out.println(solved ? "--- Gewonnen! ---" : "--- Verloren :( ---");
		
		if (solved==false) {
			System.out.println(" Die richtige Lösung war: " + Arrays.toString(colorCode.toArray()));
		}
	}

	private static void printWelcome(int codeSize) {
		System.out.println("Folgende Farben sind verfügbar: \n" + Arrays.asList(GameStoneColor.values()) + "\n");
		
		System.out.println("Regeln: Keine Farben dürfen doppelt eingegeben werden "
				+ "\n * bedeutet richtige Farbe, falsche Position "
				+ "\n # bedeutet richtige Farbe, richtige Position"
				+ "\n Codelänge: " + codeSize + "\n");
	}

	private static void printHistory(List<List<GameStoneColor>> historyTrials, List<String> historyMatches) {
	
		System.out.println("\nVerlauf:");
		for (int i = 0; i<historyTrials.size(); i++) {
			
			System.out.println(Arrays.toString(historyTrials.get(i).toArray()) + "" + historyMatches.get(i));
			
		}
		
	}

	private static String getMatchingColors(List<GameStoneColor> colorCode, List<GameStoneColor> trial) {

		String matches = "";
		if (colorCode.size() != trial.size()) {
			throw new IllegalArgumentException("Code length doesnt match");
		}

		for (int i = 0; i < colorCode.size(); i++) {

			if (colorCode.get(i) == trial.get(i)) {
				matches+="#";
			}
			else if (colorCode.contains(trial.get(i))) {
				matches+="*";
			}
		}
		
		//sort String to avoid identification of matching sequence
		char tempArray[] = matches.toCharArray();
		Arrays.sort(tempArray);
		
		return new String(tempArray);

	}

	private static boolean checkTrialMatchesColorCode(List<GameStoneColor> colorCode, List<GameStoneColor> trial) {

		if (colorCode.size() != trial.size()) {
			throw new IllegalArgumentException("Code length doesnt match");
		}

		for (int i = 0; i < colorCode.size(); i++) {

			if (colorCode.get(i) != trial.get(i)) {
				return false;
			}
		}

		return true;
	}

	private static Integer getNumberOfTrialsFromUser() throws IOException {

		System.out.println("Anzahl an Versuchen eingeben:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		Integer numberOfTrials;
		String input = reader.readLine().trim();

		if (input.matches("^[1-9]\\d*$")) {
			numberOfTrials = Integer.parseInt(input);
		}

		else {
			System.out.println("Ungueltige Eingabe, erneut versuchen...");
			numberOfTrials = getNumberOfTrialsFromUser();
		}

		return numberOfTrials;

	}

	private static List<GameStoneColor> getCodeFromUser(int codeSize) throws IOException {

		System.out.println("\nCode eingeben:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String code = reader.readLine().trim();

		if (!code.matches("^{" + codeSize + "}\\d*$")) {
			System.out.println("Ungueltige Eingabe, erneut versuchen...");
			return getCodeFromUser(codeSize);
		}

		if (checkDuplicateChar(code)) {
			System.out.println("Farben dürfen kein Duplikat enthalten, erneut versuchen...");
			return getCodeFromUser(codeSize);
		}
		
		char[] ids = code.toCharArray();

		List<GameStoneColor> colorCode = new ArrayList<>();

		for (char c : ids) {
			try {
				colorCode.add(GameStoneColor.getColorFromID(Character.getNumericValue(c)));
			} catch (UnsupportedColorIDException e) {

				System.out.println("Ungueltige Color-ID " + c + " , erneut versuchen...");
				return getCodeFromUser(codeSize);

			}
		}

		return colorCode;

	}
	
	public static boolean checkDuplicateChar(CharSequence g) {
	    for (int i = 0; i < g.length(); i++) {
	        for (int j = i + 1; j < g.length(); j++) {
	            if (g.charAt(i) == g.charAt(j)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

}
