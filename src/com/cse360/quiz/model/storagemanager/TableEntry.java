package com.cse360.quiz.model.storagemanager;

public class TableEntry {

	private int index;
	private boolean isCorrect;
	private String timeTaken;

	public TableEntry(int index, boolean isCorrect, String timeTaken) {
		this.index = index;
		this.isCorrect = isCorrect;
		this.timeTaken = timeTaken;
	}
	
	public boolean getEntryCorrectness() {
		return this.isCorrect;
	}

	public void updateEntry(int index, boolean isCorrect, String timeTaken) {
		this.index = index;
		this.isCorrect = isCorrect;
		this.timeTaken = timeTaken;
	}

	public int getIndex() {
		return index;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public String getTimeTaken() {
		return timeTaken;
	}

}
