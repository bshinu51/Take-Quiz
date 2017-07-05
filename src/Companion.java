import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Companion extends JPanel implements Runnable {
	JLabel weatherInfo;
	JLabel ghostImage;
	JLabel companionMessage;
	JLabel clock;
	GroupLayout layout;
	int startTimeMin = 10;
	int startTimeSec = 0;
	private GhostBrain ghostBrain;

	public Companion(GhostBrain ghostBrain) {
		this.ghostBrain = ghostBrain;
		weatherInfo = new JLabel();
		ghostImage = new JLabel(new ImageIcon(new ImageIcon(
				ExamController.GHOST_FILE_NAME).getImage().getScaledInstance(
				30, 30, 0)));
		companionMessage = new JLabel();
		clock = new JLabel();
		layout = new GroupLayout(this);
		setPanelLayout();
		initialize();
	}

	private void initialize() {
		this.removeAll();
		add(weatherInfo);
		add(ghostImage);
		add(companionMessage);
		add(clock);
		this.revalidate();
		this.repaint();
	}

	public void setPanelLayout() {
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(ghostImage)
								.addComponent(clock))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(companionMessage)
								.addComponent(weatherInfo)));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(ghostImage)
								.addComponent(companionMessage))
				.addGroup(
						layout.createParallelGroup().addComponent(clock)
								.addComponent(weatherInfo)));
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			if (true) {
				weatherInfo.setText("Temp: " + ghostBrain.getTemperature());
				if (startTimeSec == 00) {
					startTimeSec = 60;
					startTimeMin -= 1;
				}
				startTimeSec -= 1;
				clock.setText("remaining time: " + startTimeMin + ":"
						+ startTimeSec);
				companionMessage.setText("Correctness "
						+ ghostBrain.getTotalCorrect() + "/"
						+ ghostBrain.getTotalCount());
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
