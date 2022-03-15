import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import java.util.Date;

public class Main {
	static final String AUDIO_EXTENSION = ".mp3";
	
	static HashMap<String, String> LOG = new HashMap<String, String>();
	static HashMap<String, String> STORAGE = new HashMap<String, String>();
	static HashMap<String, String> LAB = new HashMap<String, String>();
	static HashMap<String, String> BRAIN = new HashMap<String, String>();
	
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		final String LOG_PATH = "log/";
		LOG.put("mistakes", LOG_PATH + "mistakes.txt");
		LOG.put("builtWords", LOG_PATH + "builtWords.txt");
		LOG.put("reminders", LOG_PATH + "reminders.txt");
		
		final String STORAGE_PATH = "files/storage/";
		STORAGE.put("audioDir", STORAGE_PATH + "audio/");
		STORAGE.put("shuffledAudioDir", STORAGE_PATH + "shuffledAudio/");
		STORAGE.put("storage", STORAGE_PATH + "storage.txt");
		STORAGE.put("readyToGo", STORAGE_PATH + "readyToGo.txt");
		STORAGE.put("findAudio", STORAGE_PATH + "findAudio.txt");
		
		final String LAB_PATH = "files/lab/";
		LAB.put("audioDir", LAB_PATH + "audio/");
		LAB.put("words", LAB_PATH + "labWords.txt");
		LAB.put("separateEnglish", LAB_PATH + "separateTxt/english.txt");
		LAB.put("separateRussian", LAB_PATH + "separateTxt/russian.txt");
		LAB.put("separateDefinitions", LAB_PATH + "separateTxt/definitions.txt");
		
		final String BRAIN_PATH = "files/brain/";
		BRAIN.put("audioDir", BRAIN_PATH + "audio/");
		BRAIN.put("words", BRAIN_PATH + "brainWords.txt");
		BRAIN.put("separateEnglish", BRAIN_PATH + "separateTxt/english.txt");
		BRAIN.put("separateRussian", BRAIN_PATH + "separateTxt/russian.txt");
		BRAIN.put("separateDefinitions", BRAIN_PATH + "separateTxt/definitions.txt");
		
		ArrayList<Word> labWords = pullWords(new File(LAB.get("words")), LAB.get("audioDir"));
		ArrayList<Word> brainWords = pullWords(new File(BRAIN.get("words")), BRAIN.get("audioDir"));
		ArrayList<Word> storageWords = pullWords(new File(STORAGE.get("readyToGo")), STORAGE.get("audioDir"));
		
		ArrayList<Word> chosenWords = new ArrayList<Word>();
		
