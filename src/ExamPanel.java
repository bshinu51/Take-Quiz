import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ExamPanel extends JPanel {
	private Exam exam;
	private String filename = "question_n_answer";
	ArrayList<ButtonGroup> bGroupList;
	private String[] selectedAnswer = new String[10];

	public ExamPanel() {
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize() throws IOException {
		exam = new Exam();
		exam.loadQuestions(filename);
		GridLayout layout = new GridLayout(10, 1);
		addQuestionsToPanel(exam.listOfQuestions);
		setLayout(layout);
	}

	private void addQuestionsToPanel(ArrayList<Questions> listOfQuestions) {
		bGroupList = new ArrayList<ButtonGroup>(listOfQuestions.size());
		int index = 0;
		for (Questions question : listOfQuestions) {
			JPanel panel = new JPanel();
			panel.add(new JLabel("Question:" + question));
			ButtonGroup bGroup = new ButtonGroup();
			ArrayList<JRadioButton> buttonList = new ArrayList<JRadioButton>();
			for (Answers ans : question.answers) {
				JRadioButton button = new JRadioButton(ans.answerText);
				buttonList.add(button);
				button.setActionCommand("" + index);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						answerSelected(e.getActionCommand());
					}
				});
				bGroup.add(button);
				panel.add(button);
			}
			index++;
			bGroupList.add(bGroup);
			add(panel);
		}
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
							+ selectedAnswer[i] + " to " + b.getText());
					selectedAnswer[i] = b.getText();
					answeChanged(i);
				}
			}
		}
	}

	private void answeChanged(int index) {

	}
}
