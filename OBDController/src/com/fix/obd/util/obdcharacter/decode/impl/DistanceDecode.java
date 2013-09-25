package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class DistanceDecode implements Decode{
	private String messageStr;
	public DistanceDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		if("ffffffff".equals(messageStr)){
			System.out.println(cha.getCname() + "\t" + "ά����̲���" + "\t" + cha.getCnotice());
		}
		else{
			int recent_miles = Integer.valueOf(messageStr.substring(0,8),16);
			int fit_time_miles = Integer.valueOf(messageStr.substring(8,16),16);
			int next_fit_miles = Integer.valueOf(messageStr.substring(16,24),16);
			System.out.println(cha.getCname() + "\t" + "��ǰ���Ϊ" + recent_miles + "����\t�������" + fit_time_miles + "����\t�´α��������" + next_fit_miles + "����"+ cha.getCnotice());
		}
	}
	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}
}
