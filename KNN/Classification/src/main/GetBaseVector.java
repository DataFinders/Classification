package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import util.TxtToVector;
import util.TxtVector;

/**
 * 
 * @author zy
 *	���ѵ�������ı���������
 */
public class GetBaseVector {
	
	private ArrayList<TxtVector> baseVectors;	//ѵ�������ı���������
	private ArrayList<String> baseWords;	//��ʼ����ֵ����
	private HashMap<String, HashMap<String, Double>> wordFreguency;		//ѵ�����ı��ִʴ����Ľ��
	
	public GetBaseVector(ArrayList<String> baseWords,HashMap<String, HashMap<String, Double>> wordFreguency){
		this.baseWords = baseWords;
		this.wordFreguency = wordFreguency;
		this.baseVectors = new ArrayList<TxtVector>();
		run();
	}
	/*
	 * ��������
	 */
	private void run(){
		Set<String> files = wordFreguency.keySet();
		for(String file:files){ 	//����ѵ�����е������ı�
			HashMap<String, Double> content = wordFreguency.get(file);	//��ǰ�ı��ķִʽ��
			TxtToVector ttv = new TxtToVector(file, content, baseWords, true);	//����ǰ�ı�������
			baseVectors.add(ttv.getVector());	//�������ӽ�����
		}
	}
	public ArrayList<TxtVector> getBaseVectors(){
		return this.baseVectors;
	}
}
