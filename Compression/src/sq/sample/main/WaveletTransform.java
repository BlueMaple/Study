package sq.sample.main;

/*
 * ~����һ�����顢�任�ĳ��Ϳ�
 * ~ѡ����ʵ��˲������б任
 * ~���ֱ任ֱ���������ϲ���
 */
public class WaveletTransform {
	protected LeCall_5_3 filer;
	protected int[] data;
	
	protected int originalWidth;
	protected int originalLength;
	
	//Ĭ��ʹ��LeCall(5,3)���˲���
	public WaveletTransform(){
		filer = new LeCall_5_3();
	}

	//�任������timerָ���Ǳ任�Ĵ�������timer = 3��Ϊ����Щ���ݽ�������С���任���任�ķ�Χ�𽥼�С
	public void transform(int[] pieceData, int width, int length, int timer) {
		// TODO Auto-generated method stub
		data = pieceData;
		originalWidth = width;
		originalLength = length;
		
		int index = 1;
		while(index <= timer){
			transform(width/((int)Math.pow(2, index-1)) , 
					length/((int)Math.pow(2, index-1)));
			index++;
		}
	}

	//��ά��С���任��ָ���˴����Ͻǿ�ʼ����ĳ�����Ϊ�任�����ݡ��ȶ��н��б任���ٶ��н��б任
	protected void transform(int width, int length) {
		// TODO Auto-generated method stub
		//��ÿ�н���С���任
		for(int L = 0 ; L < length ; L++){
			int[] newRow = createNewRow(width , L);
			filer.calculate(newRow);
			setRowData(newRow , width , L);
		}
		
		//��ÿ�н���С���任
		for(int W = 0 ; W < width ; W++){
			int[] newCol = createNewColumn(length , W);
			filer.calculate(newCol);
			setColumnData(newCol , length , W);
		}

	}

	//���任��������ݷ�д��data��ȥ
	protected void setColumnData(int[] newCol, int realLength, int W) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < realLength ; i++){
			data[i*originalWidth+W] = newCol[i+2];
		}
	}

	//���任��������ݷ�д��data��ȥ
	protected void setRowData(int[] newRow, int realWidth, int L) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < realWidth ; i++){
			data[L*originalWidth+i] = newRow[i+2];
		}

	}
	
	//������ż����ѡ���½�һ�����飬��չ�е�����
	protected int[] createNewColumn(int realLength, int W) {
		// TODO Auto-generated method stub
		int[] result = null;
		if(realLength %2 == 0){
			result = new int[realLength+3];
			
			result[0] = data[2*originalWidth+W];
			result[1] = data[1*originalWidth+W];
			result[result.length-1] = data[(realLength-2)*originalWidth+W];
		}
		else{
			result = new int[realLength+4];
			
			result[0] = data[2*originalWidth+W];
			result[1] = data[1*originalWidth+W];
			result[result.length-1] = data[(realLength-3)*originalWidth+W];
			result[result.length-2] = data[(realLength-2)*originalWidth+W];
		}
		
		for(int i = 0 ; i < realLength ; i++){
			result[i+2] = data[i*originalWidth+W];
		}
		
		return result;

	}

	//������ż����ѡ���½�һ�����飬��չ�е�����
	protected int[] createNewRow(int realWidth, int L) {
		// TODO Auto-generated method stub
		int[] result = null;
		if(realWidth % 2 == 0){
			result = new int[realWidth+3];
			
			result[0] = data[L*originalWidth+2];
			result[1] = data[L*originalWidth+1];
			result[result.length-1] = data[L*originalWidth+realWidth-2];
		}
		else{
			result = new int[realWidth+4];
			
			result[0] = data[L*originalWidth+2];
			result[1] = data[L*originalWidth+1];
			result[result.length-2] = data[L*originalWidth+realWidth-2];
			result[result.length-1] = data[L*originalWidth+realWidth-3];
		}
		
		for(int i = 0 ; i < realWidth ; i++){
			result[i+2] = data[L*originalWidth+i];
		}

		return result;
	}
	
	public int[] getData(){
		return this.data;
	}

}