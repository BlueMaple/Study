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
			oil_type = "未知原油类型";
		else if("01".equals(oil_type_info))
			oil_type = "汽油";
		else if("02".equals(oil_type_info))
			oil_type = "柴油A";
		else if("03".equals(oil_type_info))
			oil_type = "柴油B";
		else if("04".equals(oil_type_info))
			oil_type = "混合动力";
		else if("05".equals(oil_type_info))
			oil_type = "液态丙烷";
		double swept_volume = Integer.valueOf(messageStr.substring(2,4),16) * 0.1;
		String power_type = "";
		String power_type_info = messageStr.substring(4,6);
		if("00".equals(power_type_info))
			power_type = "不支持涡轮增压";
		else if("01".equals(power_type_info))
			power_type = "支持涡轮增压";
		System.out.println(cha.getCname() + "\t" + oil_type + "\t" + swept_volume + "L" + "\t" + power_type + "\t" + cha.getCnotice());
	}

	@Override
	public void DBOperation(CharacterIterator cha) {
		// TODO Auto-generated method stub
		
	}

}
