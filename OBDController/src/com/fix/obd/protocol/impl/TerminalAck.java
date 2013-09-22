package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;

public class TerminalAck extends ODBProtocolParser implements ODBProtocol{
	private static final  Logger logger = Logger.getLogger(ServerAck.class);
	public TerminalAck(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的Ack");
		logger.info("接受消息的命令字：" + this.getRealMessage().substring(0,4));
		logger.info("接受消息的流水号：" + this.getRealMessage().substring(4,6));
		logger.info("是否成功：" + (this.getRealMessage().substring(6,8).equals("00")?"成功":"失败"));
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
