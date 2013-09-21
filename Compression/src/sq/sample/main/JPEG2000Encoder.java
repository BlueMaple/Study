package sq.sample.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/*
 * ~����ԭ�е��������飬�½�һ��Int�������
 * ~С���任���������س������1��or3��С���任
 * ~huffman���룬
 * ~���Byte��������codeDictionary�ĳ��ȡ�codeDictionary�����ر������Ľ��
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
		//��ÿһ��Piece�Ƚ���timer�ε�С���任��Ȼ�󽫱任������ݷ�д��data��ȥ
//		for(int pieceIndex = 0 ; pieceIndex < PIECE ; pieceIndex++){
//			int[] pieceData = getPieceData(pieceIndex);
//			wavelet.transform(pieceData , WIDTH , LENGTH , timer);
//			setPieceData(pieceData , pieceIndex);
//		}
		
		//���������ݽ���Huffman����
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
	 * ~ʹ��StringBuilder����������ı�����
	 * ~ȥ����ͷdata_8λbit���㿪ʼbody���ֵı���
	 * ~����byte��ȡ�����������뵽�������ȥ
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

	/*~��CodDictionary�Ĵ�С������д��byte��ͷ��
	 *~KEY���з���������VALUE���޷�������
	 *~Tag����һ��Byte��ǰ��λ��ʾKEY��ռ���ֽڣ�����λ��ʾVALUE��ռ���ֽ�
	 *~dictionary������ʱ�����һ������Tag
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
	 * ~����õ���result��Զ������
	 * ~�������ʱ�򣬼ǵ�ȥ����ͷ��value_8λbit����ʵ�ʵ�value
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
	 * ~���(1111,1111)��byte��ΪHeader�Ľ���Tag
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

	//��pieceData�����ݷ�д��data��
	private void setPieceData(int[] pieceData, int pieceIndex) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < pieceData.length ; i++){
			data[pieceIndex*WIDTH*LENGTH + i] = pieceData[i];
		}
	}

	//��ȡ��֡ͼ��ĳһ֡ͼƬ
	private int[] getPieceData(int pieceIndex) {
		// TODO Auto-generated method stub
		int[] pieceData = new int[WIDTH*LENGTH];
		for(int i = 0 ; i < pieceData.length ; i++){
			pieceData[i] = data[pieceIndex*WIDTH*LENGTH + i];
		}
		return pieceData;
	}

	//���Է���
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
