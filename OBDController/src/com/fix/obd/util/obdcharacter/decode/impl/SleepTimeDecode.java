package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class SleepTimeDecode implements Decode{
	private String messageStr;
	public SleepTimeDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		int not_sleep_time = Integer.valueOf(messageStr.substring(0,4));
		int sleep_time = Integer.valueOf(messageStr.substring(4,8));
		System.out.println(cha.getCname() + "\t√ª–›√ﬂ ±£∫" + not_sleep_time + "s\t–›√ﬂ ±£∫" + sleep_time + "s\t" + cha.getCnotice());
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
