package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;
import com.fix.obd.util.ProtocolPropertiesUtil;

public class UploadDTC extends ODBProtocolParser implements ODBProtocol{
	private static final  Logger logger = Logger.getLogger(TerminalHeartbeat.class);
	private String clientId;
	private String bufferId;
	
	public UploadDTC(String messageStr){
		super(messageStr);
		logger.info("�յ������ն�" + this.getId() + "�ϴ�������");
	}

	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		this.clientId = this.getId();
		this.bufferId = this.getBufferId();
		
		String message = this.getRealMessage();
		int dtcNumber = Integer.valueOf(message.substring(0,2), 16);
		int stringIndex = 2;
		for(int i = 0 ; i < dtcNumber ; i++){
			String dtcString = "";
			for(int charIndex = 0 ; charIndex < 5 ; charIndex++){
				dtcString += (char)(int)Integer.valueOf(message.substring(stringIndex,stringIndex+2), 16);
				stringIndex += 2;
			}
			logger.info("���������ն˵Ĺ�����-"+(i+1)+":" + dtcString);
		}
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
