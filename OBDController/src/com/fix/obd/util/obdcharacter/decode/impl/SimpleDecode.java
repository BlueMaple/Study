package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class SimpleDecode implements Decode{
	private String messageStr;
	public SimpleDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		int character_int = Integer.valueOf(messageStr,16);
		String cname = cha.getCname();
		String cnotice = cha.getCnotice();
		System.out.println(cname + "\t" + character_int + "\t" + cnotice);
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}
}
