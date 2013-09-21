package sq.sample.main;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sq.sample.jpeg2000.HuffmanEncode;
import sq.sample.jpeg2000.JPEG2000Compression;
import sq.sample.rle.RLECompression;

public class TestMain {
	static byte[] elements;
	public static void main(String[] args){
//		Segments_Intersect test = new Segments_Intersect();
//		float[] testArray = new float[]{160,104,158,(float) 102.5,83,(float) 103.5,(float) 79.5,107};
//		boolean result = test.intersect(testArray);
		
		String subName = "S.13医疗费用";
		String targetString = subName.replaceAll("[a-zA-Z]+|\\d+", "").replace(".", "");
		System.out.println(targetString);
//		for(int i = 0 ; i < 2*line ; i++){
//			for(int j = 0 ; j < 2*line ; j++){
//				System.out.print(result[i*2*line+j]+",");
//			}
//			System.out.println();
//		}
		
//		Map<String , String> encodeMap = readLine("F:\\WorkSpace\\JPEG2000Compression\\EncoderMap.txt");
//		Map<String , String> decodeMap = readLine("F:\\WorkSpace\\JPEG2000Compression\\DecoderMap.txt");
//
//		for(Map.Entry<String, String> entry : encodeMap.entrySet()){
//			if(!decodeMap.containsKey(entry.getKey())){
//				System.out.println("DecodeMap is not equal to encodeMap");
//			}
//			else if(!entry.getValue().equals(decodeMap.get(entry.getKey()))){
//				System.out.println("!EncodeMap-"+entry.getKey()+"-"+entry.getValue());
//				System.out.println("!DecodeMap-"+entry.getKey()+"-"+decodeMap.get(entry.getKey()));
//			}
//			System.out.println("EncodeMap-"+entry.getKey()+"-"+entry.getValue());
//			System.out.println("DecodeMap-"+entry.getKey()+"-"+decodeMap.get(entry.getKey()));
//		}
	}
	
	private static Map<String , String> readLine(String fileName){
		Map<String , String> result = new HashMap<String , String>();
		try {
			FileInputStream input = new FileInputStream(fileName);
			DataInputStream dis = new DataInputStream(input);
			String l = "";
			while((l = dis.readLine()) != null){
				String[] split = l.split("-");
				if(split.length == 2){
					result.put(split[0], split[1]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	private static String[] readText(String fileName){
		byte[] demo = new byte[11088657];
		String result = null;
		try {
			FileInputStream input = new FileInputStream(fileName);
			DataInputStream dis = new DataInputStream(input);
			System.out.println(dis.read(demo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = new String(demo);
		return result.split(",");
	}
	
	private static void reCreateFile(byte[] result) {
		// TODO Auto-generated method stub
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("F:\\WorkSpace\\Compression\\ResultDICOM.DCM");
			fos.write(elements);
			fos.write(result);

			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static byte[] createRandomByteArray(){
		byte[] demo = new byte[1000000];
		for(int i = 0 ; i < 1000000 ; i++){
			
		}
		return demo;
	}
	
	//华大宝提供的范例：1042512个byte
	private static byte[] readRealDICOMByteArray(int pixelSize , int elementSize){
		byte[] demo = new byte[pixelSize];
		elements = new byte[elementSize];
		
		String filePath = "F:\\WorkSpace\\Compression\\"
				+ "1.2.840.113619.2.55.3.2831193967.596.1285460208.410.1.DCM";
		try {
			FileInputStream input = new FileInputStream(filePath);
			DataInputStream dis = new DataInputStream(input);
			
			//去掉开头不需要压缩的byte的数据
			System.out.println(dis.read(elements));
			System.out.println(dis.read(demo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return demo;
	}
}
