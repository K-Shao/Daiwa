package io;

/**
 * Object to represent a header sent by the bonx.io stream. Must provide all fields on instantiation, 
 * and provides getter methods for all fields. Most fields are self-explanatory. 
 * @author kevin
 *
 */
public class BonxHeader {
	/**
	 * Should just be 1 if you're receiving voice data. 
	 */
	private int command;
	private long timeMillis;
	/**
	 * Bonx ID of the sender of the stream. 
	 */
	private int id;
	/**
	 * Number of bytes in the current packet. At the moment, Bonx protocol is 40ms packets, with body size of 
	 * 1280 bytes. This comes out to 25*1280 bytes per second, or 32 kHz. 
	 */
	private int bodySize;
	
	public BonxHeader (int command, long timeMillis, int id, int bodySize) {
		this.command = command;
		this.timeMillis = timeMillis;
		this.id = id;
		this.bodySize = bodySize;
		
	}

	public int getCommand() {
		return command;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public int getId() {
		return id;
	}

	public int getBodySize() {
		return bodySize;
	}
	
	@Override
	public String toString () {
		return "Time: " + timeMillis + " ID: " + id + " Size: " + bodySize;
	}

}
