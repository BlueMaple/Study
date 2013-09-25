package com.fix.obd.util.obdcharacter.decode.impl;

import java.util.ArrayList;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class ACCTimeDecode implements Decode{
	private String messageStr;
	public ACCTimeDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		if("ffff".equals(messageStr)){
			System.out.println(cha.getCname() + "\t" + "以间隔时间连续存储位置信息，默认间隔为10s陆续存储" + "\t" + cha.getCnotice());
		}
		else{
			int save_interval_time = Integer.valueOf(messageStr.substring(0,4),16);
			int reply_times = Integer.valueOf(messageStr.substring(4,8),16);
			System.out.println(cha.getCname() + "\t" + "以" + save_interval_time + "s为时间间隔存储位置信息，回传" + reply_times + "次" + "\t" + cha.getCnotice());
		}
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
