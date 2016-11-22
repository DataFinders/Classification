package util;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author zy ���ı�����Ԥ�����Ľ��ת��������
 *
 */
public class TxtToVector {
	private List<String> base; // ��ʼ��������
	private HashMap<String, Double> content; // �ı��ִʽ��
	private TxtVector vector; // �ı�����
	private double[] vec; // ��ʱ��������
	/*
	 * ���캯������Ҫ�����ʼ�����������ı��ִʽ��
	 */

	public TxtToVector(String fileName, HashMap<String, Double> content, List<String> base, boolean isTRSet) {
		this.content = content;
		this.base = base;
		this.vector = new TxtVector(fileName, isTRSet);
		this.vec = new double[base.size()];
		ToVector();
	}

	/*
	 * ���ĵ�������
	 */
	private void ToVector() {
		for (int i = 0; i < base.size(); i++) { // ������ʼ�����������жϵ�ǰ�������Ƿ�������ı�������
			String key = base.get(i);
			if (content.containsKey(key)) // ����ǰ�������Ƿ�������ı������У������������Ϊ���ı���tf_idf_dfֵ
				vec[i] = content.get(key);
		}
	}

	public TxtVector getVector() {
		this.vector.setVector(vec);
		return this.vector;
	}
}
