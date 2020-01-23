package io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.Arrays;

import com.microsoft.cognitiveservices.speech.audio.PullAudioInputStreamCallback;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

public class BonxAudioStreamReader extends PullAudioInputStreamCallback {
	
	private InputStream in;
	private OutputStream out;

	public BonxAudioStreamReader () throws IOException {
		File socketFile = new File("/home/kevin/.bonx/daemons/room_10401/u_12184_ipc.sock");
		UnixSocketAddress address = new UnixSocketAddress(socketFile);
		UnixSocketChannel channel = UnixSocketChannel.open(address);
		channel.setKeepAlive(true);
		InputStream is = Channels.newInputStream(channel);
		OutputStream os = Channels.newOutputStream(channel);
		this.in = is;
		this.out = os;		
		
//		File socketFile = new File("/home/kevin/.bonx/daemons/room_10401/u-12184_ipc.sock");
		
//		AFUNIXSocket socket = AFUNIXSocket.newInstance();
//		socket.connect(new AFUNIXSocketAddress(socketFile));
//		this.stream = socket.getInputStream();
//		String socketAddress = "/home/kevin/.bonx/daemons/room_10401/u_12184_ipc.sock";
//		UnixDomainSocketClient server = new UnixDomainSocketClient (socketAddress, JUDS.SOCK_STREAM);
//		this.in = server.getInputStream();
//		this.out = server.getOutputStream();
//		System.out.println(in.available());
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int read(byte[] buffer) {
		try {
			return in.read(buffer, 0, buffer.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String readHeader () {
		byte [] header = new byte[17];
		try {
			this.in.read(header);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert header[0] == 1;
		long timeMillis = toLong(Arrays.copyOfRange(header, 1, 8));
		long id = toLong (Arrays.copyOfRange(header, 9, 12));
		long bodyLength = toLong (Arrays.copyOfRange(header, 13, 16));

		
		return ("Time: " + timeMillis + " ID: " + id + " Length: " + bodyLength);
	}
	
    private static long toLong (byte [] arr) {
    	long value = 0;
    	for (int i = 0; i < arr.length; i++) {
    		value += ((long) arr[i] & 0xffL) << 8 * i;
    	}
    	return value;
    }
}
