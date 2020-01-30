package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ui.Sys;

public class Parser {
	
	private static Map<String, String> hardDictionary = new HashMap <String, String> ();
	private static Map<String, String> jpKeysToEnKeys = new HashMap <String, String> ();
	
	public static void load() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./res/hard_dictionary.txt"));
		String line = null;
		while ((line = br.readLine())!=null) {
			String [] arr = line.split(":");
			hardDictionary.put(arr[0].trim(), arr[1].trim());
		}
		br.close();
		
		br = new BufferedReader(new FileReader("./res/keys_dictionary.txt"));
		line = null;
		while ((line = br.readLine())!=null) {
			String [] arr = line.split(":");
			jpKeysToEnKeys.put(arr[0].trim(), arr[1].trim());
		}
		br.close();
	}
	
	public static String useHardDictionary (String input) {
		for (String key: hardDictionary.keySet()) {
			input = input.replaceAll(key, hardDictionary.get(key));
		}
		return input;
	}
	
	public static String [] parseBasicForm (String input) {
		if (input.contains("は") && input.contains("です")) {
			String [] arr = input.split("は", 2);
			if (input.contains("は") && arr[1]!=null) {
				if (arr[1].contains("です")) {
					arr[1] = arr[1].split("です")[0];
				}
			}
			return arr;
		} else {
			return new String [] {input};
		}

	}
	
	private static String [] splitSentences (String input) {
		return input.split("そして");
	}
	
	private static String refineValue(String key, String value) {
		if (key.equals("D1") || key.equals("D2") || key.equals("D3") || key.equals("D4")) {
			if (value.length() == 4) {
				return value.substring(0, 2) + "." + value.substring(2, 4);
			}
		}
		
		if (key.equals("T1") || key.equals("T2") || key.equals("T3")) {
			if (value.charAt(0)=='.') {
				return "1" + value;
			}
		}
		
		if (key.equals("長さ")) {
			if (value.contains("meter")) {
				return value.split("meter")[0] + "000";
			}
		}
		
		return value;
	}

	/**
	 * 
	 * @param speech
	 * @param header
	 * @return Confirmation text to say. 
	 */
	public static String interpret(String speech, BonxHeader header) {
		if (speech.equals("NOT RECOGNIZED") || speech.equals("")) {
			return "いいえ";
		}
		
		speech = useHardDictionary(speech);
		String [] sentences = splitSentences(speech);
		StringBuilder feedback = new StringBuilder ();
		
		for (int i = 0; i < sentences.length; i++) {
			String sentence = sentences[i];
			String [] action = parseBasicForm (sentence);
			if (action.length != 1) {
				String value = refineValue (action[0], action[1]);
				feedback.append(Sys.getInstance().set(action[0], value, header));
			} else {
				feedback.append(Sys.getInstance().set(sentence, header));
			}

			if (i != sentences.length - 1) {
				feedback.append("そして");
			}
		}
		System.out.println(feedback.toString());
		return "はい, " + feedback.toString();
	}

	public static String japaneseKeyToEnglishKey(String key) {
		return jpKeysToEnKeys.get(key);
	}
}
