package sq.sample.main;

import java.util.ArrayList;

public class Segments_Intersect {
	private ArrayList<Float> segments;
	
	public Segments_Intersect(){
		
	}
	
	public Segments_Intersect(ArrayList<Float> s){
		segments = s;
	}
	
	public float[] findClosePoints(ArrayList<Float> seg){
		int pointSize = seg.size()/2;
		if(pointSize < 4){
			return null;
		}
		float[] result = new float[8];
		
		for(int i = 0 ; i < pointSize-3 ; i++){
			result[0] = seg.get(i*2);
			result[1] = seg.get(i*2+1);
			result[2] = seg.get(i*2+2);
			result[3] = seg.get(i*2+3);
			for(int j = i+2 ; j < pointSize-1 ; j++){
				result[4] = seg.get(j*2);
				result[5] = seg.get(j*2+1);
				result[6] = seg.get(j*2+2);
				result[7] = seg.get(j*2+3);
				
				if(intersect(result)){
					return result;
				}
			}
		}
		return null;
	}
	
	public boolean intersect(float[] points){
		float p1_x = points[0];float p1_y = points[1];
		float p2_x = points[2];float p2_y = points[3];
		float p3_x = points[4];float p3_y = points[5];
		float p4_x = points[6];float p4_y = points[7];
		
		float d341 = direction(p3_x,p3_y,p4_x,p4_y,p1_x,p1_y);
		float d342 = direction(p3_x,p3_y,p4_x,p4_y,p2_x,p2_y);
		float d123 = direction(p1_x,p1_y,p2_x,p2_y,p3_x,p3_y);
		float d124 = direction(p1_x,p1_y,p2_x,p2_y,p4_x,p4_y);
		
		if(((d341>0 && d342<0) || (d341<0 && d342>0)) && 
			((d123>0 && d124<0) || (d123<0 && d124>0))){
			System.out.println(d341+"-"+d342+"-"+d123+"-"+d124);
			return true;
		}
		else if(d341 == 0 && on_segment(p3_x,p3_y,p4_x,p4_y,p1_x,p1_y)){
			System.out.println(1);
			return true;
		}
		else if(d342 == 0 && on_segment(p3_x,p3_y,p4_x,p4_y,p2_x,p2_y)){
			System.out.println(2);
			return true;
		}
		else if(d123 == 0 && on_segment(p1_x,p1_y,p2_x,p2_y,p3_x,p3_y)){
			System.out.println(3);
			return true;
		}
		else if(d124 == 0 && on_segment(p1_x,p1_y,p2_x,p2_y,p4_x,p4_y)){
			System.out.println(4);
			return true;
		}
		System.out.println(5);
		return false;
	}
	
	public float[] findCloseShape(){
		int segmentsSize = segments.size();
		float[] result = new float[8];
		if(segmentsSize < 8){
			return null;
		}
		int pointsSize = segmentsSize/2;
		
		System.out.println("SegmentsSize : "+segmentsSize);
		System.out.println("PointsSize : "+pointsSize);
		for(int i = 0 ; i < pointsSize-3 ; i++){
			float p1_x = segments.get(i*2);
			float p1_y = segments.get(i*2+1);
			float p2_x = segments.get(i*2+2);
			float p2_y = segments.get(i*2+3);
			float p3_x = segments.get(i*2+4);
			float p3_y = segments.get(i*2+5); 
			float p4_x, p4_y;
			result[0] = p1_x;result[1] = p1_y;
			result[2] = p2_x;result[3] = p2_y;
			result[4] = p3_x;result[5] = p3_y;

			int index = i*2+6;
			while(index < segmentsSize - 1){		
				p4_x = segments.get(index);
				index++;
				p4_y = segments.get(index);
				index++;
				result[6] = p4_x;result[7] = p4_y;
				
				float d341 = direction(p3_x,p3_y,p4_x,p4_y,p1_x,p1_y);
				float d342 = direction(p3_x,p3_y,p4_x,p4_y,p2_x,p2_y);
				float d123 = direction(p1_x,p1_y,p2_x,p2_y,p3_x,p3_y);
				float d124 = direction(p1_x,p1_y,p2_x,p2_y,p4_x,p4_y);
				
				if(((d341>0 && d342<0) || (d341<0 && d342>0)) && 
					((d123>0 && d124<0) || (d123<0 && d124>0))){
					return result;
				}
				else if(d341 == 0 && on_segment(p3_x,p3_y,p4_x,p4_y,p1_x,p1_y)){
					return result;
				}
				else if(d342 == 0 && on_segment(p3_x,p3_y,p4_x,p4_y,p2_x,p2_y)){
					return result;
				}
				else if(d123 == 0 && on_segment(p1_x,p1_y,p2_x,p2_y,p3_x,p3_y)){
					return result;
				}
				else if(d124 == 0 && on_segment(p1_x,p1_y,p2_x,p2_y,p4_x,p4_y)){
					return result;
				}
				
				p3_x = p4_x;
				p3_y = p4_y;
				result[4] = p3_x;result[5] = p3_y;
			}
		}		
		return null;
	}
	
	private float direction(float p1_x , float p1_y , float p2_x , float p2_y ,
						float p3_x , float p3_y){
		return (p3_x-p1_x)*(p2_y-p1_y) - (p2_x-p1_x)*(p3_y-p1_y);
	}
	
	private boolean on_segment(float p1_x , float p1_y , float p2_x , float p2_y ,
			float p3_x , float p3_y){
		return Math.min(p1_x, p2_x) < p3_x && Math.max(p1_x, p2_x) > p3_x &&
			   Math.min(p1_y, p2_y) < p3_y && Math.max(p1_y, p2_y) > p3_y;
	}
}
