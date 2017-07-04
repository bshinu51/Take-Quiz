import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class BlackBoard extends Observable implements Observer {
	private static BlackBoard sInstance = null;
	HashMap<Integer, TableEntry> table = new HashMap<Integer, TableEntry>();

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
			String timeTaken = null;// = ((Companion) o);
			if (table.containsKey(index))
				table.get(index).updateEntry(index, isCorrect, timeTaken);
			else
				table.put(index, new TableEntry(index, isCorrect, timeTaken));
			((ExamView) o).clearChanged();
			setChanged();
			notifyObservers();
		}
	}
}
