package com.cse360.quiz.intelligence;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.cse360.quiz.ExamController;
import com.cse360.quiz.storagemanager.BlackBoard;
import com.cse360.quiz.storagemanager.TableEntry;

public class GhostBrain implements Observer {
	BlackBoard blackBoard = BlackBoard.getInstance();
	private int totalCorrect = 0;
	private int totalCount = 0;
	Temprature temp = new Temprature();
	private boolean hasChanged;
	public Object mLock = new Object();
	Calendar time = Calendar.getInstance();
	int hour;
	boolean isCurrentAnswerCorrect;
	String correctnessMessage = "Let's start...";
	double correctnessPercentage = 0;
	String ghostEmotion = "indiffrent";
	private String timeTaken;

	public GhostBrain() {
		blackBoard.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// iterate through tableentry and set companion message
		isCurrentAnswerCorrect = ((BlackBoard) o).getTable().get(arg)
				.isCorrect();
		timeTaken = ((BlackBoard) o).getTable().get(arg).getTimeTaken();
		HashMap<Integer, TableEntry> table = ((BlackBoard) o).getTable();
		totalCount = table.size();
		totalCorrect = 0;
		for (Map.Entry<Integer, TableEntry> entry : table.entrySet()) {
			if (entry.getValue().isCorrect()) {
				totalCorrect++;
			}
		}
		synchronized (acquireLock()) {
			setHasChanged(true);
		}
	}

	public Object acquireLock() {
		return mLock;
	}

	public String getSystemCurentTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance()
				.getTime());
	}

	public String getCompanionMessage() {
		return getGreetingMessage() + ", "
				+ getCorrectnessMessage(isCurrentAnswerCorrect)
				+ " And you took " + timeTaken + " minutes";
	}

	private String getGreetingMessage() {
		hour = time.get(Calendar.HOUR_OF_DAY);
		String greetingMessage = null;

		if (hour < 12)
			greetingMessage = "Good Morning";
		else if (hour < 17 && !(hour == 12))
			greetingMessage = "Good Afternoon";
		else if (hour == 12)
			greetingMessage = "Good Noon";
		else
			greetingMessage = "Good Evening";
		return greetingMessage;
	}

	/**
	 * public String getTempMessage() { double temperture =
	 * Double.parseDouble(temp.getTemp()); String tempertureMessage = null; if
	 * (temperture < 45) tempertureMessage = "It's cold"; else if (temperture >
	 * 46 && hour > 85) tempertureMessage = "It's nice out there"; else if
	 * (temperture > 86) tempertureMessage = "It's hot out there"; else
	 * tempertureMessage = temp.getTemp();
	 * 
	 * return tempertureMessage; }
	 */
	private String getCorrectnessMessage(boolean isCorrect) {
		if (isCorrect)
			return "You are correct!";
		else
			return "Try again!";
	}

	public String getGhostCharacter() {
		double correct = getTotalCorrect();
		double total = getTotalCount();
		double ratio = correct / total;
		if (total > 0 && ratio < 0.5 && ratio >= 0.3)
			return pickSad(0);
		else if (total > 0 && ratio < 0.3)
			return pickSad(1);
		else if (total > 0 && correct / total >= 0.7 && correct / total < 0.9)
			return pickHappy(0);
		else if (total > 0 && correct / total >= 0.9)
			return pickHappy(1);
		else
			return pickIndiff();
	}

	private String pickIndiff() {
		return ExamController.INDIF_FILE_NAME;

	}

	private String pickHappy(int i) {
		String[] str = { ExamController.HAPPY_FILE_NAME,
				ExamController.HAPPY1_FILE_NAME };
		return str[i % 2];
	}

	private String pickSad(int i) {
		String[] str = { ExamController.SAD_FILE_NAME,
				ExamController.SAD1_FILE_NAME };
		return str[i % 2];
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public String getTemperature() {
		return temp.getTemperature();
	}

	public String getTemperatureMsg() {
		return null;
	}

	public boolean isHasChanged() {
		return hasChanged;
	}

	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
}
