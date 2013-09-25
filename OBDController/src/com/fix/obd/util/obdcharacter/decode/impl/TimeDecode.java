package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class TimeDecode implements Decode{
	private String messageStr;
	public TimeDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		String date = messageStr.substring(0,2) + "-" + 
				messageStr.substring(2,4) + "-" +
				messageStr.substring(4,6) + " " + 
				messageStr.substring(6,8) + ":" +
				messageStr.substring(8,10) + ":" + 
				messageStr.substring(10,12);
		String cname = cha.getCname();
		String cnotice = cha.getCnotice();
		System.out.println(cname + "\t" + date + "\t" + cnotice);
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}
}
