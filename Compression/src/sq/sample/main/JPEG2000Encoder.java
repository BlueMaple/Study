package sq.sample.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/*
 * ~保留原有的像素数组，新建一个Int数组操作
 * ~小波变换，根据像素长宽进行1次or3次小波变换
 * ~huffman编码，
 * ~组成Byte流，包括codeDictionary的长度、codeDictionary和像素编码过后的结果
 */
public class JPEG2000Encoder {
	private byte[] originalData;
	private int WIDTH;
	private int LENGTH;
	private int PIECE;
	
	private int[] data;
	private WaveletTransform wavelet;
	private HuffmanEncoder huffman;
	
	private ArrayList<Byte> resultData;
	
	public JPEG2000Encoder(byte[] data, int w, int l, int p) {
		// TODO Auto-generated constructor stub
		originalData = data;
		WIDTH = w;
		LENGTH = l;
		PIECE = p;
		
		createEncodeData();
		
		wavelet = new WaveletTransform();
		resultData = new ArrayList<Byte>();
		huffman = new HuffmanEncoder();
	}

	private void createEncodeData() {
		// TODO Auto-generated method stub
		data = new int[originalData.length];
		for(int i = 0 ; i < data.length ; i++)
			data[i] = originalData[i];
	}

	public byte[] starEncode(int timer) {
		// TODO Auto-generated method stub
		//对每一个Piece先进行timer次的小波变换，然后将变换后的数据反写到data中去
//		for(int pieceIndex = 0 ; pieceIndex < PIECE ; pieceIndex++){
//			int[] pieceData = getPieceData(pieceIndex);
//			wavelet.transform(pieceData , WIDTH , LENGTH , timer);
//			setPieceData(pieceData , pieceIndex);
//		}
		
		//对整个数据进行Huffman编码
		huffman.startEncode(data);
		
		createCodeDictionaryHeader(huffman.getCodeDictionary());
		createCodeBody(huffman.getCodeDictionary());
		
		byte[] byteFlow = new byte[resultData.size()];
		for(int i = 0 ; i < byteFlow.length ; i++){
			byteFlow[i] = resultData.get(i);
		}
		return byteFlow;
	}

	/*
	 * ~使用StringBuilder来构造整体的编码结果
	 * ~去除开头data_8位bit才算开始body部分的编码
	 * ~按照byte拿取，计算结果加入到结果里面去
	 */
	private void createCodeBody(Map<Integer, String> codeDictionary) {
		// TODO Auto-generated method stub
		int dataLength = data.length;
		StringBuilder builder = new StringBuilder();
//		byte data_8 = (byte)(8 - (dataLength%8 == 0?8:dataLength%8));
//		resultData.add(data_8);
//		for(int i = 0 ; i < data_8 ; i++){
//			builder.append("0");
//		}
		for(int i = 0 ; i < dataLength ; i++){
			builder.append(codeDictionary.get(data[i]));
		}
		int strLength = builder.length();
		byte data_8 = (byte)(8 - (strLength%8 == 0?8:strLength%8));
		
		for(int i = 0 ; i < data_8 ; i++){
			builder.insert(0, '0');
		}
		System.out.println("String Body : "+builder.toString());
		
		int bytes = builder.length() / 8;
		for(int i = 0 ; i < bytes ; i++){
			String oneByte = builder.substring(i*8, i*8+8);
			int result = 0;
			for(int bit = 7 ; bit >= 0 ; bit--){
				int charNumber = oneByte.charAt(bit) - 48;
				result += charNumber * ((int)Math.pow(2, 7-bit));
			}
			resultData.add((byte)result);
			System.out.println("Body : "+result);
		}
	}

	/*~将CodDictionary的大小和内容写入byte流头部
	 *~KEY是有符号整数，VALUE是无符号整数
	 *~Tag：第一个Byte的前四位表示KEY所占的字节，后四位表示VALUE所占的字节
	 *~dictionary结束的时候添加一个结束Tag
	 */
	private void createCodeDictionaryHeader(Map<Integer, String> codeDictionary) {
		// TODO Auto-generated method stub
		for(Map.Entry<Integer, String> entry : codeDictionary.entrySet()){
			int key = entry.getKey();
			String value = entry.getValue();
			int keyBytes = getIntegerByte(key);
			int valueBytes = (value.length()/8) + (value.length()%8 == 0?0:1) + 1;
			
			insertTagBytes(keyBytes , valueBytes);
			insertIntegerBytes(keyBytes , key);
			insertStringBytes(valueBytes , value);
		}
		addEndHeaderFlag();
	}

	/*
	 * ~计算得到的result永远是正数
	 * ~反编码的时候，记得去除开头的value_8位bit才是实际的value
	 */
	private void insertStringBytes(int valueBytes, String value) {
		// TODO Auto-generated method stub
		byte value_8 = (byte)(8-(value.length()%8 == 0?8:value.length()%8));
		resultData.add(value_8);
		
		int length = value.length();
		int result = 0;
		for(int i = length-1 ; i >= 0 ; i--){
			int charNumber = value.charAt(i) - 48;
			result += charNumber * ((int)Math.pow(2, length-1-i));
		}

		insertIntegerBytes(valueBytes-1 , result);
	}

	private void insertIntegerBytes(int keyBytes, int key) {
		// TODO Auto-generated method stub
		for(int i = keyBytes-1 ; i >= 0 ; i--){
			byte result = (byte)(key >> (i*8));
			resultData.add(result);
		}
	}

	private void insertTagBytes(int keyBytes, int valueBytes) {
		// TODO Auto-generated method stub
		byte result = (byte)keyBytes;
		result <<= 4;
		result += (byte)valueBytes;
		
		resultData.add(result);
	}

	/*
	 * ~添加(1111,1111)的byte作为Header的结束Tag
	 */
	private void addEndHeaderFlag() {
		// TODO Auto-generated method stub
		resultData.add((byte)-1);
	}

	private int getIntegerByte(int number) {
		// TODO Auto-generated method stub
		int bytes = 0;
		int maxBytes = 0;
		int minBytes = 0;
		do{
			bytes++;
			maxBytes = (int)(Math.pow(2, 8*bytes)/2 - 1);
			minBytes = (int)(0-Math.pow(2, 8*bytes)/2);
		}while(number < minBytes || number > maxBytes);
		
		return bytes;
	}

	//将pieceData的数据反写到data中
	private void setPieceData(int[] pieceData, int pieceIndex) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < pieceData.length ; i++){
			data[pieceIndex*WIDTH*LENGTH + i] = pieceData[i];
		}
	}

	//获取多帧图的某一帧图片
	private int[] getPieceData(int pieceIndex) {
		// TODO Auto-generated method stub
		int[] pieceData = new int[WIDTH*LENGTH];
		for(int i = 0 ; i < pieceData.length ; i++){
			pieceData[i] = data[pieceIndex*WIDTH*LENGTH + i];
		}
		return pieceData;
	}

	//测试方法
	private void print(int pieceIndex , int[] pieceData) {
		// TODO Auto-generated method stub
		if(pieceIndex != 0)
			return;
		
		File resultFile = new File("piece0.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
			
			for(int k = 0 ; k < LENGTH ; k++){
				for(int m = 0 ; m < WIDTH ; m++){
					writer.write(pieceData[k*WIDTH+m]+"\t");
				}
				writer.write("\n");
			}

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
