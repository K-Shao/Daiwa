import production.Configuration;
import production.Recognizer;

public class Main {
	
    public static void main(String[] args) {
        try {
            Configuration config = new Configuration();
            
//            System.out.println(recognizeWavFile(config, "res/testjp.wav"));
            System.out.println(Recognizer.recognizeVoice(config));
//            File temp = FileHandler.convertMP3toWAV(new File("res/sample.mp3"), new File("res/result.wav"));
//            DataManager.generateDirectories("train/sorted_audio", "train/utterances.txt");
//            DataManager.generateTransFile("train/sorted_audio", "train/all_audio", "train/utterances.txt");
        } catch (Exception e) {
			e.printStackTrace();
		} 

    	    
    	
    }
}