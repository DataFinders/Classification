package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import com.chenlb.mmseg4j.example.MMseg4j;
/**
 * 
 * @author zy
 * ���ı�����Ԥ�������ı����зִʲ��Էִʽ���ϲ�ȥ�أ�ͳ�ƴ�Ƶ�ֲ���
 *
 */
public class TxtPreprocessing {
	File file;
	private  HashMap<String, Double> wordFreguency;	//�洢�ı���Ƶ�ֲ���keyΪ���valueΪ�ô������ı��г��ֵĴ���
	private MMseg4j mmseg;
	
	public TxtPreprocessing(File file){
		this.file = file;
		wordFreguency = new HashMap<String,Double>();
		mmseg = new MMseg4j();
		run();
	}
	/*
	 * ��������
	 */
	private void run(){
		String[] words = getWords(this.file);	//����ı��ķִʽ��
		this.wordFreguency = meger(words);		//ȥ���ִʽ�����ظ��Ĵ���������Ƶ
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

	public HashMap<String, Double> getWordFreguency() {
		return wordFreguency;
	}
		
}
