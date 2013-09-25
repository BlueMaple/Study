package com.fix.obd.protocol.impl;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.protocol.ODBProtocolParser;
import com.fix.obd.util.MessageUtil;
import com.fix.obd.util.ProtocolPropertiesUtil;

public class PositionInfo extends ODBProtocolParser implements ODBProtocol{
	private static final Logger logger = Logger.getLogger(PositionInfo.class);
	private String clientId;
	private String bufferId;
	public PositionInfo(String messageStr){
		super(messageStr);
		logger.info("收到来自终端" + this.getId() + "的位置信息");
	}
	@Override
	public boolean DBOperation() {
		// TODO Auto-generated method stub
		try {
			this.clientId = this.getId();
			this.bufferId = this.getBufferId();
			String message = this.getRealMessage();
//			System.out.println(message);
			String gpsDate = message.substring(0,2) + "-" + message.substring(2,4) + "-" + message.substring(4,6) + " " + message.substring(6,8) + ":" + message.substring(8,10) + ":" + message.substring(10,12);
			System.out.println("GPS时间" + gpsDate);
			String gpsAlert = message.substring(12,16);
//			System.out.println(gpsAlert);
			String alertInBinary = Integer.toBinaryString(Integer.valueOf(gpsAlert,16));
			alertInBinary = String.format("%016d", Integer.parseInt(alertInBinary));
			alertInBinary = MessageUtil.reverseStr(alertInBinary);
			System.out.println("-----------------------");
			String[] alerts = {"sos报警","超速报警","若GPS不定位，则基站信息是cellied+LAC","电池欠压","电池过压","进矩阵区域报警","出矩阵区域报警","碰撞报警","保养期报警","温高报警","vin错误报警","Obd故障严重报警","震动报警","gsm经纬度格式"};
			for(int i=0;i<alerts.length;i++){
				if(alertInBinary.charAt(i)=='1')
					System.out.println(alerts[i]);
			}
			String gpsState = message.substring(16,18);
			String statusInBinary = Integer.toBinaryString(Integer.valueOf(gpsState,16));
			statusInBinary = String.format("%08d", Integer.parseInt(statusInBinary));
			statusInBinary = MessageUtil.reverseStr(statusInBinary);
			System.out.println("-----------------------");
			System.out.println(alertInBinary);
			System.out.println(statusInBinary);
			String[][] statuses = {
					{"ACC开","ACC关"},
					{"GPS定位","GPS不定位"},
					{"北纬","南纬"},
					{"东经","西经"},
					{"疲劳驾驶","疲劳驾驶检测正常"},
					{"Obd故障","Obd未发生故障"},
					{"实时位置信息","非实时位置信息"},
					{"设备已插","设备未插"}
			};
			for(int i=0;i<statuses.length;i++){
				System.out.println(statusInBinary.charAt(i)=='1'?statuses[i][0]:statuses[i][1]);
			}
			char celidLACorNot = alertInBinary.charAt(2);
			char GPSorNot = statusInBinary.charAt(1);
//			System.out.println(GPSorNot + " " + celidLACorNot);
			if(GPSorNot=='1'){
				GPSOrientate gps = new GPSOrientate(message.substring(18));
			}
			else{
				if(celidLACorNot=='1'){
					CeliidLACOrientate cellac = new CeliidLACOrientate(message.substring(18));
				}
				else if(celidLACorNot=='0'){
					Celiid7Orientate cel = new Celiid7Orientate(message.substring(18));
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.getLocalizedMessage());
			return false;
		}
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
//	public static void main(String args[]){
//		PositionInfo p = new PositionInfo("0000000861825486344109001500040010100016100004410067c951d901cc00005fa3");
//		p.DBOperation();
//	}

	private class GPSOrientate{
		private String partOfStr;
		public GPSOrientate(String str){
			this.partOfStr = str;
			this.print();
		}
		public void print(){
			int gpsSatelliteNum = Integer.valueOf(partOfStr.substring(0,2),16);
			System.out.println("卫星个数：" + gpsSatelliteNum);
			String longitute = partOfStr.substring(2,5)+ "°" + partOfStr.substring(5,7) + "." + partOfStr.substring(7,10);
			System.out.println("经度：" + longitute);
			String latitude = partOfStr.substring(10,12) + "°" + partOfStr.substring(12,14) + "." + partOfStr.substring(14,18);
			System.out.println("纬度：" + latitude);
			int degree = Integer.valueOf(partOfStr.substring(18,20),16)*2;
			System.out.println("方向角：" + degree + "°");
			String GPSSpeed = Integer.valueOf(partOfStr.substring(20,22),16).toString();
			System.out.println("GPS速度：" + GPSSpeed + "km/s");
			String OBDSpeed = Integer.valueOf(partOfStr.substring(22,24),16).toString();
			System.out.println("OBD速度：" + OBDSpeed + "km/s");
			String engineWaterTp = Integer.valueOf(partOfStr.substring(24,26),16).toString();
			System.out.println("发动机水温：" + engineWaterTp + "摄氏度");
			String extra = partOfStr.substring(26);
			System.out.println("附加位置信息：" + extra);
		}
	}
	private class Celiid7Orientate{
		private String partOfStr;
		public Celiid7Orientate(String str){
			this.partOfStr = str;
			this.print();
		}
		public void print(){
			String GSMInfo = "";
			String GSMMessage = partOfStr.substring(0,14);
			for(int i=0;i<7;i++){
				String temp_GSM = GSMMessage.substring(i*2,(i+1)*2);
				if(i==0)
					GSMInfo += "主CellID S：" + Integer.valueOf(temp_GSM,16) + "\t";
				else
					GSMInfo += "N" + i + "：" + Integer.valueOf(temp_GSM,16) + "\t";
			}
			System.out.println(GSMInfo);String OBDSpeed = Integer.valueOf(partOfStr.substring(14,16),16).toString();
			System.out.println("OBD速度：" + OBDSpeed + "km/s");
			String engineWaterTp = Integer.valueOf(partOfStr.substring(16,18),16).toString();
			System.out.println("发动机水温：" + engineWaterTp + "摄氏度");
			String extra = partOfStr.substring(18);
			System.out.println("附加位置信息：" + extra);
		}
	}
	private class CeliidLACOrientate{
		private String partOfStr;
		public CeliidLACOrientate(String str){
			this.partOfStr = str;
			this.print();
		}
		public void print(){
			String celiidInfo = Integer.valueOf(partOfStr.substring(0,6),16).toString();
			System.out.println("Cellid：" + celiidInfo);
			String lacInfo = Integer.valueOf(partOfStr.substring(6,10),16).toString();
			System.out.println("lac：" + lacInfo);
			String mcc = Integer.valueOf(partOfStr.substring(10,14),16).toString();
			System.out.println("移动用户所属国家代码：" + mcc);
			String mnc = Integer.valueOf(partOfStr.substring(14,16),16).toString();
			System.out.println("移动网号码：" + mnc);
			String OBDSpeed = Integer.valueOf(partOfStr.substring(16,18),16).toString();
			System.out.println("OBD速度：" + OBDSpeed + "km/s");
			String engineWaterTp = Integer.valueOf(partOfStr.substring(18,20),16).toString();
			System.out.println("发动机水温：" + engineWaterTp + "摄氏度");
			String extra = partOfStr.substring(20);
			System.out.println("附加位置信息：" + extra);
		}
	}
}
