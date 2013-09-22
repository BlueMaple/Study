package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;
import com.fix.obd.util.ProtocolPropertiesUtil;

public class TerminalHeartbeat extends ODBProtocolParser implements ODBProtocol {
	private static final  Logger logger = Logger.getLogger(TerminalHeartbeat.class);
	private String clientId;
	private String bufferId;
	public TerminalHeartbeat(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的心跳包");
	}
	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		try{
			this.clientId = this.getId();
			this.bufferId = this.getBufferId();
			return true;
		}catch(Exception ex){
			logger.debug(ex.getLocalizedMessage());
			return false;
		}
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
//	public static void main(String args[]){
//		TerminalHeartbeat t = new TerminalHeartbeat("0000000861825486344100000200014f");
//		t.DBOperation();
//		t.replyToClient();
//	}
}
