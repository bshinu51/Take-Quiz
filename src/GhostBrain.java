import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class GhostBrain implements Observer {
	BlackBoard blackBoard = BlackBoard.getInstance();
	private int totalCorrect = 0;
	private int totalCount = 0;
	Temprature temp = new Temprature();
	private boolean hasChanged;
	public Object mLock = new Object();
	private String companionMessage = "";

	private String greetingMessage = null;
	Calendar time = Calendar.getInstance();
	int hour;

	String correctnessMessage = null;
	public final static String[] correcFeedbacks = { "you have chosen wisly", "good job!", "Awsome job", "you are right on", "you are correct"};
	public final static String[] incorrectFeedbacks = {"you have chosen poorly", "you can do better", "you need more practice", "you are wrong", "think more about this one"};
	private boolean isCurrentAnswerCorrect;
	Random rand = new Random();


	public GhostBrain() {
		blackBoard.addObserver(this);
	}

	public void updateCompanionMessage() {
		this.companionMessage = getGreeingMessage()
				+ ", " + getCorrectnessMessage()
			    + "! ";
	}
	public void setCompanionMessage(String companionMessage) {
		this.companionMessage = companionMessage;
	}
	public String getCompanionMessage() {
		return this.companionMessage;
	}
	
	
	private String getGreeingMessage() {
    	hour = time.get(Calendar.HOUR_OF_DAY);
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
		
		if(isCurrentAnswerCorrect)
			correctnessMessage = correcFeedbacks[(int) (n%5)];
		else
			correctnessMessage= incorrectFeedbacks[(int) (n%5)];
		
		return correctnessMessage;
	}


	@Override
	public void update(Observable o, Object arg) {
		// iterate through table entry and set companion message
		HashMap<Integer, TableEntry> table = ((BlackBoard) o).table;
		setTotalCount(table.size());
		setTotalCorrect(0);
		for (Map.Entry<Integer, TableEntry> entry : table.entrySet()) {
			if (entry.getValue().isCorrect) {
				setTotalCorrect(getTotalCorrect() + 1);
			}
		}
		// Is the answered question correct
		isCurrentAnswerCorrect = table.get(arg).getEntryCorrectness();
		System.out.println("Your answer is " + isCurrentAnswerCorrect);
		
		//Update CompanionMessage
		System.out.println("Update CompanionMessage");
		updateCompanionMessage();

		
		synchronized (mLock) {
			setHasChanged(true);
		}
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	private void setTotalCorrect(int totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public int getTotalCount() {
		return totalCount;
	}

	private void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getTemperature() {
		return temp.getTemp();
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
