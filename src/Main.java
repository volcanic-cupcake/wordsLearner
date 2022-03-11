import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class Main {
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		File file = new File("files/words.txt");
		Scanner scanner = new Scanner(file);
		int counter = 1;
		int index = 1;
		String line;
		ArrayList<Word> words = new ArrayList<Word>();
		ArrayList<Word> chosenWords = new ArrayList<Word>();
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
				
				pathWord = "files/audio/" + index + ".mp3";
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
		String wordsAvailable = "Words available: " + Integer.toString(Word.getNumber());
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
					switch(input.toLowerCase()) {
					case "/help":
						String helpCommands = "/single to reparate" + "\n";
						helpCommands += "/separate to single" + "\n\n\n";
						helpCommands += "/storage to find" + "\n";
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
					case "/single to separate":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						singleToSeparate();
						JOptionPane.showMessageDialog(null, "Done :)");
						break;
					case "/separate to single":
						backUpFiles();
						JOptionPane.showMessageDialog(null, "back up has been completed");
						separateToSingle();
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
						Scanner deezScanner = new Scanner(new File("files/storage/findAudio.txt"));
						while(deezScanner.hasNextLine()) {
							temp = deezScanner.nextLine();
							output = output + "\"" + temp.strip() + "\" ";
						}
						output = output.substring(0, output.length() - 1);
						System.out.println(output);
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
									Collections.reverse(words);
									try {
										for (int i = first - 1; i < last; i++) {
											chosenWords.add(words.get(i));
										}
										Collections.reverse(words);									
										break rangeLoop;
									}
									catch (IndexOutOfBoundsException e) {
										JOptionPane.showMessageDialog(null, "Index out of range: please enter existing indexes.");
									}
								}
							}
							catch (NumberFormatException e) {
								if (input.toLowerCase().strip().equals("max")) {
									last = words.size();
									if (first < 1 || last < 1) {
										JOptionPane.showMessageDialog(null, "Some of your indexes is less than 1. You can't do that.");
									}
									else if (first > last) {
										JOptionPane.showMessageDialog(null, "First index can't be more than last bro!");
									}
									else {
										Collections.reverse(words);
										try {
											for (int i = first - 1; i < last; i++) {
												chosenWords.add(words.get(i));
											}
											Collections.reverse(words);									
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
	public static void separateToSingle() throws IOException {
		File singleFile = new File("files/words.txt");
		File englishFile = new File("files/separateTxt/english.txt"); 
		File russianFile = new File("files/separateTxt/russian.txt"); 
		File definitionsFile = new File("files/separateTxt/definitions.txt");
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
	public static void singleToSeparate() throws IOException {
		
		File singleFile = new File("files/words.txt");
		File englishFile = new File("files/separateTxt/english.txt"); 
		File russianFile = new File("files/separateTxt/russian.txt"); 
		File definitionsFile = new File("files/separateTxt/definitions.txt");
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
		
		File singleFile = new File("files/storage/storage.txt");
		File englishFile = new File("files/storage/findAudio.txt"); 
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
		String path = "files/storage/audio";
		File audiodir = new File(path);
		String[] fnames = audiodir.list();
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Integer> foundIndexes = new ArrayList<Integer>();
		
		Scanner scanner = new Scanner(new File("files/storage/findAudio.txt"));
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
					File wordFile = new File(path + "/" + fname);
					String newPath = path + "/" + (firstIndex + counter) + ".mp3";
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
			writeFile(new File("files/storage/findAudio.txt"), text);
			
			String readyToGo = "";
			String storage = "";
			ArrayList<String> storageLines = readFile(new File ("files/storage/storage.txt"));
			ArrayList<String> newStorageLines = readFile(new File ("files/storage/storage.txt"));
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
			writeFile(new File("files/storage/readyToGo.txt"), readyToGo, true);
			writeFile(new File("files/storage/storage.txt"), storage);
		}
	}
	
	public static void shuffleReadyToGo() throws IOException {
		ArrayList<String> lines = readFile(new File("files/storage/readyToGo.txt"));
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
			File file = new File("files/storage/audio/" + inc + ".mp3");
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
				words.add(new Word(engWord, rusWord, defWord, "files/storage/audio/" + (wordNum + inc) + ".mp3", false));
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
				System.out.println(i + " New name: " + "files/storage/shuffledAudio/" + (i + 1) + ".mp3"); //shit
				File newName = new File("files/storage/shuffledAudio/" + (i + 1) + ".mp3");
				oldName.renameTo(newName);
			}
		}
		try {
			update = update.substring(0, update.length() - 1);
		}
		catch (StringIndexOutOfBoundsException e) {}
		writeFile(new File("files/storage/readyToGo.txt"), update);
	}
}
