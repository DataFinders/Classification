package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import util.TxtToVector;
import util.TxtVector;

/**
 * 
 * @author zy ���ѵ�������ı���������
 */
public class BaseVectors {

	private ArrayList<TxtVector> baseVectors; // ѵ�������ı���������
	private ArrayList<String> baseWords; // ��ʼ����ֵ����
	// private HashMap<String, HashMap<String, Double>> wordFreguency; //
	// ѵ�����ı��ִʴ����Ľ��
	private HashMap<String, HashMap<String, Double>> baseTF_idf_df;

	public BaseVectors(ArrayList<String> baseWords, HashMap<String, HashMap<String, Double>> baseTF_idf_df) {
		this.baseWords = baseWords;
		// this.wordFreguency = wordFreguency;
		this.baseTF_idf_df = baseTF_idf_df;
		this.baseVectors = new ArrayList<TxtVector>();
		run();
	}

	/*
	 * ��������
	 */
	private void run() {
		Set<String> files = baseTF_idf_df.keySet();
		for (String file : files) { // ����ѵ�����е������ı�
			HashMap<String, Double> content = baseTF_idf_df.get(file); // ��ǰ�ı��ķִʽ��
			TxtToVector ttv = new TxtToVector(file, content, baseWords, true); // ����ǰ�ı�������
			baseVectors.add(ttv.getVector()); // �������ӽ�����

			// ttv.getVector().displayVector();
		}
	}

	public ArrayList<TxtVector> getBaseVectors() {
		return this.baseVectors;
	}
}
