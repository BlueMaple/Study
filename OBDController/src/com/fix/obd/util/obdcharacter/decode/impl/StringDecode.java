package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class StringDecode implements Decode{
	private String messageStr;
	public StringDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		System.out.println(cha.getCname() + "\t" + messageStr + "\t" + cha.getCnotice());
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
