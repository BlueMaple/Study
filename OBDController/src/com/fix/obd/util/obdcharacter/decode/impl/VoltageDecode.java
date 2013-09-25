package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class VoltageDecode implements Decode{
	private String messageStr;
	public VoltageDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		int character_int = Integer.valueOf(messageStr,16);
		double character_double = character_int*0.1;
		String cname = cha.getCname();
		String cnotice = cha.getCnotice();
		System.out.println(cname + "\t" + character_double + "\t" + cnotice);
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}
}
