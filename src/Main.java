import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

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
				
				pathWord = "files/audio/(" + index + ").wav";
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
		
		//__________________________________________________________
		String input = "";
		int answer = 228;
		while(true) {
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
					input = JOptionPane.showInputDialog(null, "Time to select the range of words we're gonna learn today.\n/help for instructions.\nSelect the FIRST word of a range.");
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
					default:
						try {
							int first = Integer.parseInt(input);
							input = JOptionPane.showInputDialog(null, "Select the LAST word of a range.\nmax stands for the biggest number you can choose.");
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
							"Learn w both", "Learn quick", "Learn w audio"};
					answer = JOptionPane.showOptionDialog(null, "Select a mode\n/help for the list of modes",
			                ":O",
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options[1]);
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
					}
				}
				break;
			
			}
		}
	}
}
