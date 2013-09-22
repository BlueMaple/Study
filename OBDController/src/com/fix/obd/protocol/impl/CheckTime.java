package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;

public class CheckTime extends ODBProtocolParser implements ODBProtocol {
	private static final Logger logger = Logger.getLogger(CheckTime.class);
	private String clientId;
	private String bufferId;
	public CheckTime(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的时间查询请求信息");
	}
	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		try {
			this.clientId = this.getId();
			this.bufferId = this.getBufferId();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.getLocalizedMessage());
			return false;
		}
	}

	@Override
	public byte[] replyToClient() {
		// TODO Auto-generated method stub
		SendTime sendTime = new SendTime(clientId,bufferId);
		return sendTime.replyToClient();
	}
}
