import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class Word {
	private static int number;
	private String sentence;
	private String en;
	private String ru;
	private String def;
	private File audio;
	
	public Word(String en, String ru, String def, String pronPath) {
		number++;
		setEn(nullIfEmpty(en));
		setRu(nullIfEmpty(ru));
		setDef(nullIfEmpty(def));
		setAudio(pathToFile(pronPath));
	}
	
	public static int getNumber() {
		return number;
	}
	public String getSentence() {
		return this.sentence;
	}
	public String getEn() {
		return this.en;
	}
	public String getRu() {
		return this.ru;
	}
	public String getDef() {
		return this.def;
	}
	public File getAudio() {
		return this.audio;
	}
	//------------------------------------------
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public void setRu(String ru) {
		this.ru = ru;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public void setAudio(File audio) {
		this.audio = audio;
	}
	//------------------------------------------
	public String nullIfEmpty(String string) {
		if (string.replace(" ", "").equals("")) {
			string = null;
		}
		return string;
	}
	
	public File pathToFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file = null;
		}
		return file;
	}
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		if (this.audio != null) {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.audio);
			Clip clip = AudioSystem.getClip();
			try {
				clip.open(audioStream);
				clip.start();
			}
			catch (LineUnavailableException e) {
				JOptionPane.showMessageDialog(null, "Please close other instances of this program");
				System.exit(0);
			}
		}
	}
	public void play(boolean sleepUntilFinished) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		if (this.audio != null) {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.audio);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			if (sleepUntilFinished) {
				while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
				{
				}
			}
		}
	}
}
