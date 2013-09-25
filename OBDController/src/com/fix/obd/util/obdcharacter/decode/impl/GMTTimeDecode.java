package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class GMTTimeDecode implements Decode{
	private String messageStr;
	public GMTTimeDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		int hour = Integer.valueOf(messageStr.substring(0,2));
		int minute = Integer.valueOf(messageStr.substring(2,4));
		System.out.println(cha.getCname() + "\t�ڸ�������ʱ�����" + hour + "ʱ" + minute + "��\t" + cha.getCnotice());
	}
	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub

	}

}
