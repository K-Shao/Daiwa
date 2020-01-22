import ui.HomeScreen;
import io.BonxAudioStreamReader;
import production.Actor;
import production.Configuration;
import production.Recognizer;

public class Main {
	
    public static void main(String[] args) {
        try {
        	new HomeScreen();
//            Configuration config = new Configuration();
//            Configuration config = new Configuration();
            BonxAudioStreamReader basr = new BonxAudioStreamReader();
            byte [] buffer = new byte[128];
            basr.read(buffer);
            System.out.println(new String(buffer));
            System.out.println("here");
//            Recognizer.recognizeStream(config, new Actor());
            
//            System.out.println(recognizeWavFile(config, "res/testjp.wav"));
//            System.out.println(Recognizer.recognizeVoice(config));
//            File temp = FileHandler.convertMP3toWAV(new File("res/sample.mp3"), new File("res/result.wav"));
//            DataManager.generateDirectories("train/sorted_audio", "train/utterances.txt");
//            DataManager.generateTransFile("train/sorted_audio", "train/all_audio", "train/utterances.txt");
        } catch (Exception e) {
			e.printStackTrace();
		} 

    	    
    	
    }
}