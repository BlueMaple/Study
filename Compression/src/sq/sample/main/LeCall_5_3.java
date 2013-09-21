package sq.sample.main;

/*
 * 继承与Filter类，实现具体的变换
 */
public class LeCall_5_3 extends AbstractFilter{

	public void calculate(int[] array) {
		// TODO Auto-generated method stub
		//奇数列的数字先进行变换
		for(int index = 1 ; index < array.length ; index+=2){
			array[index] = array[index] - 
					((int)((array[index-1] + array[index+1])/2.0));
		}
		//偶数列的数字进行变换。注意第一个和最后一个数不需要变换，所以从2循环到length-1
		for(int index = 2 ; index < array.length-1 ; index+=2){
			array[index] = array[index] +
					((int)((2 + array[index-1] + array[index+1])/4.0));
		}
	}

	@Override
	public void antiCalculate(int[] array) {
		// TODO Auto-generated method stub
		//偶数列的额数字先进行变换
		for(int index = 2 ; index < array.length-1 ; index+=2){
			array[index] = array[index] -
					((int)((array[index-1] + array[index+1] + 2)/4.0));
		}
		//奇数列的数字进行变换
		for(int index = 1 ; index < array.length-1 ; index+=2){
			array[index] = array[index] +
					((int)((array[index-1] + array[index+1])/2.0));
		}
	}

}
