package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.BonxHeader;
import ui.Sys;

public class Parser {
	
	private static Map<String, String> hardDictionary = new HashMap <String, String> ();
	
	public static String useHardDictionary (String input) {
		for (String key: hardDictionary.keySet()) {
			input = input.replaceAll(key, hardDictionary.get(key));
		}
		return input;
	}
	
	public static String [] parseBasicForm (String input) {
		String [] arr = input.split("は");
		if (input.contains("は") && arr[1]!=null) {
			if (arr[1].contains("です")) {
				arr[1] = arr[1].split("です")[0];
			}
		}
		return arr;
		
	}

	public static void load() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./res/hard_dictionary.txt"));
		String line = null;
		while ((line = br.readLine())!=null) {
			String [] arr = line.split(":");
			hardDictionary.put(arr[0].trim(), arr[1].trim());
		}
		br.close();
	}

	public static void interpret(String speech, BonxHeader header) {
		speech = useHardDictionary(speech);
		String [] action = parseBasicForm (speech);
		if (action.length != 1) {
			Sys.getInstance().set(action[0], action[1], header);
		}
	}

}
