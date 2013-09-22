package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;
import com.fix.obd.util.ProtocolPropertiesUtil;

public class PositionInfo extends ODBProtocolParser implements ODBProtocol{
	private static final Logger logger = Logger.getLogger(PositionInfo.class);
	private String clientId;
	private String bufferId;
	public PositionInfo(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的位置信息");
	}
	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		try {
			this.clientId = this.getId();
			this.bufferId = this.getBufferId();
			String message = this.getRealMessage();
			System.out.println(message);
			String gpsDate = message.substring(0,2) + "-" + message.substring(2,4) + "-" + message.substring(4,6) + " " + message.substring(6,8) + ":" + message.substring(8,10) + ":" + message.substring(10,12);
			System.out.println(gpsDate);
			String gpsAlert = message.substring(12,16);
			System.out.println(gpsAlert);
			String alertInBinary = Integer.toBinaryString(Integer.valueOf(gpsAlert,16));
			System.out.println(alertInBinary);
			String gpsState = message.substring(16,18);
			String gpsSatelliteNum = message.substring(18,20);
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
		StackTraceElement[] stacks = new Throwable().getStackTrace(); 
		String classname =  stacks[0].getClassName().substring(stacks[0].getClassName().lastIndexOf(".")+1);
		ProtocolPropertiesUtil p = new ProtocolPropertiesUtil();
		String operationId = p.getIdByProtocol(classname);
		ServerAck serverACK = new ServerAck(clientId,bufferId,operationId);
		return serverACK.replyToClient();
	}
	public static void main(String args[]){
		PositionInfo p = new PositionInfo("000000086182548634410300150004001010000014010401000000000000000000004e");
		p.DBOperation();
	}
}
