package sq.sample.main;

/*
 * �̳���Filter�࣬ʵ�־���ı任
 */
public class LeCall_5_3 extends AbstractFilter{

	public void calculate(int[] array) {
		// TODO Auto-generated method stub
		//�����е������Ƚ��б任
		for(int index = 1 ; index < array.length ; index+=2){
			array[index] = array[index] - 
					((int)((array[index-1] + array[index+1])/2.0));
		}
		//ż���е����ֽ��б任��ע���һ�������һ��������Ҫ�任�����Դ�2ѭ����length-1
		for(int index = 2 ; index < array.length-1 ; index+=2){
			array[index] = array[index] +
					((int)((2 + array[index-1] + array[index+1])/4.0));
		}
	}

	@Override
	public void antiCalculate(int[] array) {
		// TODO Auto-generated method stub
		//ż���еĶ������Ƚ��б任
		for(int index = 2 ; index < array.length-1 ; index+=2){
			array[index] = array[index] -
					((int)((array[index-1] + array[index+1] + 2)/4.0));
		}
		//�����е����ֽ��б任
		for(int index = 1 ; index < array.length-1 ; index+=2){
			array[index] = array[index] +
					((int)((array[index-1] + array[index+1])/2.0));
		}
	}

}
