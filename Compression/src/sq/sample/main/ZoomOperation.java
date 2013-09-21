package sq.sample.main;

public class ZoomOperation {
	private int times = 2;
	private int SWIDTH;
	private int SLENGTH;
	
	private int DWIDTH;
	private int DLENGTH;
	
	public ZoomOperation(int w , int l){
		SWIDTH = w;
		SLENGTH = l;
		
		DWIDTH = (SWIDTH-2)*times;
		DLENGTH = (SLENGTH-2)*times;
	}
	
	public ZoomOperation(int w , int l , int t){
		SWIDTH = w;
		SLENGTH = l;
		times = t;
		
		DWIDTH = (SWIDTH-2)*times;
		DLENGTH = (SLENGTH-2)*times;
	}
	
	public int[] zoom(int[] source){
		int[] result = new int[DWIDTH*DLENGTH];
		for(int i = 0 ; i < result.length ; i++){
			int xIndex = i/DWIDTH;
			int yIndex = i%DWIDTH;
			
			float xIndexReal  = xIndex / ((float)times) + 1;
			float yIndexReal = yIndex / ((float)times) + 1;
			
			int s_xIndex = (int)xIndexReal;
			int s_yIndex = (int)yIndexReal;
			if(s_xIndex == xIndexReal && s_yIndex == yIndexReal){
				result[i] = source[s_xIndex * SWIDTH+s_yIndex];
			}
			if(s_xIndex != xIndexReal && s_yIndex == yIndexReal){
				int leftXDifference = (int)((xIndexReal-s_xIndex)*10);
				int rightXDifference = (int)((s_xIndex+1-xIndexReal)*10);
				result[i] = (leftXDifference * source[s_xIndex*SWIDTH+s_yIndex]+
						rightXDifference * source[(s_xIndex+1)*SWIDTH+s_yIndex]) / 10;
			}
			if(s_xIndex == xIndexReal && s_yIndex != yIndexReal){
				int leftYDifference = (int)((yIndexReal-s_yIndex)*10);
				int rightYDifference = (int)((s_yIndex+1-yIndexReal)*10);
				result[i] = (leftYDifference * source[s_xIndex*SWIDTH+s_yIndex]+
						rightYDifference * source[s_xIndex*SWIDTH+s_yIndex+1]) / 10;
			}
			if(s_xIndex != xIndexReal && s_yIndex != yIndexReal){
				int leftXDifference = (int)((xIndexReal-s_xIndex)*10);
				int rightXDifference = (int)((s_xIndex+1-xIndexReal)*10);
				int leftYDifference = (int)((yIndexReal-s_yIndex)*10);
				int rightYDifference = (int)((s_yIndex+1-yIndexReal)*10);
				
				result[i] = (leftXDifference*leftYDifference*source[s_xIndex*SWIDTH+s_yIndex]+
						leftXDifference*rightYDifference*source[s_xIndex*SWIDTH+s_yIndex+1]+
						rightXDifference*leftYDifference*source[(s_xIndex+1)*SWIDTH+s_yIndex]+
						rightXDifference*rightYDifference*source[(s_xIndex+1)*SWIDTH+s_yIndex+1])/100;
			}
		}
		return result;
	}
	
}
