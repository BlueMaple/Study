package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;

public class UploadOBDInfo extends ODBProtocolParser implements ODBProtocol{
	private static final Logger logger = Logger.getLogger(UploadOBDInfo.class);
	private String clientId;
	private String bufferId;

	public UploadOBDInfo(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的ODB信息查询请求信息");
	}
	
	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		logger.info("终端信息包括" + this.getRealMessage());
		String realMessage = this.getRealMessage();
		return false;
	}

	@Override
	public byte[] replyToClient() {
		// TODO Auto-generated method stub
		return null;
	}

}
