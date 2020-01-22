import db.DBConn;
import ui.HomeScreen;

public class Main {
	
    public static void main(String[] args) {
        try {
        	DBConn.init();
        	new HomeScreen();
        	
//    		File socketFile = new File("/home/kevin/.bonx/daemons/room_10401/u_12184_ipc.sock");
//    		UnixSocketAddress address = new UnixSocketAddress(socketFile);
//    		UnixSocketChannel channel = UnixSocketChannel.open(address);
//    		InputStreamReader r = new InputStreamReader(Channels.newInputStream(channel));
//    		CharBuffer result = CharBuffer.allocate(1024);
//    		r.read(result);
//    		result.flip();
//    		System.out.println("Read from server: " + result.toString());

    		
    		
    		
    		
//            Configuration config = new Configuration(); 
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