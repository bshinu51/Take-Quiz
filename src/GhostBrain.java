import java.util.Observable;
import java.util.Observer;

public class GhostBrain implements Observer {
	BlackBoard blackBoard = BlackBoard.getInstance();

	public GhostBrain() {
		blackBoard.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// iterate through tableentry and set companion message
	}
}
