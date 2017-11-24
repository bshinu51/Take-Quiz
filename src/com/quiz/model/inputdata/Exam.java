package com.quiz.model.inputdata;

import java.io.IOException;
import java.util.ArrayList;

public class Exam {

	private ArrayList<Question> listOfQuestions;

	public void loadQuestions(String filename) throws IOException {
		setListOfQuestions(CSVParser.loadCSV(filename));
	}

	public ArrayList<Question> getListOfQuestions() {
		return listOfQuestions;
	}

	void setListOfQuestions(ArrayList<Question> listOfQuestions) {
		this.listOfQuestions = listOfQuestions;
	}
}
