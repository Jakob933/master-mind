package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.UnsupportedColorIDException;

public class GameRound {
	
	private List<GameStoneColor> code;
	private Integer codeSize;
	private Integer maxTrials;
	private boolean finished;
	private Integer numberOfTrials;
	private boolean solved;
	private List<List<GameStoneColor>> historyTrials;
	private List<String> historyMatches;
	
	public GameRound(List<GameStoneColor> code) {
	
		this.code = code;
		this.codeSize = code.size();
		this.finished = false;
		this.numberOfTrials = 6;
		this.historyTrials = new ArrayList<>();
		this.historyMatches = new ArrayList<>();

	}
	
	public void nextCodeTry() throws IOException {
		
		numberOfTrials+=1; 
		
		if (numberOfTrials > maxTrials) {
			this.finished = true;
			this.solved = false;
			return;
		}
		
		System.out.println("\nRunde " + numberOfTrials + "/" + maxTrials);
		
		List<GameStoneColor> trial = getCodeFromUser(this.getCodeSize());
		
		if (checkTrialMatchesColorCode(trial)) {
			this.finished = true;
			this.solved = true;
		}
		
		historyTrials.add(trial);
		historyMatches.add(getMatchingColors(trial));
		printHistory();
		
	}
	
	private boolean checkTrialMatchesColorCode(List<GameStoneColor> trialColorCode) {

		if (trialColorCode.size() != code.size()) {
			throw new IllegalArgumentException("Code length doesnt match");
		}

		for (int i = 0; i < trialColorCode.size(); i++) {

			if (code.get(i) != trialColorCode.get(i)) {
				return false;
			}
		}

		return true;
	}
	
	private String getMatchingColors(List<GameStoneColor> trial) {

		String matches = "";
		if (code.size() != trial.size()) {
			throw new IllegalArgumentException("Code length doesnt match");
		}

		for (int i = 0; i < code.size(); i++) {

			if (code.get(i) == trial.get(i)) {
				matches+="#";
			}
			else if (code.contains(trial.get(i))) {
				matches+="*";
			}
		}
		
		//sort String to avoid identification of matching sequence
		char tempArray[] = matches.toCharArray();
		Arrays.sort(tempArray);
		
		return new String(tempArray);

	}
	
	public void printHistory() {
		
		System.out.println("\nSpielverlauf:");
		for (int i = 0; i<historyTrials.size(); i++) {
			
			System.out.println(Arrays.toString(historyTrials.get(i).toArray()) + "" + historyMatches.get(i));
			
		}
		
	}
	
	public List<GameStoneColor> getCode() {
		return code;
	}

	public int getCodeSize() {
		return codeSize;
	}

	public void printRules() {
		System.out.println("Folgende Farben sind verfügbar: \n" + Arrays.asList(GameStoneColor.values()) + "\n");
		
		System.out.println("Regeln: Keine Farben dürfen doppelt eingegeben werden "
				+ "\n * bedeutet richtige Farbe, falsche Position "
				+ "\n # bedeutet richtige Farbe, richtige Position"
				+ "\n Codelänge: " + codeSize + "\n");
	}

	public void setNumberOfTrialsByUserInput() throws IOException {
		
		maxTrials = getNumberOfTrialsFromUser();
		System.out.println("\nOK, du hast " + maxTrials + " Versuche!");
		
	}
	
	// return parameter to enable recursively method
	private static int getNumberOfTrialsFromUser() throws IOException {
		
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

		System.out.println("Code eingeben:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String code = reader.readLine().trim();

		if (!code.matches("^\\d{" + codeSize + "}$")) {
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
	
	private static boolean checkDuplicateChar(CharSequence g) {
	    for (int i = 0; i < g.length(); i++) {
	        for (int j = i + 1; j < g.length(); j++) {
	            if (g.charAt(i) == g.charAt(j)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isSolved() {
		return solved;
	}
	
}
