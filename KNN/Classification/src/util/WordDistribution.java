package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import com.chenlb.mmseg4j.example.MMseg4j;

/*
 * 该类主要用于获得一个文档集中词项分布情况，如：获得该文档集中出现每个词项的文本个数、获得每个文档的词频信息
 */
public class WordDistribution {
	private File file;
	private HashMap<String,Double> wordDistribution;	//存储文档集词项分布信息 ，key为词项；value为出现过该此项的文本个数
	private HashMap<String, HashMap<String, Double>> wordFreguency;		//存储文本词频信息，key为文本名；value是一个HashMap，其key为词项，value问在该文本中出现的次数。
	private MMseg4j mmseg;

	public WordDistribution(File file){
		this.file = file;
		wordDistribution = new HashMap<String,Double>();
		wordFreguency = new HashMap<String,HashMap<String,Double>>();
		mmseg = new MMseg4j();
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
		String[] words = getWords(f);	//获得文档的分词结果
		HashMap<String,Double> m = meger(words);	//去除重复的词，并计算词频
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
	
	/*
	 * 获取文本内容并分词
	 */
	private String[] getWords(File f){
		String[] words = null;
		try {
			//从磁盘上读取当前文本的内容
			FileInputStream in = new FileInputStream(f);
			byte[] by = new byte[(int) f.length()];
			in.read(by, 0, by.length);
			String content = new String(by,"utf-8");
			//分词
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
	 * 去除文本分词结果的的重复项，并计算数组中词项出现的频率
	 */
	private HashMap<String,Double> meger(String[] words){
		HashMap<String,Double> m = new HashMap<String,Double>();	//存储当前文本的词频分布情况，key为词项，value为改词在当前文本出现的次数
		for(String word:words){		//遍历文本中的所有词
			if(m.containsKey(word))		//若词项已经出现在词频分布数组，则该词项的value加1
				m.put(word, m.get(word)+1.0);
			else	//若改词未出现在词频分布数组，则将改词加入词频分布数组，并将改词的value置1
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
