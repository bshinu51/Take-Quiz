import java.io.IOException;
import java.util.ArrayList;

public class Exam {

	ArrayList<Questions> listOfQuestions;

	public void loadQuestions(String filename) throws IOException {
		listOfQuestions = CSVParser.loadCSV(filename);
	}
}
