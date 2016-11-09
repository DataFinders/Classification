package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import com.chenlb.mmseg4j.example.MMseg4j;

/**
 * 
 * @author zy
 * ������Ҫ���ڻ��ѵ�����д���ֲ�������磺��ø��ĵ����г���ÿ��������ı����������ÿ���ĵ��Ĵ�Ƶ��Ϣ
 *
 */
public class TSWordDistribution {
	private File file;
	private HashMap<String,Double> wordDistribution;	//�洢�ĵ�������ֲ���Ϣ ��keyΪ���valueΪ���ֹ��ô�����ı�����
	private HashMap<String, HashMap<String, Double>> wordFreguency;		//�洢�ı���Ƶ��Ϣ��keyΪ�ı�����value��һ��HashMap����keyΪ���value���ڸ��ı��г��ֵĴ�����
	private TxtPreprocessing tp ;	//TxtPreprocessing�࣬���ڶ��ı���Ԥ����

	public TSWordDistribution(File file){
		this.file = file;
		wordDistribution = new HashMap<String,Double>();
		wordFreguency = new HashMap<String,HashMap<String,Double>>();
		run();
	}
	
	/*
	 * ��������
	 */
	private void run(){
		File[] files = file.listFiles();
		for(File f:files){
			deal(f);
		}
	}
	
	/*
	 * ���������Դ����ÿһ���ı����д���
	 */
	private void deal(File f){
		tp = new TxtPreprocessing(f);
		HashMap<String,Double> m = tp.getWordFreguency();	//ȥ���ظ��Ĵʣ��������Ƶ
		Set<String> keys = m.keySet();
		
		//����ǰ�ĵ��ķִʽ���������´���ֲ�
		for(String word:keys){	//�������ĵ����ֵ����дʣ��������ظ���
			if(wordDistribution.containsKey(word))	//������ֲ������Ѿ������Ĵʣ���Ĵʵ�value��1
				wordDistribution.put(word, wordDistribution.get(word)+1.0);
			else	//������ֲ����鲻�����Ĵʣ��򽫸Ĵʼ������ֲ����飬����value��ֵΪ1
				wordDistribution.put(word, 1.0);
		}
		
		wordFreguency.put(f.getName(), m);	//�洢��ǰ�ĵ��Ĵ�Ƶ�ֲ�
	}
	
	public HashMap<String,Double> getWordDistribution(){
		return this.wordDistribution;
	}

	public HashMap<String, HashMap<String, Double>> getWordFreguency() {
		return wordFreguency;
	}
}
