package com.fix.obd.protocol.impl;

import java.lang.reflect.Constructor;
import org.apache.log4j.Logger;
import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;
import com.fix.obd.util.ProtocolPropertiesUtil;
import com.fix.obd.util.obd.ASCIIByteDecoder;
import com.fix.obd.util.obd.ByteDecoder;
import com.fix.obd.util.obd.XMLReader;

public class UploadOBDInfo extends ODBProtocolParser implements ODBProtocol{
	private static final Logger logger = Logger.getLogger(UploadOBDInfo.class);
	private String clientId;
	private String bufferId;
	private XMLReader reader;


	public UploadOBDInfo(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的ODB信息查询请求信息");
		reader = new XMLReader();
	}
	
	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		this.clientId = this.getId();
		this.bufferId = this.getBufferId();
		
		String realMessage = this.getRealMessage();		
		String time = readTime(realMessage);
		
		boolean sign = true;
		if(sign){
			readEffectiveParameter(realMessage);
		}
		else{
			readParameter(realMessage);
		}
		
		return false;
	}
	
	private void readParameter(String message) {
		// TODO Auto-generated method stub
		int mapSize = reader.getMapSize();
		int effIndex = 12;
		for(int index = 0 ; index < mapSize ; index++){
			String name = reader.getElementName(index);
			int length = reader.getElementLength(index);
			String handler = reader.getElementHandler(index);

			if(length > 0){
				System.out.println(message.length()+"--"+effIndex+length*2);
				String effString = message.substring(effIndex , effIndex+length*2);
				effIndex += length*2;
				
				try {
					Constructor con = Class.forName("com.fix.obd.protocol.impl."+handler).getConstructor();
					ByteDecoder decoder = (ByteDecoder) con.newInstance();
					String result = decoder.decode(effString, length);
					
					logger.info("收到OBD信息-"+name+":"+result);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(length == -1 && handler.equals("ASCIIByteDecoder")){
				ASCIIByteDecoder decoder = new ASCIIByteDecoder();

				String effString = message.substring(effIndex , effIndex+2);
				effIndex += 2;
				int effInteger = decoder.getStringValue(effString);
				logger.info("收到OBD信息-"+name+":"+effInteger);
				effString = message.substring(effIndex, effIndex+effInteger*2);
				effIndex += effInteger*2;
				String result = decoder.decode(effString, effInteger);
				
				logger.info("收到OBD信息-"+name+":"+result);
			}

		}
	}

	private void readEffectiveParameter(String message) {
		// TODO Auto-generated method stub
		String eff = message.substring(12, 22);
		byte[] effBits = changeStringToBits(eff);
		int index = 0;
		int effIndex = 22;
		while(index < effBits.length){
			if(effBits[index] == 1){
				String name = reader.getElementName(index);
				int length = reader.getElementLength(index);
				String handler = reader.getElementHandler(index);

				if(length > 0){
					String effString = message.substring(effIndex , effIndex+length*2);
					effIndex += length*2;
					
					try {
						Constructor con = Class.forName("com.fix.obd.util.obd."+handler).getConstructor();
						ByteDecoder decoder = (ByteDecoder) con.newInstance();
						String result = decoder.decode(effString, length);
						
						logger.info("收到OBD信息-"+name+":("+length+")"+result);
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				else if(length == -1 && handler.equals("ASCIIByteDecoder")){
					ASCIIByteDecoder decoder = new ASCIIByteDecoder();

					String effString = message.substring(effIndex , effIndex+2);
					effIndex += 2;
					int effInteger = decoder.getStringValue(effString);
					effString = message.substring(effIndex, effIndex+effInteger*2);
					effIndex += effInteger*2;
					String result = decoder.decode(effString, effInteger);
					
					logger.info("收到OBD信息-"+name+":("+effInteger+")"+result);
				}
			}
			index++;
		}
	}

	private String readTime(String message){
		String timePart = message.substring(0, 12);
		logger.info("OBD状态信息-时间：" + timePart);
		
		return timePart;
	}
	
	private byte[] changeStringToBits(String eff) {
		// TODO Auto-generated method stub
		int length = eff.length();
		byte[] result = new byte[length*4];
		for(int i = 0 ; i < length ; i++){
			char c = eff.charAt(i);
			int cInteger = (c >= 58)?(c-87):(c-48);
			for(int k = 3 ; k >= 0 ; k--){
				if(cInteger >= Math.pow(2, k)){
					result[i*4+3-k] = 1;
					cInteger -= Math.pow(2, k);
				}
			}
		}
		for(int revise = 0 ; revise < length*2 ; revise++){
			byte temp = result[revise];
			result[revise] = result[result.length-1-revise];
			result[result.length-1-revise] = temp;
		}
		
		return result;
	}

	@Override
	public byte[] replyToClient() {
		// TODO Auto-generated method stub
		StackTraceElement[] stacks = new Throwable().getStackTrace(); 
		String classname =  stacks[0].getClassName().substring(stacks[0].getClassName().lastIndexOf(".")+1);
		ProtocolPropertiesUtil p = new ProtocolPropertiesUtil();
		String operationId = p.getIdByProtocol(classname);
		ServerAck serverACK = new ServerAck(clientId,bufferId,operationId);
		return serverACK.replyToClient();

	}

}
