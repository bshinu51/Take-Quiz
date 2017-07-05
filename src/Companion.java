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
		initialize(ExamController.INDIF_FILE_NAME);
	}

	private void initialize(String fileName) {
		this.removeAll();
		weatherInfo = new JLabel();
		weatherInfo.setText("Temp: " + ghostBrain.getTemperature());
		ghostImage = new JLabel(new ImageIcon(new ImageIcon(fileName)
				.getImage().getScaledInstance(50, 50, 0)));
		companionMessage = new JLabel();
		clock = new JLabel();
		layout = new GroupLayout(this);
		setPanelLayout();
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
		boolean isEnter = false;
		while (true) {
			synchronized (ghostBrain.mLock) {
				if (ghostBrain.isHasChanged())
					isEnter = true;
				ghostBrain.setHasChanged(false);
			}
			if (isEnter) {
				double correct = ghostBrain.getTotalCorrect();
				double total = ghostBrain.getTotalCount();
				if (total > 0 && correct / total < 0.5)
					initialize(pickSad(startTimeSec));
				else if (total > 0 && correct / total >= 0.8)
					initialize(pickHappy(startTimeSec));
				else
					initialize(pickIndiff());
				System.out.println(correct + " " + total + " " + correct
						/ total);
				companionMessage
						.setText("Correctness " + correct + "/" + total);
				isEnter = false;
			}
			if (startTimeSec == 00) {
				startTimeSec = 60;
				startTimeMin -= 1;
			}
			startTimeSec -= 1;
			clock.setText("remaining time: " + startTimeMin + ":"
					+ startTimeSec);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
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
}
