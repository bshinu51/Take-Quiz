package com.cse360.quiz.intelligence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class Temprature {
	String urlText = "https://api.darksky.net/forecast/a8d89b8329c66bc61a64b50e625c0159/"
			+ "33.424564,-111.928001";
	private String temperature;

	public Temprature() {
		try {
			getTempFromURL();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

	public void getTempFromURL() throws IOException, JSONException {
		URL url = new URL(urlText);
		URLConnection connection = url.openConnection();
		connection.connect();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), Charset.forName("UTF-8")));
		StringBuilder sBuilder = new StringBuilder();
		String temp;
		while ((temp = bReader.readLine()) != null) {
			sBuilder.append(temp);
		}
		JSONObject jsonResult;
		jsonResult = new JSONObject(sBuilder.toString());
		bReader.close();
		temperature = ""
				+ ((JSONObject) jsonResult.get("currently")).get("temperature");
	}

	public String getTemperature() {
		return temperature;
	}
}
