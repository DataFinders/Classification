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
 * 该类主要用于获得训练集中词项分布情况，如：获得该文档集中出现每个词项的文本个数、获得每个文档的词频信息
 *
 */
public class TSWordDistribution {
	private File file;
	private HashMap<String,Double> wordDistribution;	//存储文档集词项分布信息 ，key为词项；value为出现过该此项的文本个数
	private HashMap<String, HashMap<String, Double>> wordFreguency;		//存储文本词频信息，key为文本名；value是一个HashMap，其key为词项，value问在该文本中出现的次数。
	private TxtPreprocessing tp ;	//TxtPreprocessing类，用于对文本的预处理

	public TSWordDistribution(File file){
		this.file = file;
		wordDistribution = new HashMap<String,Double>();
		wordFreguency = new HashMap<String,HashMap<String,Double>>();
		run();
	}
	
	/*
	 * 驱动方法
	 */
	private void run(){
		File[] files = file.listFiles();
		for(File f:files){
			deal(f);
		}
	}
	
	/*
	 * 处理方法，对传入的每一个文本进行处理
	 */
	private void deal(File f){
		tp = new TxtPreprocessing(f);
		HashMap<String,Double> m = tp.getWordFreguency();	//去除重复的词，并计算词频
		Set<String> keys = m.keySet();
		
		//处理当前文档的分词结果，并更新词项分布
		for(String word:keys){	//遍历该文档出现的所有词（不包含重复）
			if(wordDistribution.containsKey(word))	//若词项分布数组已经包含改词，则改词的value加1
				wordDistribution.put(word, wordDistribution.get(word)+1.0);
			else	//若词项分布数组不包含改词，则将改词加入词项分布数组，并设value初值为1
				wordDistribution.put(word, 1.0);
		}
		
		wordFreguency.put(f.getName(), m);	//存储当前文档的词频分布
	}
	
	public HashMap<String,Double> getWordDistribution(){
		return this.wordDistribution;
	}

	public HashMap<String, HashMap<String, Double>> getWordFreguency() {
		return wordFreguency;
	}
}
