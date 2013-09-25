package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class ASCIIDecode implements Decode{
	private String messageStr;
	public ASCIIDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		String urlMessage = "";
		int length = messageStr.length()/2;
		for(int i=0;i<length;i++){
			String cutStr = messageStr.substring(2*i,2*i+2);
			int ascii = Integer.valueOf(cutStr,16);
			char realStr = (char)ascii;
			urlMessage += realStr;
		}
		System.out.println(cha.getCname() + "\t" + urlMessage + "\t" + cha.getCnotice());
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
