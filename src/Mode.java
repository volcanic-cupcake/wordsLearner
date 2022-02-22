import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public abstract class Mode {
	private static int number;
	
	public Mode() {
		number++;
	}
	public static int getNumber() {
		return number;
	}
	public abstract void start(ArrayList<Word> words) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException;
	
	public String convertMsg (String str) {
	    StringBuilder sb = new StringBuilder(str);
	    int num = str.length() / 150;
	    int position = 151;
	    String strTemp;
	    
	    for (int i = 0; i < num; i++) {
	    	strTemp = sb.toString();
	    	innerLoop:
	    	for (int j = position; j > (position - 30); j--) {
	    		if (strTemp.charAt(j) == ' ') {
	    			position = j;
	    			break innerLoop;
	    		}
	    	}
		    sb.insert(position, '\n');
		    position = position + 150;
	    }
	    return sb.toString();
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
	
	public void showElement(String message) {
		callPages(convertMsg(message));
	}
}
