import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Test extends Mode{
	private static int number;
	private int showEnglish;
	private int showRussian;
	private int showDefinition;
	private int playAudio;
	private int letShowingAll;
	
	Test (int showEnglish, int showRussian, int showDefinition, int playAudio) {
		super();
		// 2 for show after correct answer
		// 1 for show by default
		// 0 for show by command
		setShowEnglish(showEnglish);
		setShowRussian(showRussian);
		setShowDefiniton(showDefinition);
		setPlayAudio(playAudio);
	}
	public static int getNumber() {
		return number;
	}
	public int getShowEnglish() {
		return this.showEnglish;
	}
	public int getShowRussian() {
		return this.showRussian;
	}
	public int getShowDefinition() {
		return this.showDefinition;
	}
	public int getPlayAudio() {
		return this.playAudio;
	}
	public int getLetShowingAll() {
		return this.letShowingAll;
	}
	//------------------------------------------
	public void setShowEnglish(int showEnglish) {
		this.showEnglish = showEnglish;
	}
	public void setShowRussian(int showRussian) {
		this.showRussian = showRussian;
	}
	public void setShowDefiniton(int showDefiniton) {
		this.showDefinition = showDefiniton;
	}
	public void setPlayAudio(int playAudio) {
		this.playAudio = playAudio;
	}
	public void setLetShowingAll(int letShowingAll) {
		this.letShowingAll = letShowingAll;
	}
	
	public String configure(Word word, int index) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		StringBuilder editedMessage;
		String message = "";
		if (getShowEnglish() == index) {
			message = message + word.getEn() + " — ";
		}
		if (getShowRussian() == index) {
			message = message + word.getRu() + " — ";
		}
		if (getShowDefinition() == index) {
			message = message + "\n\n" + word.getDef() + " — ";
		}
		if (getPlayAudio() == index) {
			word.play();
		}
		if (!message.equals("")) {
			editedMessage = new StringBuilder(message);
			for (int j = 1; j <= 3; j++) {
				editedMessage.deleteCharAt(message.length() - j);
			}
			return convertMsg(editedMessage.toString());
		}
		return "";
	}
	
	public void start(ArrayList<Word> words)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		Collections.shuffle(words);
		String input = "";
		String answer;
		Word word;
		String message;
		String mistakeLines = "";
		File mistakesFile = new File("log/mistakes.txt");
		boolean mistakesExists = mistakesFile.exists();
		
		for (int i = 0; i < words.size(); i++) {
			word = words.get(i);
			message = configure(word, 1);
			input = JOptionPane.showInputDialog(null, message).strip().toLowerCase();
			answer = word.getEn().strip().toLowerCase();
			
			if (input == null) {
				
			}
			else if (input.equals("/exit")) {
				break;
			}
			else {
				if (input.equals(answer)) {
					configure(word, 2);
					continue;
				}
				else if (input.equals("/en")) {
					showElement(word.getEn());
					i--;
					continue;
				}
				else if (input.equals("/ru")) {
					showElement(word.getRu());
					i--;
					continue;
				}
				else if (input.equals("/def")) {
					showElement(word.getDef());
					i--;
					continue;
				}
				else if (input.equals("/au")) {
					word.play();
					i--;
					continue;
				}
				
				
				else if (input.charAt(0) == '/') {
					JOptionPane.showMessageDialog(null, "Invalid command, bro :o");
					i--;
					continue;
				}
				else {
					if (mistakesExists) {
						mistakeLines = mistakeLines + answer + ":\t" + input + "\n";
					}
					JOptionPane.showMessageDialog(null, "Incorrect :(");
					i--;
					continue;
				}
			}
		}
		if (mistakesExists && !mistakeLines.strip().equals("")) {
			mistakeLines = "\n" + mistakeLines.substring(0, mistakeLines.length() - 1);
			FileWriter writer = new FileWriter(mistakesFile, true);
			writer.write(mistakeLines);
			writer.close();
		}
		
		String[] options = {"Done", "Restart"};
		int option = JOptionPane.showOptionDialog(null, "Done!\nWanna do it again? :D",
                ":O",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
		switch (option) {
			case -1:
				System.exit(0);
				break;
			case 1:
				start(words);
				break;
		}
	}

}
