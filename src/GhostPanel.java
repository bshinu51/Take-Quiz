import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GhostPanel extends JPanel implements Runnable {
	JLabel weatherInfo;
	JLabel ghostImage;
	JLabel comapanionMessage;
	GroupLayout layout;

	public GhostPanel() {
		weatherInfo = new JLabel();
		ghostImage = new JLabel();
		comapanionMessage = new JLabel();
		layout = new GroupLayout(this);
		setPanelLayout();
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

	public void setPanelLayout() {
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addComponent(weatherInfo)
				.addComponent(ghostImage)
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING).addComponent(
								comapanionMessage)));
		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(weatherInfo).addComponent(ghostImage)
						.addComponent(comapanionMessage)));
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			if (true) {
				weatherInfo.setText("Modify Comapnion here ");
				comapanionMessage.setText("" + i++);
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
