import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
	public void setLetShowingAll(boolean letShowingAll) {
		this.letShowingAll = letShowingAll;
	}
	
	public String configureMessage(Word word) {
		StringBuilder editedMessage;
		String message = "";
		if (getShowEnglish()) {
			message = message + word.getEn() + " — ";
		}
		if (getShowRussian()) {
			message = message + word.getRu() + " : ";
		}
		if (getShowDefinition()) {
			message = message + "\n\n" + word.getDef() + " — ";
		}
		
		editedMessage = new StringBuilder(message);
		for (int j = 1; j <= 3; j++) {
			editedMessage.deleteCharAt(message.length() - j);
		}
		return convertMsg(editedMessage.toString());
	}
	
	public void callPages(String messageSet) {
		String messageLine = "";
		ArrayList<String> messagePages = new ArrayList<String>();
		Scanner scanner = new Scanner(messageSet);
		int count = 0;
		while(scanner.hasNextLine()) {
			count++;
			messageLine = messageLine + '\n' + scanner.nextLine();
			if (count == 42) {
				messagePages.add(messageLine);
				messageLine = "";
				count = 0;
			}
		}
		scanner.close();
		
		int pageNum = messagePages.size();
		if (pageNum != 0 && !messageLine.equals("")) {
			//add last message line when it's not full
			messagePages.add(messageLine);
		}
		pageNum = messagePages.size();
		if (pageNum < 2) {
			JOptionPane.showMessageDialog(null, messageLine);
		}
		else {
			String pageInput;
			String pageMsg;
			for (int i = 0; i < pageNum; i++) {
				pageMsg = "Page " + Integer.toString(i + 1) + '/' + Integer.toString(pageNum) + "\n\n" + messagePages.get(i);
				pageInput = JOptionPane.showInputDialog(null, pageMsg);
				if (pageInput == null) {
					if (i > 0) {
						i = i - 2;
					}
					else {
						i--;
					}
					continue;
				}
				else if (pageInput.equals("/exit")) {
					break;
				}
			}
		}
	}
	@Override
	public void start(ArrayList<Word> words) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		Collections.shuffle(words);
		String input = "";
		Word word;
		String message;
		String messageSet = "Noice! Here are your words:\n\n";
		for (int i = 0; i < words.size(); i++) {
			word = words.get(i);
			if (getPlayAudio()) {
				words.get(i).play();
			}
			
			message = configureMessage(word);
			input = JOptionPane.showInputDialog(null, message);
			if (input == null) {
				if (i > 0) {
					i = i - 2;
				}
				else {
					i--;
				}
				continue;
			}
			else if (input.equals("/exit")) {
				break;
			}
			else {
				if (getLetBuildSentences()) {
					word.setSentence(input);
				}
			}
		}
		
		//showing all
		for (Word word1 : words) {
			messageSet = messageSet + configureMessage(word1) + "\n__________________________________________________\n";
		}
		callPages(messageSet);
		
		//built sentences
		ArrayList<String> lines = new ArrayList<String>();
		File sentenceFile = new File("log/builtWords.txt");
		boolean builtWordsExists = sentenceFile.exists();
		if (builtWordsExists) {
			Scanner scanner = new Scanner(sentenceFile);
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
			scanner.close();
		}
		messageSet = "";
		String sentence;
		String line;
		String pureWord;
		int lineLength;
		int linesSize = lines.size();
		boolean wordDoesntExist;
		for (Word word1 : words) {
			sentence = word1.getSentence();
			//this part is for saving new built words in a .txt
			if (builtWordsExists && !sentence.strip().equals("")) {
				wordDoesntExist = true;
				innerloop:
				for (int i = 0; i < linesSize; i++) {
					line = lines.get(i);
					lineLength = line.length();
					pureWord = line.substring(0, lineLength - 1);
					if (pureWord.equals( word1.getEn())) {
						lines.set(i, line + "\n" + "\t" + sentence);
						wordDoesntExist = false;
						break innerloop;
					}
				}
				if (wordDoesntExist) {
					lines.add(word1.getEn() + ":" + "\n" + "\t" + sentence);
				}
			}
			
			//this part is for showing the user current built words
			if (!sentence.strip().equals("")) {
				message = word1.getEn() + " :\n" + sentence;
				message = convertMsg(message);
				messageSet = messageSet + message + "\n__________________________________________________\n"; 
			}
		}
		//this part is for saving new built words in a .txt
		if (builtWordsExists) {
			String updatedLines = "";
			for (String line1 : lines) {
				updatedLines = updatedLines + line1 + "\n";
			}
			updatedLines = updatedLines.substring(0, updatedLines.length() - 1);
			FileWriter writer = new FileWriter(sentenceFile);
			writer.write(updatedLines);
			writer.close();
		}
		//this part is for showing the user current built words
		if (!messageSet.equals("")) {
			messageSet = "Here are sentences you built:\n\n" + messageSet;
			callPages(messageSet);
		}
	}
}
