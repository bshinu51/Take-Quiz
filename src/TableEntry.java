public class TableEntry {

	int index;
	boolean isCorrect;
	String timeTaken;

	public TableEntry(int index, boolean isCorrect, String timeTaken) {
		this.index = index;
		this.isCorrect = isCorrect;
		this.timeTaken = timeTaken;
	}

	public void updateEntry(int index, boolean isCorrect, String timeTaken) {
		this.index = index;
		this.isCorrect = isCorrect;
		this.timeTaken = timeTaken;
	}

}
