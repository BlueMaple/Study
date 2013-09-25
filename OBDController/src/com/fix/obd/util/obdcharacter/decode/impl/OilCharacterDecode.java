package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class OilCharacterDecode implements Decode {
	private String messageStr;
	public OilCharacterDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		double swept_volume = Integer.valueOf(messageStr.substring(0,2))*0.1;
		int volume_efficienty = Integer.valueOf(messageStr.substring(2,4));
		System.out.println(cha.getCname() + "\t排量" + swept_volume + "L\t容积效率" + volume_efficienty + "\t" + cha.getCnotice());
	}
	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub

	}
}
