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
 * 对文本进行预处理，对文本进行分词并对分词结果合并去重，统计词频分布。
 *
 */
public class TxtPreprocessing {
	private File file;
	private  HashMap<String, Double> wordFreguency;	//存储文本词频分布，key为词项，value为该词项在文本中出现的次数
	private static MMseg4j mmseg = new MMseg4j();
	
	public TxtPreprocessing(File file){
		this.file = file;
		wordFreguency = new HashMap<String,Double>();
		run();
	}
	/*
	 * 驱动方法
	 */
	private void run(){
		ArrayList<String> words = getWords(this.file);	//获得文本的分词结果
		this.wordFreguency = meger(words);		//去除分词结果中重复的词项，并计算词频
	}
	/*
	 * 获取文本内容并分词
	 */
	private ArrayList<String> getWords(File f){
		ArrayList<String> result = new ArrayList<String>();
		try {
			//从磁盘上读取当前文本的内容
			@SuppressWarnings("resource")
			FileInputStream in = new FileInputStream(f);
			byte[] by = new byte[(int) f.length()];
			in.read(by, 0, by.length);
			String content = new String(by,"utf-8");
			//分词
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
	 * 去除文本分词结果的的重复项，并计算数组中词项出现的频率
	 */
	private HashMap<String,Double> meger(ArrayList<String> words){
		HashMap<String,Double> m = new HashMap<String,Double>();	//存储当前文本的词频分布情况，key为词项，value为改词在当前文本出现的次数
		for(String word:words){		//遍历文本中的所有词
			if(m.containsKey(word))		//若词项已经出现在词频分布数组，则该词项的value加1
				m.put(word, m.get(word)+1.0);
			else	//若改词未出现在词频分布数组，则将改词加入词频分布数组，并将改词的value置1
				m.put(word, 1.0);
		}
		return m;
	}

	public HashMap<String, Double> getWordFreguency() {
		return wordFreguency;
	}
		
}
