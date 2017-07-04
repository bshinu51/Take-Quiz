import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BlackBoard implements Observer {
	ArrayList<TableEntry> table = new ArrayList<TableEntry>();

	@Override
	public void update(Observable o, Object arg) {
		if (o.hasChanged()) {
			boolean isCorrect = false;// find whether its correct or not
			int index = ((Companion) o).getQuestionIndex();
			String timeTaken = null;// = ((Companion) o);
			if (table.get(index) != null)
				table.set(index, new TableEntry(index, isCorrect, timeTaken));
			else
				table.get(index).updateEntry(index, isCorrect, timeTaken);
		}
	}
}
