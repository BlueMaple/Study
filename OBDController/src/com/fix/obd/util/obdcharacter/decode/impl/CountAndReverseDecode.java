package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.MessageUtil;
import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class CountAndReverseDecode implements Decode{
	private String messageStr;
	public CountAndReverseDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		int length = Integer.valueOf(messageStr.substring(0,2),16);
		String restOfStr = messageStr.substring(2);
		restOfStr = MessageUtil.reverseStr(restOfStr);
		String realStr = restOfStr.substring(0,length);
		realStr = MessageUtil.reverseStr(realStr);
		System.out.println(cha.getCname() + "\t" + realStr + "\t" + cha.getCnotice());
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
