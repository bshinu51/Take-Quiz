package com.cse360.quiz.model.inputdata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVParser {

	private final static String BY_COMMA = ",";

	public static ArrayList<Question> loadCSV(String filename)
			throws IOException {
		ArrayList<Question> list = new ArrayList<Question>();
		BufferedReader iReader = new BufferedReader(new FileReader(filename));
		String s;
		while ((s = iReader.readLine()) != null) {
			System.out.println(s);
			list.add(convertToQuestion(s));
		}
		iReader.close();
		return list;
	}

	private static Question convertToQuestion(String s) {
		if (s != null) {
			String str[] = s.split(BY_COMMA);
			return new Question(str);
		}
		return null;
	}
}
