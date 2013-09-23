package com.sq;

public class SignedByteDecoder extends ByteDecoder{

	@Override
	public String decode(String source, int length) {
		// TODO Auto-generated method stub
		int result = 0;
		for(int i = 0 ; i < source.length() ; i++){
			int cInteger = changeHexadecimalToDecimal(source.charAt(i));
			cInteger = 16 - cInteger;
			result += cInteger * Math.pow(16, source.length()-1-i);
		}
		result += 1;
		return (0-result)+"";
	}

}
