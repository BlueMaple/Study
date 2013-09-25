package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class TenMinTimeDecode implements Decode{
	private String messageStr;
	public TenMinTimeDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		int character_int = Integer.valueOf(messageStr,16);
		String cname = cha.getCname();
		String cnotice = cha.getCnotice();
		System.out.println(cname + "\t" + character_int*10 + "min\t" + cnotice);
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}
}
