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

	public GhostBrain() {
		blackBoard.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// iterate through tableentry and set companion message
		HashMap<Integer, TableEntry> table = ((BlackBoard) o).table;
		setTotalCount(table.size());
		setTotalCorrect(0);
		for (Map.Entry<Integer, TableEntry> entry : table.entrySet()) {
			if (entry.getValue().isCorrect) {
				setTotalCorrect(getTotalCorrect() + 1);
			}
		}
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
