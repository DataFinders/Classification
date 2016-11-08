package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import com.chenlb.mmseg4j.example.MMseg4j;

/*
 * ������Ҫ���ڻ��һ���ĵ����д���ֲ�������磺��ø��ĵ����г���ÿ��������ı����������ÿ���ĵ��Ĵ�Ƶ��Ϣ
 */
public class WordDistribution {
	private File file;
	private HashMap<String,Double> wordDistribution;	//�洢�ĵ�������ֲ���Ϣ ��keyΪ���valueΪ���ֹ��ô�����ı�����
	private HashMap<String, HashMap<String, Double>> wordFreguency;		//�洢�ı���Ƶ��Ϣ��keyΪ�ı�����value��һ��HashMap����keyΪ���value���ڸ��ı��г��ֵĴ�����
	private MMseg4j mmseg;

	public WordDistribution(File file){
		this.file = file;
		wordDistribution = new HashMap<String,Double>();
		wordFreguency = new HashMap<String,HashMap<String,Double>>();
		mmseg = new MMseg4j();
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
		String[] words = getWords(f);	//����ĵ��ķִʽ��
		HashMap<String,Double> m = meger(words);	//ȥ���ظ��Ĵʣ��������Ƶ
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
	
	/*
	 * ��ȡ�ı����ݲ��ִ�
	 */
	private String[] getWords(File f){
		String[] words = null;
		try {
			//�Ӵ����϶�ȡ��ǰ�ı�������
			FileInputStream in = new FileInputStream(f);
			byte[] by = new byte[(int) f.length()];
			in.read(by, 0, by.length);
			String content = new String(by,"utf-8");
			//�ִ�
			words = mmseg.getResult(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return words;
	}
	
	/*
	 * ȥ���ı��ִʽ���ĵ��ظ�������������д�����ֵ�Ƶ��
	 */
	private HashMap<String,Double> meger(String[] words){
		HashMap<String,Double> m = new HashMap<String,Double>();	//�洢��ǰ�ı��Ĵ�Ƶ�ֲ������keyΪ���valueΪ�Ĵ��ڵ�ǰ�ı����ֵĴ���
		for(String word:words){		//�����ı��е����д�
			if(m.containsKey(word))		//�������Ѿ������ڴ�Ƶ�ֲ����飬��ô����value��1
				m.put(word, m.get(word)+1.0);
			else	//���Ĵ�δ�����ڴ�Ƶ�ֲ����飬�򽫸Ĵʼ����Ƶ�ֲ����飬�����Ĵʵ�value��1
				m.put(word, 1.0);
		}
		return m;
	}
	
	public HashMap<String,Double> getWordDistribution(){
		return this.wordDistribution;
	}

	public HashMap<String, HashMap<String, Double>> getWordFreguency() {
		return wordFreguency;
	}

}
