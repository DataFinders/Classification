package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author zy
 * 将文本经过预处理后的结果转换成向量
 *
 */
public class TxtVector {
	List<String> base;	//初始特征向量
	HashMap<String,Double> content;	//文本分词结果
	double[] vector;	//文本向量
	/*
	 * 构造函数，需要传入初始特征向量和文本分词结果
	 */
	public TxtVector(HashMap<String,Double> content,HashMap<String,Double> Base){
		this.content = content;
		this.base = new ArrayList<String>();
		
		Set<String> set = Base.keySet();
		for(String s:set)
			this.base.add(s);
		this.vector = new double[base.size()];
		ToVector();
	}
	/*
	 * 将文档向量化
	 */
	private void ToVector(){
		for(int i=0;i<base.size();i++){	//遍历初始特征向量，判断当前特征项是否出现于文本内容中
			String key = base.get(i);
			if(content.containsKey(key))	//若当前特征项是否出现于文本内容中，则该向量点置为1
				vector[i] = 1.0;
		}
	}
	
	public double[] getVector(){
		return this.vector;
	}
}
