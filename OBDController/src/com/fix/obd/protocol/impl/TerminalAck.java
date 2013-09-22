package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;

public class TerminalAck extends ODBProtocolParser implements ODBProtocol{
	private static final  Logger logger = Logger.getLogger(ServerAck.class);
	public TerminalAck(String messageStr){
		super(messageStr);
		logger.info("�յ������ն�" + this.getId() + "��Ack");
		logger.info("������Ϣ�������֣�" + this.getRealMessage().substring(0,4));
		logger.info("������Ϣ����ˮ�ţ�" + this.getRealMessage().substring(4,6));
		logger.info("�Ƿ�ɹ���" + (this.getRealMessage().substring(6,8).equals("00")?"�ɹ�":"ʧ��"));
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
