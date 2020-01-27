package io;

public class BonxHeader {
	private int command;
	private long timeMillis;
	private int id;
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
