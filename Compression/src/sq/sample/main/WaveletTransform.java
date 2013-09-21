package sq.sample.main;

/*
 * ~传入一个数组、变换的长和宽
 * ~选择合适的滤波器进行变换
 * ~各种变换直接在数组上操作
 */
public class WaveletTransform {
	protected LeCall_5_3 filer;
	protected int[] data;
	
	protected int originalWidth;
	protected int originalLength;
	
	//默认使用LeCall(5,3)的滤波器
	public WaveletTransform(){
		filer = new LeCall_5_3();
	}

	//变换方法，timer指的是变换的次数。如timer = 3即为对这些数据进行三次小波变换，变换的范围逐渐减小
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

	//二维的小波变换，指定了从左上角开始计算的长宽作为变换的内容。先对行进行变换，再对列进行变换
	protected void transform(int width, int length) {
		// TODO Auto-generated method stub
		//按每行进行小波变换
		for(int L = 0 ; L < length ; L++){
			int[] newRow = createNewRow(width , L);
			filer.calculate(newRow);
			setRowData(newRow , width , L);
		}
		
		//按每列进行小波变换
		for(int W = 0 ; W < width ; W++){
			int[] newCol = createNewColumn(length , W);
			filer.calculate(newCol);
			setColumnData(newCol , length , W);
		}

	}

	//将变换后的列数据反写到data中去
	protected void setColumnData(int[] newCol, int realLength, int W) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < realLength ; i++){
			data[i*originalWidth+W] = newCol[i+2];
		}
	}

	//将变换后的行数据反写到data中去
	protected void setRowData(int[] newRow, int realWidth, int L) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < realWidth ; i++){
			data[L*originalWidth+i] = newRow[i+2];
		}

	}
	
	//根据奇偶性来选择新建一个数组，扩展列的数据
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

	//根据奇偶性来选择新建一个数组，扩展行的数据
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