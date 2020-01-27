package production;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.naming.ConfigurationException;

import com.microsoft.cognitiveservices.speech.SourceLanguageConfig;
import com.microsoft.cognitiveservices.speech.SpeechConfig;

public class Configuration {
	
	public SpeechConfig speechConfig;
	public SourceLanguageConfig sourceLanguageConfig;
	
	private String speechSubscriptionKey;
	private String serviceRegion;
	private String language;
	
	public Configuration () throws ConfigurationException {
		this("./res/config.txt");
	}
	
	public Configuration (String configFile) throws ConfigurationException {
		this(new File(configFile));
	}
	
	public Configuration (File configFile) throws ConfigurationException {
		try {
			Scanner sc = new Scanner(configFile);
			while (sc.hasNextLine()) {
				String [] arr = sc.nextLine().split(":");
				if (arr.length != 2) {
					continue;
				}
				String key = arr[0].trim();
				String value = arr[1].trim();
				this.set(key, value);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			throw new ConfigurationException("Configuration file not found! File name: " + configFile.getAbsolutePath());
		}
		speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
		speechConfig.setSpeechSynthesisLanguage(language);
		sourceLanguageConfig = SourceLanguageConfig.fromLanguage(language);
		
		if (speechConfig == null) {
			throw new ConfigurationException("SpeechConfig object not initialized. Please check speechSubscriptionKey and serviceRegion in config.txt. ");
		}
		
		if (sourceLanguageConfig == null) {
			sourceLanguageConfig = SourceLanguageConfig.fromLanguage("en-US"); //Defaults to English
		}
	}

	private void set(String key, String value) {
		
		if (key.equals("subscriptionKey")) {
			speechSubscriptionKey = value;
		}
		if (key.equals("serviceRegion")) {
			serviceRegion = value;
		}
		if (key.equals("language")) {
			language = value;
		}
		
	}
}
