package com.sq;

public class ASCIIByteDecoder extends ByteDecoder{

	@Override
	public String decode(String source, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStringValue(String effString) {
		// TODO Auto-generated method stub
		int result = 0;
		result += changeHexadecimalToDecimal(effString.charAt(0))*16 +
				changeHexadecimalToDecimal(effString.charAt(1));
		return result;
	}

}
