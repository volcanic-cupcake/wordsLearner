import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
}
