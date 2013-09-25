package com.fix.obd.util.obdcharacter.decode.impl;

import com.fix.obd.util.model.CharacterIterator;
import com.fix.obd.util.obdcharacter.decode.Decode;

public class ConfigOilInfoDecode implements Decode{
	private String messageStr;
	public ConfigOilInfoDecode(String messageStr){
		this.messageStr = messageStr;
	}
	@Override
	public void print(CharacterIterator cha) {
		// TODO Auto-generated method stub
		String oil_type = "";
		String oil_type_info = messageStr.substring(0,2);
		if("00".equals(oil_type_info))
			oil_type = "δ֪ԭ������";
		else if("01".equals(oil_type_info))
			oil_type = "����";
		else if("02".equals(oil_type_info))
			oil_type = "����A";
		else if("03".equals(oil_type_info))
			oil_type = "����B";
		else if("04".equals(oil_type_info))
			oil_type = "��϶���";
		else if("05".equals(oil_type_info))
			oil_type = "Һ̬����";
		double swept_volume = Integer.valueOf(messageStr.substring(2,4),16) * 0.1;
		String power_type = "";
		String power_type_info = messageStr.substring(4,6);
		if("00".equals(power_type_info))
			power_type = "��֧��������ѹ";
		else if("01".equals(power_type_info))
			power_type = "֧��������ѹ";
		System.out.println(cha.getCname() + "\t" + oil_type + "\t" + swept_volume + "L" + "\t" + power_type + "\t" + cha.getCnotice());
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
