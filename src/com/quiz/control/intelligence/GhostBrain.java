package com.quiz.control.intelligence;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.quiz.model.storagemanager.BlackBoard;
import com.quiz.model.storagemanager.TableEntry;
import com.quiz.view.ExamController;

public class GhostBrain implements Observer {
	BlackBoard blackBoard = BlackBoard.getInstance();
	private int totalCorrect = 0;
	private int totalCount = 0;
	Temprature temp = new Temprature();
	private boolean hasChanged;
	public Object mLock = new Object();

	Calendar time = Calendar.getInstance();
	int hour;
	double correctnessPercentage = 0;
	private String companionMessage = "";
	String ghostEmotion = "indiffrent";
	private String timeTaken;
	String correctnessMessage = null;
	public final static String[] correcFeedbacks = { "you have chosen wisly",
			"good job!", "Awsome job", "you are right on", "you are correct" };
	public final static String[] incorrectFeedbacks = {
			"you have chosen poorly", "you can do better",
			"you need more practice", "you are wrong",
			"think more about this one" };
	private boolean isCurrentAnswerCorrect;
	Random rand = new Random();

	public GhostBrain() {
		blackBoard.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// iterate through table entry and set companion message
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
		updateCompanionMessage();
	}

	public Object acquireLock() {
		return mLock;
	}

	public String getSystemCurentTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance()
				.getTime());
	}

	public void updateCompanionMessage() {
		this.companionMessage = getGreetingMessage() + ", "
				+ getCorrectnessMessage() + "! ";
	}

	public void setCompanionMessage(String companionMessage) {
		this.companionMessage = companionMessage;
	}

	public String getCompanionMessage() {
		return this.companionMessage + " And you took " + timeTaken
				+ " minutes to answer this question";
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

	public String getCorrectnessMessage() {
		double n = Math.random() * 5 + 1;

		if (isCurrentAnswerCorrect)
			correctnessMessage = correcFeedbacks[(int) (n % 5)];
		else
			correctnessMessage = incorrectFeedbacks[(int) (n % 5)];

		return correctnessMessage;
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

	public String getTemperatureMsg() {
		String tmpOpen = "! (Temp: ";
		String t = temp.getTemperature();
		String tmpClose = " F)";
		if (Double.parseDouble(t) > 90)
			return " It's too hot outside" + tmpOpen + t + tmpClose;
		else if (Double.parseDouble(t) < 60)
			return " It's too cold outside" + tmpOpen + t + tmpClose;
		return " It's a romantic weather" + tmpOpen + t + tmpClose;
	}

	public boolean isHasChanged() {
		return hasChanged;
	}

	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
}
