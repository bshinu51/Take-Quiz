package com.cse360.quiz.inputdata;

import java.io.IOException;
import java.util.ArrayList;

public class Exam {

	private ArrayList<Questions> listOfQuestions;

	public void loadQuestions(String filename) throws IOException {
		setListOfQuestions(CSVParser.loadCSV(filename));
	}

	public ArrayList<Questions> getListOfQuestions() {
		return listOfQuestions;
	}

	void setListOfQuestions(ArrayList<Questions> listOfQuestions) {
		this.listOfQuestions = listOfQuestions;
	}
}
