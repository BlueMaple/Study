package com.sq;

public class ASCIIByteDecoder extends ByteDecoder{

	@Override
	public String decode(String source, int length) {
		// TODO Auto-generated method stub
		String result = "";
		for(int i = 0 ; i < source.length()/2 ; i+=2){
			char oneChar = (char)((source.charAt(i)-'0')*10 + (source.charAt(i+1) - '0'));
			result += oneChar;
		}
		return result;
	}

	public int getStringValue(String effString) {
		// TODO Auto-generated method stub
		int result = 0;
		result += changeHexadecimalToDecimal(effString.charAt(0))*16 +
				changeHexadecimalToDecimal(effString.charAt(1));
		return result;
	}

}
