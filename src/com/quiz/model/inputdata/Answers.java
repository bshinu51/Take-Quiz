package com.quiz.model.inputdata;

public class Answers {
	public String answerText;
	public boolean isCorrect;

	public Answers(String answer, boolean isCorrect) {
		setAnswerText(answer);
		setCorrect(isCorrect);
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

}
