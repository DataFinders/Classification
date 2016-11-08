package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/*
 * ���ı�ת��������
 */
public class TxtVector {
	List<String> base;	//��ʼ��������
	HashMap<String,Double> content;	//�ı��ִʽ��
	double[] vector;	//�ı�����
	/*
	 * ���캯������Ҫ�����ʼ�����������ı��ִʽ��
	 */
	public TxtVector(HashMap<String,Double> content,HashMap<String,Double> map){
		this.content = content;
		this.base = new ArrayList<String>();
		
		Set<String> set = map.keySet();
		for(String s:set)
			this.base.add(s);
		
		this.vector = new double[base.size()];
		ToVector();
	}
	/*
	 * ���ĵ�������
	 */
	private void ToVector(){
		for(int i=0;i<base.size();i++){	//������ʼ�����������жϵ�ǰ�������Ƿ�������ı�������
			String key = base.get(i);
			if(content.containsKey(key))	//����ǰ�������Ƿ�������ı������У������������Ϊ1
				vector[i] = 1.0;
		}
	}
	
	public double[] getVector(){
		return this.vector;
	}
}
