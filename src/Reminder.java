
public class Reminder {
	private long time;
	private int firstIndex;
	private int lastIndex;
	
	Reminder(long time, int firstIndex, int lastIndex) {
		setTime(time);
		setFirstIndex(firstIndex);
		setLastIndex(lastIndex);
	}
	
	public long getTime() {
		return this.time;
	}
	public int getFirstIndex() {
		return this.firstIndex;
	}
	public int getLastIndex() {
		return this.lastIndex;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
}
