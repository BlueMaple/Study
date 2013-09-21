package sq.sample.rle;

import java.util.ArrayList;

public class RLECompression {
	public byte[] compression(byte[] pixel){
		ArrayList<Byte> compPixel = new ArrayList<Byte>();
		int size = pixel.length;
		
		int index = 1;
		byte front = pixel[0];
		int count = 1;
		while(index < size){
			byte current = pixel[index];
			if(current == front && count < 255){
				count++;
			}
			else{
				compPixel.add((byte)count);
				compPixel.add(front);
				
				front = current;
				count = 1;
			}
			
			index++;
		}
		compPixel.add((byte)count);
		compPixel.add(front);
		
		byte[] result = new byte[compPixel.size()];
		for(int i = 0 ; i < result.length ; i++)
			result[i] = compPixel.get(i);
		
		
		System.out.println("RLECompressed:"+result.length);
		return result;
	}
}
