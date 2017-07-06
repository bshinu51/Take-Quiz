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
		initialize(ExamController.WELCOME_FILE_NAME);
	}

	private void initialize(String fileName) {
		this.removeAll();
		weatherInfo = new JLabel();
		weatherInfo.setText("Temp: " + ghostBrain.getTemperature());
		ghostImage = new JLabel(new ImageIcon(new ImageIcon(fileName)
				.getImage().getScaledInstance(70, 70, 0)));
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
		boolean isThreadStop = false;
		while (!isThreadStop) {
			synchronized (ghostBrain.mLock) {
				if (ghostBrain.isHasChanged())
					isEnter = true;
				ghostBrain.setHasChanged(false);
			}
			if (isEnter) {
				double correct = ghostBrain.getTotalCorrect();
				double total = ghostBrain.getTotalCount();
				double ratio = correct / total;
				if (total > 0 && ratio < 0.5 && ratio >= 0.3)
					initialize(pickSad(0));
				else if (total > 0 && ratio < 0.3)
					initialize(pickSad(1));
				else if (total > 0 && correct / total >= 0.7
						&& correct / total < 0.9)
					initialize(pickHappy(0));
				else if (total > 0 && correct / total >= 0.9)
					initialize(pickHappy(1));
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
			if (ghostBrain.getTotalCount() == 10
					|| (startTimeMin == 0 && startTimeSec == 0))
				isThreadStop = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
		afterExamOver();
	}

	private void afterExamOver() {
		companionMessage.setText(" Your exam is over");
		ExamController.examOver(ghostBrain.getTotalCorrect());
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
