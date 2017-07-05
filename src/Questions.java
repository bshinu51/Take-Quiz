import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Questions {

	String question;
	ArrayList<Answers> answers;
	String currectAnswer;
	String imagePath;
	ButtonGroup bGroup;

	public Questions(String[] str) {
		question = str[0];
		answers = new ArrayList<Answers>();
		boolean isCurrect = false;
		currectAnswer = str[5];
		for (int i = 0; i < 4; i++) {
			isCurrect = false;
			if (currectAnswer.equalsIgnoreCase(str[i + 1]))
				isCurrect = true;
			answers.add(new Answers(str[i + 1], isCurrect));
		}
		if (str.length > 6)
			imagePath = str[6];
	}
}
