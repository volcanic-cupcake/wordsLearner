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
}
