import java.util.ArrayList;
import javax.swing.ButtonGroup;

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
