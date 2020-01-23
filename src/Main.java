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
//    		channel.setKeepAlive(true);
//    		InputStream is = Channels.newInputStream(channel);
//    		byte[] header = new byte[17];
//    		is.read(header);

//    		byte [] body = new byte[(int) bodyLength];
//    		is.read(body);
//    		for (byte b: body) {
//    			System.out.println(b);
//    		}
    		
//            Configuration config = new Configuration(); 
//            BonxAudioStreamReader basr = new BonxAudioStreamReader();
//            System.out.println(basr.readHeader());
//            
//            Recognizer.recognizeStream(basr, config, new Actor());
//            System.out.println("here");
            
            
            
            
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