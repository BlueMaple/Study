package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class UploadPositionDecode implements Decode {
	private String messageStr;
	public UploadPositionDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		if("ffff".equals(messageStr)){
			System.out.println(cha.getCname() + "\t" + "以里程间隔连续存储或者回传位置信息" + "\t" + cha.getCnotice());
		}
		else{
			int save_interval_time = Integer.valueOf(messageStr.substring(0,4),16);
			int reply_times = Integer.valueOf(messageStr.substring(4,8),16);
			System.out.println(cha.getCname() + "\t" + "以" + save_interval_time + "KM为回传间隔存储位置信息，回传" + reply_times + "次" + "\t" + cha.getCnotice());
		}
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
