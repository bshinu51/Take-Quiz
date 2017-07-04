import javax.swing.JLabel;
import javax.swing.JPanel;

public class GhostPanel extends JPanel implements Runnable {
	JLabel weatherInfo;
	JLabel ghostImage;
	JLabel comapanionMessage;

	public GhostPanel() {
		weatherInfo = new JLabel();
		ghostImage = new JLabel();
		comapanionMessage = new JLabel();
		initialize();
	}

	private void initialize() {
		this.removeAll();
		add(weatherInfo);
		add(ghostImage);
		add(comapanionMessage);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			if (true) {
				weatherInfo.setText("Modify Comapnion here " + i++);
				initialize();
				if (i == 100)
					i = 0;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
	}
}
