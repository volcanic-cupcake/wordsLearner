import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Learn extends Mode {
	private static int number;
	private boolean showEnglish;
	private boolean showRussian;
	private boolean showDefinition;
	private boolean playAudio;
	private boolean letBuildSentences;
	private boolean letAutoListing;
	private boolean letShowingAll;
	
	public Learn (boolean showEnglish, boolean showRussian, boolean showDefinition, boolean playAudio, boolean letBuildSentences, boolean letAutoListing, boolean letShowingAll) {
		super();
		number++;
		// true for show by default
		// false for show by command
		setShowEnglish(showEnglish);
		setShowRussian(showRussian);
		setShowDefiniton(showDefinition);
		setPlayAudio(playAudio);
		setLetBuildSentences(letBuildSentences);
	}
	
	public static int getNumber() {
		return number;
	}
	public boolean getShowEnglish() {
		return this.showEnglish;
	}
	public boolean getShowRussian() {
		return this.showRussian;
	}
	public boolean getShowDefinition() {
		return this.showDefinition;
	}
	public boolean getPlayAudio() {
		return this.playAudio;
	}
	public boolean getLetBuildSentences() {
		return this.letBuildSentences;
	}
	public boolean getLetAutoListing() {
		return this.letAutoListing;
	}
	public boolean getLetShowingAll() {
		return this.letShowingAll;
	}
	//------------------------------------------
	public void setShowEnglish(boolean showEnglish) {
		this.showEnglish = showEnglish;
	}
	public void setShowRussian(boolean showRussian) {
		this.showRussian = showRussian;
	}
	public void setShowDefiniton(boolean showDefiniton) {
		this.showDefinition = showDefiniton;
	}
	public void setPlayAudio(boolean playAudio) {
		this.playAudio = playAudio;
	}
	public void setLetBuildSentences(boolean letBuildSentences) {
		this.letBuildSentences = letBuildSentences;
	}
	public void setLetAutoListing(boolean letAutoListing) {
		this.letAutoListing = letAutoListing;
	}
	public void setLetShowingAll(boolean letShowingAll) {
		this.letShowingAll = letShowingAll;
	}
	
	@Override
	public void start(ArrayList<Word> words) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		Collections.shuffle(words);
		String input = "";
		String message;
		Word word;
		StringBuilder editedMessage;
		
		for (int i = 0; i < words.size(); i++) {
			message = "";
			word = words.get(i);
			if (getPlayAudio()) {
				words.get(i).play();
			}
			
			if (getShowEnglish()) {
				message = message + word.getEn() + " — ";
			}
			if (getShowRussian()) {
				message = message + word.getRu() + " — ";
			}
			if (getShowDefinition()) {
				message = message + word.getDef() + " — ";
			}
			
			editedMessage = new StringBuilder(message);
			for (int j = 1; j <= 3; j++) {
				editedMessage.deleteCharAt(message.length() - j);
			}
			input = JOptionPane.showInputDialog(null, editedMessage);
			System.out.println(input);
			
			if (input == null) {
				if (i > 0) {
					i = i - 2;
				}
				else {
					i--;
				}
			}
		}
	}
}
