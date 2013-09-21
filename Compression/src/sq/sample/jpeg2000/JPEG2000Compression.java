package sq.sample.jpeg2000;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JPEG2000Compression {
	private byte[] dcm;
	private byte[] encodedDcm;
	
	private int[] pixel; 
	private int WIDTH = 888;
	private int LENGTH = 587;
	private int byteToPixel = 2;

	public JPEG2000Compression(byte[] test , int wid , int leng , int btp) {
		// TODO Auto-generated constructor stub
		WIDTH = wid;
		LENGTH = leng;
		byteToPixel = btp;
		
		dcm = test;
		pixel = new int[test.length / byteToPixel];
		
		changeByteToPixel();
	}
	
	//�����õĹ��췽��
	public JPEG2000Compression(int[] test , int wid , int leng , int btp){
		pixel = test;
		WIDTH = wid;
		LENGTH = leng;
		byteToPixel = btp;
	}
	
	//��byteת��ΪPixel
	private void changeByteToPixel() {
		// TODO Auto-generated method stub
		int pixelSize = pixel.length;
		for(int i = 0 ; i < pixelSize ; i++){
			for(int k = 0 ; k < byteToPixel ; k++){
				pixel[i] += dcm[byteToPixel*i + k];
				pixel[i] <<= 8;
//				pixel[i] += dcm[byteToPixel*i+k];

			}
		}
	}

	public void compress(){
		//�Ȱ�ÿ�н���С���任
		for(int L = 0 ; L < LENGTH ; L++){
			int[] newRow = createNewRow(WIDTH , L);
			waveLeCall5_3(newRow);
			resetRowPixel(newRow , WIDTH , L);
		}
		
		//���н���С���任
		for(int W = 0 ; W < WIDTH ; W++){
			int[] newCol = createNewColumn(LENGTH , W);
			waveLeCall5_3(newCol);
			resetColumnPixel(newCol , LENGTH , W);
		}
		
		huffmanEncode();
		
//		����--���--����
//		for(int k = 0 ; k < LENGTH ; k++){
//			for(int m = 0 ; m < WIDTH ; m++){
//				System.out.print(pixel[k*WIDTH+m]+" ");
//			}
//			System.out.println();
//		}
		
//		printToText(pixel);
		
	}

	//��Pixel���byte����ͬʱbyte��֮ǰҪ���϶�Ӧ���ֵ䣡��
	private void huffmanEncode() {
		// TODO Auto-generated method stub
		HuffmanEncode huffEncode = new HuffmanEncode(pixel);
		huffEncode.encode();
		
		ArrayList<Byte> byteList = new ArrayList<Byte>();
		huffEncode.dictionaryToByte(byteList , byteToPixel);
		huffEncode.pixelToByte(byteList , byteToPixel);
	}

	//С���任�����õ���LeCall(5,3)�˲���
	private void waveLeCall5_3(int[] array){
		//�����е������Ƚ��б任
		for(int index = 1 ; index < array.length ; index+=2){
			array[index] = array[index] - 
					((int)(array[index-1]/2.0 + array[index+1]/2.0));
		}
		//ż���е����ֽ��б任��ע���һ�������һ��������Ҫ�任�����Դ�2ѭ����length-1
		for(int index = 2 ; index < array.length-1 ; index+=2){
			array[index] = array[index] +
					((int)((2 + array[index-1] + array[index+1])/4.0));
		}

	}
	
	//��newRow�ж�Ӧ��ֵ��д��Pixel��ȥ
	private void resetRowPixel(int[] newRow, int realWidth, int L) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < realWidth ; i++){
			pixel[L*realWidth+i] = newRow[i+2];
		}
	}
	
	//��newCol�ж�Ӧ��ֵ��д��Pixel��ȥ
	private void resetColumnPixel(int[] newCol, int realLength, int W) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < realLength ; i++){
			pixel[i*WIDTH+W] = newCol[i+2];
		}
	}
	
	//����ʵ�ʸ߶ȵ���ż���½�һ�е�������ʵ��С���任
	private int[] createNewColumn(int realLength, int W) {
		// TODO Auto-generated method stub
		int[] result = null;
		if(realLength %2 == 0){
			result = new int[realLength+3];
			result[0] = pixel[2*WIDTH+W];
			result[1] = pixel[1*WIDTH+W];
			result[result.length-1] = pixel[(realLength-2)*WIDTH+W];
		}
		else{
			result = new int[realLength+4];
			result[0] = pixel[2*WIDTH+W];
			result[1] = pixel[1*WIDTH+W];
			result[result.length-1] = pixel[(realLength-3)*WIDTH+W];
			result[result.length-2] = pixel[(realLength-2)*WIDTH+W];
		}
		
		for(int i = 0 ; i < realLength ; i++){
			result[i+2] = pixel[i*WIDTH+W];
		}
		
		return result;
	}


	//����ʵ�ʿ�ȵ���ż���½�һ�е�������ʵ��С���任
	private int[] createNewRow(int realWidth , int L) {
		// TODO Auto-generated method stub
		int[] result = null;
		if(realWidth % 2 == 0){
			result = new int[realWidth+3];
			result[0] = pixel[L*realWidth+2];
			result[1] = pixel[L*realWidth+1];
			result[result.length-1] = pixel[L*realWidth+realWidth-2];
		}
		else{
			result = new int[realWidth+4];
			result[0] = pixel[L*realWidth+2];
			result[1] = pixel[L*realWidth+1];
			result[result.length-2] = pixel[L*realWidth+realWidth-2];
			result[result.length-1] = pixel[L*realWidth+realWidth-3];
		}
		
		for(int i = 0 ; i < realWidth ; i++){
			result[i+2] = pixel[L*realWidth+i];
		}

		return result;
	}
	
	//�����������Txt�ļ���ȥ
	private void printToText(int[] result) {
		// TODO Auto-generated method stub
//		File resultFile = new File("result.txt");
//		try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
//			
//			for(int k = 0 ; k < LENGTH ; k++){
//				for(int m = 0 ; m < WIDTH ; m++){
//					writer.write(pixel[k*WIDTH+m]+"\t");
//				}
//				writer.write("\n");
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		int max = 0;
		int min = 0;
		int countMax = 0;
		Map<Integer , Integer> count = new HashMap<Integer , Integer>();
		for(int k = 0 ; k < LENGTH ; k++){
			for(int m = 0 ; m < WIDTH ; m++){
				if(pixel[k*WIDTH+m] > max){
					max = pixel[k*WIDTH+m];
				}
				if(pixel[k*WIDTH+m] < min){
					min = pixel[k*WIDTH+m];
				}
				if(!count.containsKey(pixel[k*WIDTH+m])){
					count.put(pixel[k*WIDTH+m], 1);
				}
				else{
					int temp = count.get(pixel[k*WIDTH+m]);
					count.put(pixel[k*WIDTH+m], temp+1);
					if(temp+1 > countMax){
						countMax = temp+1;
					}
				}
			}
		}
		
		System.out.println("Max value: "+max);
		System.out.println("Min value: "+min);
		System.out.println("All count: "+count.size());
		System.out.println("Max frequent: "+countMax);
	}

}
