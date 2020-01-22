package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.etsy.net.JUDS;
import com.etsy.net.UnixDomainSocketClient;
import com.microsoft.cognitiveservices.speech.audio.PullAudioInputStreamCallback;

public class BonxAudioStreamReader extends PullAudioInputStreamCallback {
	
	private InputStream in;
	private OutputStream out;

	public BonxAudioStreamReader () throws IOException {
//		File socketFile = new File("/home/kevin/.bonx/daemons/room_10401/u-12184_ipc.sock");
//		AFUNIXSocket socket = AFUNIXSocket.newInstance();
//		socket.connect(new AFUNIXSocketAddress(socketFile));
//		this.stream = socket.getInputStream();
		String socketAddress = "/home/kevin/.bonx/daemons/room_10401/u_12184_ipc.sock";
		UnixDomainSocketClient server = new UnixDomainSocketClient (socketAddress, JUDS.SOCK_STREAM);
		this.in = server.getInputStream();
		this.out = server.getOutputStream();
		System.out.println(in.available());
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

}
