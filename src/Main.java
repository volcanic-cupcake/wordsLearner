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
				
				pathWord = "files/audio/" + index + ".wav";
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
						JOptionPane.showMessageDialog(null, "range settings: bla-bla-bla");
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
		String wordOfAudio;
		for (int i = 0; i < words.size(); i++) {
			for (String fname : fnames) {
				wordOfAudio = fname.substring(0, fname.length() - 11).strip();
				word = words.get(i);
				if (word.equals(wordOfAudio)) {
					foundIndexes.add(i);
					File wordFile = new File(path + "/" + fname);
					String newPath = path + "/" + (firstIndex + i) + ".mp3";
					wordFile.renameTo(new File(newPath));
				}
			}
		}
		for (int i : foundIndexes) {
			words.remove(i);
		}
		if (!foundIndexes.isEmpty()) {
			String text = "";
			for (String line : words) {
				text += line + "\n";
			}
			text = text.substring(0, text.length() - 1);
			writeFile(new File("files/storage/findAudio.txt"), text);
		}
	}
}
