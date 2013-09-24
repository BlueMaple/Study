package com.fix.obd.protocol.impl;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;

public class DTCStatus extends ODBProtocolParser implements ODBProtocol{

	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] replyToClient() {
		// TODO Auto-generated method stub
		return null;
	}

}
