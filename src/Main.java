import db.DBConn;
import io.BonxTester;
import parsing.Parser;
import production.Synthesizer;
import ui.HomeScreen;
import ui.Sys;

public class Main {
	
    public static void main(String[] args) {
        try {

        	
        	DBConn.init();
        	Parser.load();
        	Synthesizer.speak("おはようございます", Sys.getInstance().getConfiguration());
        	System.exit(0);
        	HomeScreen hs = new HomeScreen();
        	new Thread(new BonxTester(hs)).run();

//            DataManager.generateDirectories("train/sorted_audio", "train/utterances.txt");
//            DataManager.generateTransFile("train/sorted_audio", "train/all_audio", "train/utterances.txt");
        } catch (Exception e) {
			e.printStackTrace();
		} 
    }
    

}