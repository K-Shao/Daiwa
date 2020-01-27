package production;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class Synthesizer {
	
	
	public static void speak (String text, Configuration config) throws InterruptedException, ExecutionException {
		SpeechSynthesizer synth = new SpeechSynthesizer(config.speechConfig);
		Future<SpeechSynthesisResult> task = synth.SpeakTextAsync(text);
		SpeechSynthesisResult result = task.get();
		
		result.close();
		synth.close();
	}

}
