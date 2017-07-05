import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ExamView extends Observable {
	JPanel viewPanel = new JPanel();
	private Exam exam;
	private String filename = "question_n_answer";
	private ArrayList<ButtonGroup> bGroupList;
	private String[] selectedAnswer = new String[10];
	private int questionIndex;
	private boolean hasChanged;

	@SuppressWarnings("unused")
	private ExamView() {

	}

	public ExamView(JFrame frame) {
		try {
			initialize(frame);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize(JFrame frame) throws IOException {
		setExam(new Exam());
		// Populate exam object
		getExam().loadQuestions(filename);
		GridLayout layout = new GridLayout(10, 1);
		addQuestionsToPanel(getExam().listOfQuestions);
		viewPanel.setLayout(layout);
		frame.add(viewPanel);
	}

	private void addQuestionsToPanel(ArrayList<Questions> listOfQuestions) {
		bGroupList = new ArrayList<ButtonGroup>(listOfQuestions.size());
		int index = 0;
		for (Questions question : listOfQuestions) {
			JPanel panel = new JPanel();
			panel.add(new JLabel("Question:" + question));
			// For each question group of four buttons from which only 1 can be selected
			ButtonGroup bGroup = new ButtonGroup();
			ArrayList<JRadioButton> buttonList = new ArrayList<JRadioButton>();
			// For each answer in each question
			for (Answers ans : question.answers) {
				JRadioButton button = new JRadioButton(ans.answerText);
				buttonList.add(button);
				// Sets the command name for the action event fired
				// by this button to question number
				button.setActionCommand("" + index);
				button.addActionListener(new ActionListener() {
					@Override
					// Invoked when an action occurs on button
					public void actionPerformed(ActionEvent e) {
						// Pass the button's action command name 
						answerSelected(e.getActionCommand());
					}
				});
				bGroup.add(button);
				panel.add(button);
			}
			index++;
			bGroupList.add(bGroup);
			viewPanel.add(panel);
		}
	}
	
	/**
	 * answerSelected
	 * this method is called when user clicks on any answer
	 * @param {String} Clicked button's action command name(question number)
	 */
	protected void answerSelected(String index) {
		int i = Integer.parseInt(index);
		Enumeration<AbstractButton> buttonList = bGroupList.get(i)
				.getElements();
		while (buttonList.hasMoreElements()) {
			AbstractButton b = buttonList.nextElement();
			// If button was clicked
			if (b.isSelected()) {
				// If question's answer was changed by the click
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
	/**
	 * answerChanged
	 * This method is called when user changes question's answer
	 * It will notify the observer of the change
	 * @param {int} Clicked button's action command name (question number)
	 */
	private void answerChanged(int index) {
		setQuestionIndex(index);
		// Marks Observable object as having been changed
		setChanged();
		
		// The hasChanged method will now return true, then notify all of its observers 
		// and then call the clearChanged method to indicate that this object has no longer changed.
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
		if (selectedAnswer[index]
				.equals(exam.listOfQuestions.get(index).currectAnswer))
			value = true;
		return value;
	}

	public void clearChanged() {
		hasChanged = false;
	}
}
