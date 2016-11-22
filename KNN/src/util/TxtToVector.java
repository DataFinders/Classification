package util;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author zy 将文本经过预处理后的结果转换成向量
 *
 */
public class TxtToVector {
	private List<String> base; // 初始特征向量
	private HashMap<String, Double> content; // 文本分词结果
	private TxtVector vector; // 文本向量
	private double[] vec; // 临时向量数组
	/*
	 * 构造函数，需要传入初始特征向量和文本分词结果
	 */

	public TxtToVector(String fileName, HashMap<String, Double> content, List<String> base, boolean isTRSet) {
		this.content = content;
		this.base = base;
		this.vector = new TxtVector(fileName, isTRSet);
		this.vec = new double[base.size()];
		ToVector();
	}

	/*
	 * 将文档向量化
	 */
	private void ToVector() {
		for (int i = 0; i < base.size(); i++) { // 遍历初始特征向量，判断当前特征项是否出现于文本内容中
			String key = base.get(i);
			if (content.containsKey(key)) // 若当前特征项是否出现于文本内容中，则该向量点置为该文本的tf_idf_df值
				vec[i] = content.get(key);
		}
	}

	public TxtVector getVector() {
		this.vector.setVector(vec);
		return this.vector;
	}
}
