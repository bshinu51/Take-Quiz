package com.cse360.quiz.model.storagemanager;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import com.cse360.quiz.view.ExamView;

public class BlackBoard extends Observable implements Observer {
	private static BlackBoard sInstance = null;
	private HashMap<Integer, TableEntry> table = new HashMap<Integer, TableEntry>();

	private BlackBoard() {

	}

	public static BlackBoard getInstance() {
		if (sInstance == null)
			sInstance = new BlackBoard();
		return sInstance;
	}

	@Override
	public void update(Observable o, Object arg) {
		if ((boolean) arg) {
			int index = ((ExamView) o).getQuestionIndex();
			boolean isCorrect = ((ExamView) o).isCorrect(index);
			String timeTaken = ((ExamView) o).getTimeTaken();
			if (getTable().containsKey(index))
				getTable().get(index).updateEntry(index, isCorrect, timeTaken);
			else
				getTable().put(index,
						new TableEntry(index, isCorrect, timeTaken));
			((ExamView) o).clearChanged();
			setChanged();
			notifyObservers(index);
		}
	}

	public HashMap<Integer, TableEntry> getTable() {
		return table;
	}
}
