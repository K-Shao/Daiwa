package production;

import java.util.Scanner;
import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.CancellationDetails;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SessionEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionCanceledEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.util.EventHandler;

public class Recognizer {

	public static String recognizeWavFile (Configuration config, String file) {
		AudioConfig audioInput = AudioConfig.fromWavFileInput(file);
		if (audioInput == null) {
			return "Couldn't recognize file: file not found. Path: " + file;
		}
		
		SpeechRecognizer recognizer = new SpeechRecognizer (config.speechConfig, config.sourceLanguageConfig, audioInput);

		System.out.println("Processing...");
		String toReturn = "";
		try {
			Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
			SpeechRecognitionResult result = task.get();
			
            if (result.getReason() == ResultReason.RecognizedSpeech) {
                toReturn = "Recognized: " + result.getText();
            }
            else if (result.getReason() == ResultReason.NoMatch) {
                toReturn = "NOT RECOGNIZED";
            }
            else if (result.getReason() == ResultReason.Canceled) {
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                toReturn = "CANCELED: Reason=" + cancellation.getReason();

                if (cancellation.getReason() == CancellationReason.Error) {
                    toReturn += "CANCELED: ErrorCode=" + cancellation.getErrorCode();
                    toReturn += "CANCELED: ErrorDetails=" + cancellation.getErrorDetails();
                    toReturn += "CANCELED: Did you update the subscription info?";
                }
            }
		} catch (Exception e) {
			return "Unexpected error during transcription!";
		} finally {
			recognizer.close();
		}

		return toReturn;
	}
	
	public static String recognizeLongWav (Configuration config, String file) {
        // <recognitionContinuousWithFile>
        // Creates an instance of a speech config with specified
        // subscription key and service region. Replace with your own subscription key
        // and service region (e.g., "westus").

        // Creates a speech recognizer using file as audio input.
        AudioConfig audioInput = AudioConfig.fromWavFileInput(file);
		if (audioInput == null) {
			return "Couldn't recognize file: file not found. Path: " + file;
		}
		
        SpeechRecognizer recognizer = new SpeechRecognizer (config.speechConfig, config.sourceLanguageConfig, audioInput);

        {
            // Subscribes to events.
            recognizer.recognizing.addEventListener(new EventHandler<SpeechRecognitionEventArgs>() {
				@Override
				public void onEvent(Object s, SpeechRecognitionEventArgs e) {
				    System.out.println("RECOGNIZING: Text=" + e.getResult().getText());
				}
			});

            recognizer.recognized.addEventListener(new EventHandler<SpeechRecognitionEventArgs>() {
				@Override
				public void onEvent(Object s, SpeechRecognitionEventArgs e) {
				    if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
				        System.out.println("RECOGNIZED: Text=" + e.getResult().getText());
				    }
				    else if (e.getResult().getReason() == ResultReason.NoMatch) {
				        System.out.println("NOMATCH: Speech could not be recognized.");
				    }
				}
			});

            recognizer.canceled.addEventListener(new EventHandler<SpeechRecognitionCanceledEventArgs>() {
				@Override
				public void onEvent(Object s, SpeechRecognitionCanceledEventArgs e) {
				    System.out.println("CANCELED: Reason=" + e.getReason());

				    if (e.getReason() == CancellationReason.Error) {
				        System.out.println("CANCELED: ErrorCode=" + e.getErrorCode());
				        System.out.println("CANCELED: ErrorDetails=" + e.getErrorDetails());
				        System.out.println("CANCELED: Did you update the subscription info?");
				    }
				}
			});

            recognizer.sessionStarted.addEventListener(new EventHandler<SessionEventArgs>() {
				@Override
				public void onEvent(Object s, SessionEventArgs e) {
				    System.out.println("\n    Session started event.");
				}
			});

            recognizer.sessionStopped.addEventListener(new EventHandler<SessionEventArgs>() {
				@Override
				public void onEvent(Object s, SessionEventArgs e) {
				    System.out.println("\n    Session stopped event.");
				}
			});
            
            try {
            	recognizer.startContinuousRecognitionAsync();
            	recognizer.stopContinuousRecognitionAsync();
                // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
                System.out.println("Say something...");
                recognizer.startContinuousRecognitionAsync().get();

                System.out.println("Press any key to stop");
                new Scanner(System.in).nextLine();

                recognizer.stopContinuousRecognitionAsync().get();
            } catch (Exception e) {
            	
            	return "Unexpected exception";
            } finally {
                config.speechConfig.close();
                audioInput.close();
                recognizer.close();
            }
        }
        config.speechConfig.close();
        audioInput.close();
        recognizer.close();
        return "END of method";
        // </recognitionContinuousWithFile>
	}
	
	
	public static String recognizeVoice (Configuration config) {
		String toReturn = null;
		try {
			SpeechRecognizer recognizer = new SpeechRecognizer(config.speechConfig, config.sourceLanguageConfig);
			
			System.out.println("Say something...");
	        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
	        SpeechRecognitionResult result = task.get();
	        
			if (result.getReason() == ResultReason.RecognizedSpeech) {
	            toReturn = "We recognized: " + result.getText();
	        }
	        else if (result.getReason() == ResultReason.NoMatch) {
	            toReturn = "NOMATCH: Speech could not be recognized.";
	        }
	        else if (result.getReason() == ResultReason.Canceled) {
	            CancellationDetails cancellation = CancellationDetails.fromResult(result);
	            toReturn = "CANCELED: Reason=" + cancellation.getReason();

	            if (cancellation.getReason() == CancellationReason.Error) {
	                toReturn += "CANCELED: ErrorCode=" + cancellation.getErrorCode();
	                toReturn += "CANCELED: ErrorDetails=" + cancellation.getErrorDetails();
	                toReturn += "CANCELED: Did you update the subscription info?";
	            }
	        }

	        recognizer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return toReturn;
	}

}
