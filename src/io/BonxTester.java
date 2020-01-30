package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;
import production.Actor;
import production.Recognizer;
import ui.HomeScreen;
import ui.Sys;

/**
 * Bit of a misnomer. Was originally intended to test Bonx, but ended up being used for the actual connection. 
 * Implements Runnable, and runs an infinite loop, listening for a complete stream from Bonx, and delegating 
 * an Actor object to deal with what comes in. 
 * @author kevin
 *
 */
public class BonxTester implements Runnable {
	
	private UnixSocketAddress address = null;
	private InputStream stream = null;
	
	public static final int BLOCKING_TIME = 250;
	/**
	 * Really shouldn't be final - it should come from a config file or maybe even be settable in the program
	 * if you want to have multiple rooms. 
	 */
	public static final String SOCKET_FILE = "/home/kevin/.bonx/daemons/room_10401/u_12184_ipc.sock";
	
	public void initBonx () {
		File socketFile = new File(SOCKET_FILE);
		address = new UnixSocketAddress(socketFile);
	}
	
	/**
	 * 
	 * @return A 2-element Object [] where the first element is a String giving the message, the second element is a BonxHeader with the proper header. 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Object[] testBonx () throws IOException, InterruptedException, ExecutionException {

		UnixSocketChannel channel = UnixSocketChannel.open(address);
		stream = Channels.newInputStream(channel);
		
		BonxHeader header = this.readHeader(stream);
		System.out.println(header);
		channel.setSoTimeout(BLOCKING_TIME);
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		
		byte current = 0;
		LinkedList<Byte> last = new LinkedList<Byte>();
		int index = 0;
		while (true) {
			
			if (current == -1 && last.size() >= 5 & this.containsOnly(last, -1)) {
				break;
			}
			last.addLast(current);
			if (last.size () > 5) {
				last.pollFirst();
			}
			current = (byte) stream.read();
			bOut.write(current);
			
			index++;
			if (index == header.getBodySize()) {
				BonxHeader temp = this.readHeader(stream);
				if (temp.getBodySize() == 1280) {
					header = temp;
				}
				index = 0;
			}
		}
		AudioFormat format = new AudioFormat (16000, 16, 1, true, false);
		
		ByteArrayInputStream bIn = new ByteArrayInputStream(bOut.toByteArray());
		AudioInputStream ais = new AudioInputStream(bIn, format, bOut.size());
		AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new FileOutputStream("tmp/" + header.getTimeMillis() + ".wav"));
		String recognition = Recognizer.recognizeWavFile(Sys.getInstance().getConfiguration(), "tmp/" + header.getTimeMillis() + ".wav");
		return new Object [] {recognition, header};
	}   
	 
	private boolean containsOnly(LinkedList<Byte> last, int i) { 
		for (Byte b: last) {
			if (((byte) i) != b) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void run() {
		initBonx();		

		while (true) {	
			try {
				Object [] result = testBonx ();
				String speech = ((String) result [0]).toLowerCase();
				BonxHeader header = (BonxHeader) result[1];
				System.out.println("Go ahead, " + header.getId());
				System.out.println("Heard: " + speech);
				new Actor(speech, header, hs).run();
			} catch (IOException | InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	 
	
	public BonxHeader readHeader (InputStream stream) {
		byte [] header = new byte[17];
		try {
			stream.read(header);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int command = header[0];
		long timeMillis = toLong(Arrays.copyOfRange(header, 1, 8));
		int id = (int) toLong (Arrays.copyOfRange(header, 9, 12));
		int bodyLength = (int) toLong (Arrays.copyOfRange(header, 13, 16));

		return new BonxHeader(command, timeMillis, id, bodyLength);
	}
	
    private static long toLong (byte [] arr) {
    	long value = 0;
    	for (int i = 0; i < arr.length; i++) {
    		value += ((long) arr[i] & 0xffL) << 8 * i;
    	}
    	return value;
    }

    public BonxTester (HomeScreen hs) {
    	this.hs = hs;
    }
    
    private HomeScreen hs;
}
