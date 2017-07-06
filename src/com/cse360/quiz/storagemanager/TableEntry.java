package com.cse360.quiz.storagemanager;

public class TableEntry {

	private int index;
	private boolean isCorrect;
	private String timeTaken;

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
