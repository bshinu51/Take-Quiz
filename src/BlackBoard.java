import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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

	public HashMap<Integer, TableEntry> getTable() {
		return table;
	}

	public double calculateTableCorrectness() {
		int correctAnswers = 0;
		for(TableEntry te : table.values()) {
			if(te.getEntryCorrectness())
				correctAnswers++;
	    }
		return correctAnswers*10;
	}

	@Override
	public void update(Observable o, Object arg) {
		if ((boolean) arg) {
			int index = ((ExamView) o).getQuestionIndex();
			boolean isCorrect = ((ExamView) o).isCorrect(index);
			String timeTaken = null;// = ((Companion) o);
			// Since the questin's answer has changed update the table entry
			if (table.containsKey(index))
				table.get(index).updateEntry(index, isCorrect, timeTaken);
			else
				table.put(index, new TableEntry(index, isCorrect, timeTaken));
			((ExamView) o).clearChanged();

			//Marks this as has been changed
			setChanged();
			// notifying the companion which question has changed
			notifyObservers(index);
		}
	}
}
