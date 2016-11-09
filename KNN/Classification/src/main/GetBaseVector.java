package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import util.TxtToVector;
import util.TxtVector;

/**
 * 
 * @author zy
 *	获得训练集的文本向量集合
 */
public class GetBaseVector {
	
	private ArrayList<TxtVector> baseVectors;	//训练集的文本向量集合
	private ArrayList<String> baseWords;	//初始特征值集合
	private HashMap<String, HashMap<String, Double>> wordFreguency;		//训练集文本分词处理后的结果
	
	public GetBaseVector(ArrayList<String> baseWords,HashMap<String, HashMap<String, Double>> wordFreguency){
		this.baseWords = baseWords;
		this.wordFreguency = wordFreguency;
		this.baseVectors = new ArrayList<TxtVector>();
		run();
	}
	/*
	 * 驱动方法
	 */
	private void run(){
		Set<String> files = wordFreguency.keySet();
		for(String file:files){ 	//遍历训练集中的所有文本
			HashMap<String, Double> content = wordFreguency.get(file);	//当前文本的分词结果
			TxtToVector ttv = new TxtToVector(file, content, baseWords, true);	//将当前文本向量化
			baseVectors.add(ttv.getVector());	//将结果添加进集合
		}
	}
	public ArrayList<TxtVector> getBaseVectors(){
		return this.baseVectors;
	}
}
