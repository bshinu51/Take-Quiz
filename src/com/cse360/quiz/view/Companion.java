package com.cse360.quiz.view;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cse360.quiz.control.intelligence.GhostBrain;

public class Companion extends JPanel implements Runnable {
	JLabel weatherInfo;
	JLabel ghostImage;
	JLabel companionMessage;
	JLabel clock;
	GridLayout layout;
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
		weatherInfo.setText(ghostBrain.getTemperatureMsg());
		ghostImage = new JLabel(new ImageIcon(new ImageIcon(fileName)
				.getImage().getScaledInstance(70, 70, 0)));
		ghostImage.setBounds(0, 0, 70, 70);
		companionMessage = new JLabel();
		clock = new JLabel();
		layout = new GridLayout(2, 2);
		// setPanelLayout();
		setLayout(layout);
		add(ghostImage);
		add(companionMessage);
		add(weatherInfo);
		add(clock);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void run() {
		boolean isThreadStop = false;
		while (!isThreadStop) {
			synchronized (ghostBrain.acquireLock()) {
				if (ghostBrain.isHasChanged()) {
					initialize(ghostBrain.getGhostCharacter());
					companionMessage.setText(ghostBrain.getCompanionMessage());
					ghostBrain.setHasChanged(false);
				}
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
		clock.setText("");
		ExamController.examOver(ghostBrain.getTotalCorrect());
	}

}
