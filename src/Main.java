import io.BonxTester;
import io.DBConn;
import io.Parser;
import ui.HomeScreen;

public class Main {
	
    public static void main(String[] args) {
    	
        try {
        	DBConn.init();
        	Parser.load();
        	HomeScreen hs = new HomeScreen();
        	new Thread(new BonxTester(hs)).run();
        } catch (Exception e) { //Pokemon exception...oops
			e.printStackTrace();
		} 
        
    }
    

}