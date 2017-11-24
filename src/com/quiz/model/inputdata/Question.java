package com.quiz.model.inputdata;

import java.util.ArrayList;

public class Question {
	private String question;
	private ArrayList<Answers> answers;
	private String currectAnswer;
	private String imagePath;

	public Question(String[] str) {
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
		if (str.length > 6 && !str[6].equals("null"))
			imagePath = str[6];
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<Answers> getAnswers() {
		return answers;
	}

	public String getCurrectAnswer() {
		return currectAnswer;
	}

	public String getImagePath() {
		return imagePath;
	}

	public boolean hasImage() {
		return imagePath != null ? true : false;
	}
}
