package com.fix.obd.protocol;

public interface ODBProtocol {
	public boolean DBOperation();
	public byte[] replyToClient();
}
