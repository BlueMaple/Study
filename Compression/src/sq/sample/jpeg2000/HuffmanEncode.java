package sq.sample.jpeg2000;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HuffmanEncode {
	private int[] data;
	private Map<Integer , Integer> dictionary;
	private ArrayList<HuffmanElement> treeNode;
	private Map<Integer , String> codeDictionary;
	
	public HuffmanEncode(int[] d){
		data = d;
		dictionary = new HashMap<Integer , Integer>();
		treeNode = new ArrayList<HuffmanElement>();
		codeDictionary = new HashMap<Integer , String>();
	}

	public void encode(){
		countAppearance();
		sortElements();
		createHuffmanTree();
		gainElementCode();
	}

	private void gainElementCode() {
		// TODO Auto-generated method stub
		int treeSize = treeNode.size();
		for(int i = treeSize-1 ; i >= 0 ; i--){
			HuffmanElement current = treeNode.get(i);
			if(current.left != null && current.right != null){
				current.left.huffCode = current.huffCode+"0";
				current.right.huffCode = current.huffCode+"1";
			}
			else{
				if(!current.huffCode.isEmpty() && !codeDictionary.containsKey(current.value)){
					codeDictionary.put(current.value, current.huffCode);
//					if(current.huffCode.length() <= 16)
//						System.out.println(current.value+"--"+current.appearence+"--"+current.huffCode);
				}
			}
		}
		
		int totalLength = 0;
		for(Map.Entry<Integer, String> entry : codeDictionary.entrySet()){
			totalLength += (16-entry.getValue().length()) * dictionary.get(entry.getKey());
		}
		System.out.println("less : "+totalLength);
	}

	private void createHuffmanTree() {
		// TODO Auto-generated method stub
		int startIndex = 0;
		while(startIndex < treeNode.size() - 1){
			HuffmanElement father = new HuffmanElement();
			HuffmanElement one = treeNode.get(startIndex);
			startIndex++;
			HuffmanElement another = treeNode.get(startIndex);
			startIndex++;
			
			father.left = one;
			father.right = another;
			father.appearence = one.appearence+another.appearence;

			int s = startIndex; int e = treeNode.size()-1;
			while(e - s != 1 && e - s != 0){
				if(treeNode.get(e).appearence < father.appearence){
					e = e+1;
					break;
				}
				if(treeNode.get(s).appearence > father.appearence){
					e = startIndex-1;
					break;
				}
				int middle = (s+e)/2;
				if(treeNode.get(middle).appearence > father.appearence){
					e = middle;
				}
				else{
					s = middle;
				}
			}
			treeNode.add(e, father);
		}
//		for(int i = 0 ; i < treeNode.size() ; i++){
//			System.out.println(treeNode.get(i).appearence);
//		}
		
	}

	private void sortElements() {
		// TODO Auto-generated method stub
		for(Map.Entry<Integer, Integer> entry : dictionary.entrySet()){
			HuffmanElement e = new HuffmanElement();
			e.value = entry.getKey();
			e.appearence = entry.getValue();
			
			treeNode.add(e);
		}
		
		Collections.sort(treeNode);
//		for(int i = 0 ; i < 100 ; i++){
//			System.out.println(treeNode.get(i).value+" : "+treeNode.get(i).appearence);
//		}
	}

	private void countAppearance() {
		// TODO Auto-generated method stub
		int dataLength = data.length;
		for(int i = 0 ; i < dataLength ; i++){
			int element = data[i];
			if(dictionary.containsKey(element)){
				int number = dictionary.get(element);
				dictionary.put(element, number+1);
			}
			else{
				dictionary.put(element, 1);
			}
		}
	}

	public void dictionaryToByte(ArrayList<Byte> byteList , int byteToPixel) {
		// TODO Auto-generated method stub
		//开始的byteToPixel个byte用来指明CodeDictionary的大小
		int codeSize = codeDictionary.size();
		for(int i = byteToPixel-1 ; i >= 0 ; i--){
			byte temp = (byte) (codeSize >> (i*8) & 0xff);
			byteList.add(temp);
		}
		//将codeDictionary转换为byte
		for(Map.Entry<Integer, String> entry : codeDictionary.entrySet()){
			int value = entry.getKey();
			for(int i = byteToPixel-1 ; i >= 0 ; i--){
				byte temp = (byte) (value >> (i*8) & 0xff);
				byteList.add(temp);
			}

			String ccc = entry.getValue();

		}
	}

	public void pixelToByte(ArrayList<Byte> byteList, int byteToPixel) {
		// TODO Auto-generated method stub
		int pixelLength = this.data.length;
		for(int index = 0 ; index < pixelLength ; index++){
			int pixel = data[index];
			String code = codeDictionary.get(pixel);

		}
	}
}
class HuffmanElement implements Comparable<HuffmanElement>{
	public int value;
	public int appearence;
	public HuffmanElement left;
	public HuffmanElement right;
	public String huffCode = ""; 
	
	@Override
	public int compareTo(HuffmanElement e) {
		// TODO Auto-generated method stub
		if(e.appearence > appearence){
			return -1;
		}
		else if(e.appearence < appearence){
			return 1;
		}
		else{
			return 0;
		}

	}
}