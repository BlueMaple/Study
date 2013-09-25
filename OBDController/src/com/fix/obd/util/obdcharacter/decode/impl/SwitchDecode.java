package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class SwitchDecode implements Decode{
	private String messageStr;
	public SwitchDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		String[] switches = cha.getCnotice().split(";");
		String content = "";
		for(int i=0;i<switches.length;i++){
			String[] choiceandcontent = switches[i].split(":");
			if(messageStr.equals(choiceandcontent[0]))
				content = choiceandcontent[1];
		}
		System.out.println(cha.getCname() + "\t" + content);
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}
	
}
