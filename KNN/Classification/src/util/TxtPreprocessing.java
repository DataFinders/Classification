package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.chenlb.mmseg4j.example.MMseg4j;
/**
 * 
 * @author zy
 * ���ı�����Ԥ�������ı����зִʲ��Էִʽ���ϲ�ȥ�أ�ͳ�ƴ�Ƶ�ֲ���
 *
 */
public class TxtPreprocessing {
	private File file;
	private  HashMap<String, Double> wordFreguency;	//�洢�ı���Ƶ�ֲ���keyΪ���valueΪ�ô������ı��г��ֵĴ���
	private static MMseg4j mmseg = new MMseg4j();
	
	public TxtPreprocessing(File file){
		this.file = file;
		wordFreguency = new HashMap<String,Double>();
		run();
	}
	/*
	 * ��������
	 */
	private void run(){
		ArrayList<String> words = getWords(this.file);	//����ı��ķִʽ��
		this.wordFreguency = meger(words);		//ȥ���ִʽ�����ظ��Ĵ���������Ƶ
	}
	/*
	 * ��ȡ�ı����ݲ��ִ�
	 */
	private ArrayList<String> getWords(File f){
		ArrayList<String> result = new ArrayList<String>();
		try {
			//�Ӵ����϶�ȡ��ǰ�ı�������
			@SuppressWarnings("resource")
			FileInputStream in = new FileInputStream(f);
			byte[] by = new byte[(int) f.length()];
			in.read(by, 0, by.length);
			String content = new String(by,"utf-8");
			//�ִ�
			String[] words  = mmseg.getResult(content);
			for(String word:words){
				if(!stopWords.stopWords.contains(word) && word.length()>1)
					result.add(word);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * ȥ���ı��ִʽ���ĵ��ظ�������������д�����ֵ�Ƶ��
	 */
	private HashMap<String,Double> meger(ArrayList<String> words){
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
