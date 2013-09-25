package com.fix.obd.util;

public class MessageUtil {
	public static String buildCheckNode(ResponseStrMaker response){
		String strForCheckNode = response.getId() + response.getBufferId() + response.getLength() + response.getMessageBody();
		byte[] checkBytes = BytesOperater.HexString2Bytes(strForCheckNode);
		BytesOperater.printHexString(strForCheckNode, checkBytes);
		strForCheckNode = BytesOperater.Byte2HexString(BytesOperater.BCC(checkBytes));
		return strForCheckNode;
	}
	public static byte[] buildOutputStream(ResponseStrMaker response) {
		String responseStr = response.buildResponse();
		byte[] protocolBytes = BytesOperater
				.HexString2Bytes(responseStr);
		return protocolBytes;
	}
	public static String reverseStr(String str){
		StringBuffer buffer = new StringBuffer(str);
		StringBuffer result = buffer.reverse();
		return result.toString();
	}
}
