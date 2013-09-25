package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;
import com.fix.obd.util.ProtocolPropertiesUtil;

public class DTCStatus extends ODBProtocolParser implements ODBProtocol{
	private static final  Logger logger = Logger.getLogger(TerminalHeartbeat.class);
	private String clientId;
	private String bufferId;
	
	public DTCStatus(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "OBD故障状态");
	}

	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		this.clientId = this.getId();
		this.bufferId = this.getBufferId();

		String message = this.getRealMessage();
		int messageInteger = Integer.valueOf(message , 16);
		if(messageInteger == 0){
			message = "正常";
		}
		else if(messageInteger == 1){
			message = "警告";
		}
		else if(messageInteger == 2){
			message = "严重";
		}
		logger.info("OBD故障状态："+messageInteger+"，"+message);
		return false;
	}

	@Override
	public byte[] replyToClient() {
		// TODO Auto-generated method stub
		StackTraceElement[] stacks = new Throwable().getStackTrace(); 
		String classname =  stacks[0].getClassName().substring(stacks[0].getClassName().lastIndexOf(".")+1);
		ProtocolPropertiesUtil p = new ProtocolPropertiesUtil();
		String operationId = p.getIdByProtocol(classname);
		ServerAck serverACK = new ServerAck(clientId,bufferId,operationId);
		return serverACK.replyToClient();

	}

}
