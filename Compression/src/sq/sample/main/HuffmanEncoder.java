package sq.sample.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * ~传入一个数组
 * ~内部实现Huffman节点
 * ~最终的编码结果应该是<int,byte>形式
 */
public class HuffmanEncoder {
	private int[] data;
	private Map<Integer , Integer> dictionary;
	private ArrayList<HuffmanNode> treeNode;
	private Map<Integer , String> codeDictionary;
	
	public Map<Integer, Integer> getDictionary() {
		return dictionary;
	}

	public ArrayList<HuffmanNode> getTreeNode() {
		return treeNode;
	}

	public Map<Integer, String> getCodeDictionary() {
		return codeDictionary;
	}

	public HuffmanEncoder(){
		dictionary = new HashMap<Integer , Integer>();
		treeNode = new ArrayList<HuffmanNode>();
		codeDictionary = new HashMap<Integer , String>();
	}

	public void startEncode(int[] d) {
		// TODO Auto-generated method stub
		data = d;
		
		countAppearance();
		sortNodes();
		createHuffmanTree();
		gainNodeCode();
		
		System.out.println("Encode code dcitionary size : "+codeDictionary.size()+
				"\n1 is "+codeDictionary.get(1));

		//测试代码
//		print();
	}

	private void gainNodeCode() {
		// TODO Auto-generated method stub
		int treeSize = treeNode.size();
		for(int i = treeSize-1 ; i >= 0 ; i--){
			HuffmanNode current = treeNode.get(i);
			if(current.left != null && current.right != null){
				current.left.huffCode = current.huffCode+"0";
				current.right.huffCode = current.huffCode+"1";
			}
			else{
				if(!current.huffCode.isEmpty() && !codeDictionary.containsKey(current.value)){
					codeDictionary.put(current.value, current.huffCode);
				}
			}
		}
	}

	private void createHuffmanTree() {
		// TODO Auto-generated method stub
		int startIndex = 0;
		while(startIndex < treeNode.size() - 1){
			HuffmanNode father = new HuffmanNode();
			HuffmanNode one = treeNode.get(startIndex);
			startIndex++;
			HuffmanNode another = treeNode.get(startIndex);
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
	}

	private void sortNodes() {
		// TODO Auto-generated method stub
		for(Map.Entry<Integer, Integer> entry : dictionary.entrySet()){
			HuffmanNode e = new HuffmanNode();
			e.value = entry.getKey();
			e.appearence = entry.getValue();
			
			treeNode.add(e);
		}
		
		Collections.sort(treeNode);

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

	//将类似“0101011100”的二进制String转换为int数值
	private int binaryStringToInt(String huffCode) {
		// TODO Auto-generated method stub
		int length = huffCode.length();
		int result = 0;
		for(int i = length-1 ; i >= 0 ; i--){
			int charNumber = huffCode.charAt(i) - 48;
			result += charNumber * ((int)Math.pow(2, length-1-i));
		}
		
		return result;
	}

//	public void print(){
//		int max = 0;
//		int min = 0;
//		
//		for(Map.Entry<Integer, Integer> entry : codeDictionary.entrySet()){
//			System.out.println(entry.getKey()+"--"+entry.getValue());
//			if(entry.getKey() > max){
//				max = entry.getKey();
//			}
//			if(entry.getKey() < min){
//				min = entry.getKey();
//			}
//		}
//		System.out.println("Code Dictionary Size : "+codeDictionary.size());
//		System.out.println("max is : "+max);
//		System.out.println("min is : "+min);
//	}
}
class HuffmanNode implements Comparable<HuffmanNode>{
	public int value;
	public int appearence;
	public HuffmanNode left;
	public HuffmanNode right;
	public String huffCode = ""; 
	
	@Override
	public int compareTo(HuffmanNode e) {
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