package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;

public class ReadDTC extends ODBProtocolParser implements ODBProtocol{
	private static final  Logger logger = Logger.getLogger(TerminalHeartbeat.class);
	private String clientId;
	private String bufferId;

	public ReadDTC(String messageStr){
		super(messageStr);
		logger.info("�յ������ն�" + this.getId() + "���������");
	}
	
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
