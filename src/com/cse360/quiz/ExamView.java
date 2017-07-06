package com.cse360.quiz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Observable;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.cse360.quiz.inputdata.Answers;
import com.cse360.quiz.inputdata.Exam;
import com.cse360.quiz.inputdata.Questions;

public class ExamView extends Observable {
	private JPanel viewPanel = new JPanel();
	private Exam exam;
	private ArrayList<ButtonGroup> bGroupList;
	private String[] selectedAnswer = new String[10];
	private int questionIndex;
	private boolean hasChanged;
	private String endTime;
	private String timeTaken;
	Calendar cal;

	@SuppressWarnings("unused")
	private ExamView() {

	}

	public ExamView(JFrame frame) {
		try {
			initialize(frame);
			cal = Calendar.getInstance();
			endTime = cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize(JFrame frame) throws IOException {
		setExam(new Exam());
		getExam().loadQuestions(ExamController.QA_FILE_NAME);
		GridLayout layout = new GridLayout(10, 1);
		addQuestionsToPanel(getExam().getListOfQuestions());
		getViewPanel().setLayout(layout);
		frame.add(getViewPanel());
	}

	private void addQuestionsToPanel(ArrayList<Questions> listOfQuestions) {
		bGroupList = new ArrayList<ButtonGroup>(listOfQuestions.size());
		int index = 0;
		for (Questions que : listOfQuestions) {
			JPanel panel = new JPanel();
			panel.add(new JLabel("Question: " + que.getQuestion()));
			ButtonGroup bGroup = new ButtonGroup();
			ArrayList<JRadioButton> buttonList = new ArrayList<JRadioButton>();
			for (Answers ans : que.getAnswers()) {
				JRadioButton button = new JRadioButton(ans.answerText);
				buttonList.add(button);
				button.setActionCommand("" + index);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setTimeTaken();
						answerSelected(e.getActionCommand());
					}
				});
				bGroup.add(button);
				panel.add(button);
			}
			index++;
			bGroupList.add(bGroup);
			getViewPanel().add(panel);
		}
	}

	protected void setTimeTaken() {
		cal = Calendar.getInstance();
		String str[] = endTime.split(":");
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		timeTaken = (min - Integer.parseInt(str[0])) + ":"
				+ (sec - Integer.parseInt(str[1]));
		endTime = min + ":" + sec;
	}

	public String getTimeTaken() {
		System.out.println(timeTaken);
		return timeTaken;
	}

	protected void answerSelected(String index) {
		int i = Integer.parseInt(index);
		Enumeration<AbstractButton> buttonList = bGroupList.get(i)
				.getElements();
		while (buttonList.hasMoreElements()) {
			AbstractButton b = buttonList.nextElement();
			if (b.isSelected()) {
				if (selectedAnswer[i] != b.getText()) {
					System.out.println("Answer changed from "
							+ selectedAnswer[i] + " to " + b.getText()
							+ " for the question " + i);
					selectedAnswer[i] = b.getText();
					hasChanged = true;
					answerChanged(i);
				}
			}
		}
	}

	private void answerChanged(int index) {
		setQuestionIndex(index);
		setChanged();
		notifyObservers(hasChanged);
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	private void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public Exam getExam() {
		return exam;
	}

	private void setExam(Exam exam) {
		this.exam = exam;
	}

	public boolean isCorrect(int index) {
		boolean value = false;
		if (selectedAnswer[index].equals(exam.getListOfQuestions().get(index)
				.getCurrectAnswer()))
			value = true;
		return value;
	}

	public void clearChanged() {
		hasChanged = false;
	}

	public JPanel getViewPanel() {
		return viewPanel;
	}
}