		String wordsAvailable = "Words in brain: " + brainWords.size();
		wordsAvailable += "\n" + "Ready to go words: " + storageWords.size();
		wordsAvailable += "\n" + "Words in Lab: " + labWords.size();
		wordsAvailable += "\n\n" + "Total: " + Word.getNumber();
		//__________________________________________________________
		String input = "";
		int answer = 228;
		while(true) {
			chosenWords.clear(); //test
			boolean setUp1 = true;
			String[] options = {"start", "help"};
			answer = JOptionPane.showOptionDialog(null, "Hello!\nClick \"help\" for the commands",
	                ":D",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			switch (answer) {
			case -1:
				System.exit(0);
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "Commands: alalalalla");
				break;
			case 0:

				rangeLoop:
				while(setUp1) {
					input = JOptionPane.showInputDialog(null, "Time to select the range of words we're gonna learn today.\n/help for instructions.\nSelect the FIRST word of a range.\n\n" + wordsAvailable);
					if (input == null) {
						setUp1 = false;
						break rangeLoop;
					}
					
					File singleLabFile = new File(LAB.get("words"));
					File englishLabFile = new File(LAB.get("separateEnglish")); 
					File russianLabFile = new File(LAB.get("separateRussian")); 
					File definitionsLabFile = new File(LAB.get("separateDefinitions"));
					
					File singleBrainFile = new File(BRAIN.get("words"));
					File englishBrainFile = new File(BRAIN.get("separateEnglish")); 
					File russianBrainFile = new File(BRAIN.get("separateRussian")); 
					File definitionsBrainFile = new File(BRAIN.get("separateDefinitions"));
					
					switch(input.toLowerCase()) {
					case "/help":
						String helpCommands = "/single to separate lab" + "\n";
						helpCommands += "/separate to single lab" + "\n\n";
						helpCommands += "/single to separate brain" + "\n";
						helpCommands += "/separate to single brain" + "\n\n\n";
						helpCommands += "/storage to find" + "\n";
						helpCommands += "/give list" + "\n";
						helpCommands += "/rename audios" + "\n";
						helpCommands += "/shuffle ready to go" + "\n";
						JOptionPane.showMessageDialog(null, helpCommands);
						break;
					case "/exit":
						System.exit(0);
						break;
					case "/back":
						setUp1 = false;
						break rangeLoop;
					case "/single to separate lab":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						singleToSeparate(singleLabFile, englishLabFile, russianLabFile, definitionsLabFile);
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/separate to single lab":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						separateToSingle(singleLabFile, englishLabFile, russianLabFile, definitionsLabFile);
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/single to separate brain":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						singleToSeparate(singleBrainFile, englishBrainFile, russianBrainFile, definitionsBrainFile);
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/separate to single brain":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						separateToSingle(singleBrainFile, englishBrainFile, russianBrainFile, definitionsBrainFile);
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/storage to find":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						storageToFind();
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/rename audios":
						String input1337 = JOptionPane.showInputDialog(null, "Enter first index");
						int input1338 = Integer.parseInt(input1337);
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						renameAudios(input1338);
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/shuffle ready to go":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						shuffleReadyToGo();
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/give list":
						String output = "";
						String temp;
						Scanner deezScanner = new Scanner(new File(STORAGE.get("findAudio")));
						while(deezScanner.hasNextLine()) {
							temp = deezScanner.nextLine();
							output = output + "\"" + temp.strip() + "\" ";
						}
						output = output.substring(0, output.length() - 1);
						System.out.println(output);
						System.exit(0);
						break;
					case "/time to learn":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						timeToLearn(labWords, brainWords, storageWords);
						JOptionPane.showMessageDialog(null, "Done :)");
						System.exit(0);
						break;
					default:
						try {
							int first = Integer.parseInt(input);
							input = JOptionPane.showInputDialog(null, "Select the LAST word of a range.\nmax stands for the biggest number you can choose.\n\n" + wordsAvailable);
							if (input == null) {
								setUp1 = false;
								break rangeLoop;
							}
							int last;
							try {
								last = Integer.parseInt(input);
								if (first < 1 || last < 1) {
									JOptionPane.showMessageDialog(null, "Some of your indexes is less than 1. You can't do that.");
								}
								else if (first > last) {
									JOptionPane.showMessageDialog(null, "First index can't be more than last bro!");
								}
								else {
									Collections.reverse(labWords);
									try {
										for (int i = first - 1; i < last; i++) {
											chosenWords.add(labWords.get(i));
										}
										Collections.reverse(labWords);									
										break rangeLoop;
									}
									catch (IndexOutOfBoundsException e) {
										JOptionPane.showMessageDialog(null, "Index out of range: please enter existing indexes.");
									}
								}
							}
							catch (NumberFormatException e) {
								if (input.toLowerCase().strip().equals("max")) {
									last = labWords.size();
									if (first < 1 || last < 1) {
										JOptionPane.showMessageDialog(null, "Some of your indexes is less than 1. You can't do that.");
									}
									else if (first > last) {
										JOptionPane.showMessageDialog(null, "First index can't be more than last bro!");
									}
									else {
										Collections.reverse(labWords);
										try {
											for (int i = first - 1; i < last; i++) {
												chosenWords.add(labWords.get(i));
											}
											Collections.reverse(labWords);									
											break rangeLoop;
										}
										catch (IndexOutOfBoundsException e2) {
											JOptionPane.showMessageDialog(null, "Index out of range: please enter existing indexes.");
										}
									}
								}
								JOptionPane.showMessageDialog(null, "Better enter a number bro!");
							}
						}
						catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Better enter a number bro!");
						}
						
					}
				}
				
				modeLoop:
				while (setUp1) {
					String[] options2 = {"go back", "help", "Learn w translation", "Learn w definition",
							"Learn w both", "Learn quick", "Learn w audio", "Test modes"};
					answer = JOptionPane.showOptionDialog(null, "Select a mode\n/help for the list of modes",
			                ":O",
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[7]);
					Mode mode;
					switch (answer) {
					case -1:
						System.exit(0);
						break;
					case 0:
						setUp1 = false;
						break modeLoop;
					case 1:
						JOptionPane.showMessageDialog(null, "List of modes: bla-bla-bla");
						break;
					case 2:
						mode = new Learn(true, true, false, true, true, true);
						mode.start(chosenWords);
						break;
					case 3:
						mode = new Learn(true, false, true, true, true, true);
						mode.start(chosenWords);
						break;
					case 4:
						mode = new Learn(true, true, true, true, true, true);
						mode.start(chosenWords);
						break;
					case 5:
						mode = new Learn(true, false, false, true, true, true);
						mode.start(chosenWords);
						break;
					case 6:
						mode = new Learn(false, false, false, true, true, true);
						mode.start(chosenWords);
						break;
					case 7:
						testModesGUI(chosenWords);
						break;
					}
				}
				break;
			
			}
		}
	}
	public static ArrayList<Word> pullWords(File wordsFile, String audioDir) throws FileNotFoundException {
		Scanner scanner = new Scanner(wordsFile);
		int counter = 1;
		int index = 1;
		String line;
		ArrayList<Word> words = new ArrayList<Word>();
		String engWord = "";
		String rusWord = "";
		String defWord = "";
		String pathWord;
		Word word;
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (counter == 1) {
				engWord = line;
			}
			else if (counter == 2) {
				rusWord = line;
			}
			else if (counter == 3) {
				defWord = line;
				counter = 0;
				
				pathWord = audioDir + index + AUDIO_EXTENSION;
				index++;
				word = new Word(engWord, rusWord, defWord, pathWord);
				words.add(word);
			}
			else {
				System.out.println("something went wrong");
			}
			counter++;
		}
		scanner.close();
		return words;
	}
	public static void testModesGUI(ArrayList<Word> chosenWords) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		int answer = 0;
		String[] options = {"learn modes", "help", "Test w tranlation", "Test w definition", "Test w both", "Test spelling"};
		answer = JOptionPane.showOptionDialog(null, "Select a mode\n/help for the list of modes",
                ":D",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		Mode mode;
		switch (answer) {
		case -1:
			System.exit(0);
			break;
		case 0:
			break;
		case 1:
			JOptionPane.showMessageDialog(null, "List of modes: bla-bla-bla");
			break;
		case 2:
			mode = new Test(0, 1, 0, 2);
			mode.start(chosenWords);
			break;
		case 3:
			mode = new Test(0, 0, 1, 2);
			mode.start(chosenWords);
			break;
		case 4:
			mode = new Test(0, 1, 1, 2);
			mode.start(chosenWords);
			break;
		case 5:
			mode = new Test(0, 0, 0, 1);
			mode.start(chosenWords);
			break;
		}
	}
	
	public static void backUpFiles() throws IOException {
	    File source = new File("files");
	    File destination;
	    int counter = 1;
	    while (counter != 0) {
	    	destination = new File("backUp/" + counter + "/files");
	    	if (!destination.exists()) {
	    		FileUtils.copyDirectory(source, destination);
	    		counter = 0;
	    	} else {
	    		counter++;
	    	}
	    }
	}
	
	public static ArrayList<String> readFile(File file) throws FileNotFoundException {
		ArrayList<String> content = new ArrayList<String>();
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			content.add(scanner.nextLine());
		}
		scanner.close();
		return content;
	}
	public static void writeFile(File file, String content) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}
	public static void writeFile(File file, String content, boolean aye) throws IOException {
		FileWriter writer = new FileWriter(file, true);
		writer.write(content);
		writer.close();
	}
	public static void separateToSingle(File singleFile, File englishFile, File russianFile, File definitionsFile) throws IOException {
		
		boolean exist = singleFile.exists() && englishFile.exists() && russianFile.exists() && definitionsFile.exists();
		
		if (exist) {
			ArrayList<String> english = readFile(englishFile);
			ArrayList<String> russian = readFile(russianFile);
			ArrayList<String> definitions = readFile(definitionsFile);
			
			String addedLines = "";
			for (int i = 0; i < english.size(); i++) {
				addedLines += english.get(i) + "\n";
				try {
					addedLines += russian.get(i) + "\n";
				}
				catch (IndexOutOfBoundsException e) {}
				
				try {
					addedLines += definitions.get(i) + "\n";
				}
				catch (IndexOutOfBoundsException e) {}
			}
			addedLines = addedLines.substring(0, addedLines.length() - 1);
			writeFile(singleFile, addedLines);
		}
		else {
			JOptionPane.showMessageDialog(null, "Some file is missing");
		}
	}
	public static void singleToSeparate(File singleFile, File englishFile, File russianFile, File definitionsFile) throws IOException {
		
		boolean exist = singleFile.exists() && englishFile.exists() && russianFile.exists() && definitionsFile.exists();
		
		if (exist) {
			String english = "";
			String russian = "";
			String definitions = "";
			ArrayList<String> single = readFile(singleFile);
			int counter = 0;
			for (String line : single) {
				switch (counter) {
				case 0:
					english += line + "\n";
					counter++;
					break;
				case 1:
					russian += line + "\n";
					counter++;
					break;
				case 2:
					definitions += line + "\n";
					counter = 0;
					break;
				}
			}
			
			english = english.substring(0, english.length() - 1);
			russian = russian.substring(0, russian.length() - 1);
			definitions = definitions.substring(0, definitions.length() - 1);
			
			writeFile(englishFile, english);
			writeFile(russianFile, russian);
			writeFile(definitionsFile, definitions);
		}
		else {
			JOptionPane.showMessageDialog(null, "Some file is missing");
		}
	}
	
	public static void storageToFind() throws IOException {
		
		File singleFile = new File(STORAGE.get("storage"));
		File englishFile = new File(STORAGE.get("findAudio")); 
		boolean exist = singleFile.exists() && englishFile.exists();
		
		if (exist) {
			String english = "";
			ArrayList<String> single = readFile(singleFile);
			int counter = 0;
			for (String line : single) {
				switch (counter) {
				case 0:
					english += line + "\n";
					counter++;
					break;
				case 1:
					counter++;
					break;
				case 2:
					counter = 0;
					break;
				}
			}
			
			english = english.substring(0, english.length() - 1);
			
			writeFile(englishFile, english);
		}
		else {
			JOptionPane.showMessageDialog(null, "Some file is missing");
		}
	}
	public static void renameAudios(int firstIndex) throws IOException {
		String path = STORAGE.get("audioDir");
		File audiodir = new File(path);
		String[] fnames = audiodir.list();
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Integer> foundIndexes = new ArrayList<Integer>();
		
		Scanner scanner = new Scanner(new File(STORAGE.get("findAudio")));
		while(scanner.hasNextLine()) {
			words.add(scanner.nextLine().strip());
		}
		scanner.close();
		String word;
		String wordOfAudio = "";
		String wordOfAudio2 = "";
		int counter = 0;
		for (int i = 0; i < words.size(); i++) {
			for (String fname : fnames) {
				try {
					wordOfAudio = fname.substring(0, fname.length() - 11).strip();
				}
				catch (StringIndexOutOfBoundsException e) {}
				
				try {
					wordOfAudio2 = fname.substring(0, fname.length() - 4).strip();
				}
				catch (StringIndexOutOfBoundsException e) {}
				
				word = words.get(i);
				if (word.equals(wordOfAudio) || word.equals(wordOfAudio2)) {
					foundIndexes.add(i);
					File wordFile = new File(path + fname);
					String newPath = path + (firstIndex + counter) + AUDIO_EXTENSION;
					counter++;
					wordFile.renameTo(new File(newPath));
				}
			}
		}
		Collections.reverse(foundIndexes);
		for (int i : foundIndexes) {
			words.remove(i);
		}
		if (!foundIndexes.isEmpty()) {
			String text = "";
			for (String line : words) {
				text += line + "\n";
			}
			try {
				text = text.substring(0, text.length() - 1);
			}
			catch (StringIndexOutOfBoundsException e) {}
			writeFile(new File(STORAGE.get("findAudio")), text);
			
			String readyToGo = "";
			String storage = "";
			ArrayList<String> storageLines = readFile(new File (STORAGE.get("storage")));
			ArrayList<String> newStorageLines = readFile(new File (STORAGE.get("storage")));
			ArrayList<Integer> changesStorage = new ArrayList<Integer>();
			int eng;
			int rus;
			int def;
			Collections.reverse(foundIndexes);
			System.out.println(foundIndexes); //shit
			for (int i : foundIndexes) {
				eng = (i + 1) * 3 - 2;
				rus = (i + 1) * 3 - 1;
				def = (i + 1) * 3;
				
				eng--;
				rus--;
				def--;
				
				System.out.println(i); //shit
				System.out.println(eng); //shit
				changesStorage.add(eng);
				changesStorage.add(rus);
				changesStorage.add(def);
				
				readyToGo += storageLines.get(eng) + "\n";
				readyToGo += storageLines.get(rus) + "\n";
				readyToGo += storageLines.get(def) + "\n";
			}
			try {
				readyToGo = "\n" + readyToGo.substring(0, readyToGo.length() - 1);
			}
			catch (StringIndexOutOfBoundsException e) {}
			Collections.reverse(changesStorage);
			for (int i : changesStorage) {
				newStorageLines.remove(i);
			}
			for (String newStorageLine : newStorageLines) {
				storage += newStorageLine + "\n";
			}
			try {
				storage = storage.substring(0, storage.length() - 1);
			}
			catch (StringIndexOutOfBoundsException e) {}
			writeFile(new File(STORAGE.get("readyToGo")), readyToGo, true);
			writeFile(new File(STORAGE.get("storage")), storage);
		}
	}
	
	public static void shuffleReadyToGo() throws IOException {
		ArrayList<String> lines = readFile(new File(STORAGE.get("readyToGo")));
		ArrayList<Word> words = new ArrayList<Word>();
		String engWord = "";
		String rusWord = "";
		String defWord = "";
		int counter = 0;
		
		//File audiodir = new File("files/storage/audio");
		//String[] audioNames = audiodir.list();
		int inc = 0;
		boolean stop = true;
		while (stop) {
			inc++;
			File file = new File(STORAGE.get("audioDir") + inc + AUDIO_EXTENSION);
			if (file.exists()) {
				stop = false;
			}
		}
		
		String line = "";
		int wordNum = 0;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			switch (counter) {
			case 0:
				engWord = line;
				counter++;
				break;
			case 1:
				rusWord = line;
				counter++;
				break;
			case 2:
				defWord = line;
				words.add(new Word(engWord, rusWord, defWord, STORAGE.get("audioDir") + (wordNum + inc) + AUDIO_EXTENSION, false));
				wordNum++;
				counter = 0;
				break;
			}
		}
		Collections.shuffle(words);
		String update = "";
		for (int i = 0; i < words.size(); i++) {
			Word word = words.get(i);
			update += word.getEn() + "\n";
			update += word.getRu() + "\n";
			update += word.getDef() + "\n";
			
			System.out.println(i + " Old name: " + word.getAudio()); //shit
			
			File oldName = new File(word.getAudio());
			if (oldName.exists()) {
				System.out.println(i + " New name: " + "files/storage/shuffledAudio/" + (i + inc) + ".mp3"); //shit
				File newName = new File(STORAGE.get("shuffledAudioDir") + (i + inc) + AUDIO_EXTENSION);
				oldName.renameTo(newName);
			}
		}
		try {
			update = update.substring(0, update.length() - 1);
		}
		catch (StringIndexOutOfBoundsException e) {}
		writeFile(new File(STORAGE.get("readyToGo")), update);
	}
	public static void timeToLearn(ArrayList<Word> labWords, ArrayList<Word> brainWords, ArrayList<Word> storageWords) throws IOException {
		int num = Integer.parseInt(JOptionPane.showInputDialog(null, "How many words are we learning today?"));
		int storageNum = storageWords.size();
		if (num <= storageNum) {
			int brainNum;
			int newIndex;
			File oldAudioPath;
			File newAudioPath;
			for (Word labWord : labWords) {
				brainNum = brainWords.size();
				newIndex = brainNum + 1;
				
				brainWords.add(labWord);
				oldAudioPath = new File(labWord.getAudio());
				newAudioPath = new File(BRAIN.get("audioDir") + newIndex + AUDIO_EXTENSION);
				oldAudioPath.renameTo(newAudioPath);
			}
			labWords.clear();
			//now we need to write from storage to lab
			int lastStorageIndex;
			for (int i = 0; i < num; i++) {
				lastStorageIndex = storageWords.size() - 1;
				newIndex = i + 1;
				Word lastStorageWord = storageWords.get(lastStorageIndex);
				
				labWords.add(lastStorageWord);
				oldAudioPath = new File(lastStorageWord.getAudio());
				newAudioPath = new File(LAB.get("audioDir") + newIndex + AUDIO_EXTENSION);
				oldAudioPath.renameTo(newAudioPath);
				
				storageWords.remove(lastStorageIndex);
			}
			
			String updatedLab = "";
			String updatedBrain = "";
			String updatedStorage = "";
			for (Word labWord : labWords) {
				updatedLab += labWord.getEn() + "\n";
				updatedLab += labWord.getRu() + "\n";
				updatedLab += labWord.getDef() + "\n";
			}
			for (Word brainWord : brainWords) {
				updatedBrain += brainWord.getEn() + "\n";
				updatedBrain += brainWord.getRu() + "\n";
				updatedBrain += brainWord.getDef() + "\n";
			}
			for (Word storageWord : storageWords) {
				updatedStorage += storageWord.getEn() + "\n";
				updatedStorage += storageWord.getRu() + "\n";
				updatedStorage += storageWord.getDef() + "\n";
			}
			
			try {
				updatedLab = updatedLab.substring(0, updatedLab.length() - 1);
			}
			catch (StringIndexOutOfBoundsException e) {}
			
			try {
				updatedBrain = updatedBrain.substring(0, updatedBrain.length() - 1);
			}
			catch (StringIndexOutOfBoundsException e) {}
			
			try {
				updatedStorage = updatedStorage.substring(0, updatedStorage.length() - 1);
			}
			catch (StringIndexOutOfBoundsException e) {}
			
			writeFile(new File(LAB.get("words")), updatedLab);
			writeFile(new File(BRAIN.get("words")), updatedBrain);
			writeFile(new File(STORAGE.get("readyToGo")), updatedStorage);
		}
		else {
			JOptionPane.showMessageDialog(null, "Man, you should extend your storage for sure");
		}
	}
	
	public static void setReminders(int firstIndex, int lastIndex) throws IOException {
		//after how many minutes those reminders should go (in minutes)
		long[] timePeriods = {20, 1440, 20160, 86400}; //20 min, 1 day, 2 weeks, 2 months
		
		
		File remindersFile = new File(LOG.get("reminders"));
		ArrayList<String> reminders = readFile(remindersFile);
		long minutesNow = new Date().getTime() / 1000 / 60;
		long reminderTime;
		String newReminders = "";
		for (long timePeriod : timePeriods) {
			reminderTime = minutesNow + timePeriod;
			
			newReminders += reminderTime + "\n";
			newReminders += firstIndex + "\n";
			newReminders += lastIndex + "\n";
		}
		
		try {
			newReminders = newReminders.substring(0, newReminders.length() - 1);
		}
		catch (StringIndexOutOfBoundsException e) {}
		
		if (!reminders.isEmpty()) {
			newReminders = "\n" + newReminders;
		}
		writeFile(remindersFile, newReminders, true);
	}
	public static void overrideReminders(ArrayList<Reminder> reminders) throws IOException {
		String updatedReminders = "";
		for (Reminder reminder : reminders) {
			updatedReminders += reminder.getTime() + "\n";
			updatedReminders += reminder.getFirstIndex() + "\n";
			updatedReminders += reminder.getLastIndex() + "\n";
		}
		try {
			updatedReminders = updatedReminders.substring(0, updatedReminders.length() - 1);
		}
		catch (StringIndexOutOfBoundsException e) {}
		
		writeFile(new File(LOG.get("reminders")), updatedReminders);
	}
	
	public static void showReminders() throws FileNotFoundException {
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		File remindersFile = new File(LOG.get("reminders"));
		ArrayList<String> remindersLines = readFile(remindersFile);
		long time = 0;
		int firstIndex = 0;
		int lastIndex = 0;
		int counter = 0;
		
		for (String remindersLine : remindersLines) {
			switch (counter) {
			case 0:
				time = Long.parseLong(remindersLine);
				counter++;
				break;
			case 1:
				firstIndex = Integer.parseInt(remindersLine);
				counter++;
				break;
			case 2:
				lastIndex = Integer.parseInt(remindersLine);
				reminders.add(new Reminder(time, firstIndex, lastIndex));
				counter = 0;
				break;
			}
		}
		// now I just have to:
		// sort reminders by time
		// show reminders that have expired right now && let choose words from the reminder and start learning them rightaway
		// let list all reminders with a command
		// let delete reminders you no more need
		// great success
	}
}
