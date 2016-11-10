package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TF_IDF {
	public static File file = new File("data/TrainingSet");
	public static HashMap<String, Double> wordDistribution;
	public static HashMap<String, HashMap<String, Double>> wordFreguency;
	private static HashMap<String, Double> tfidfHashMap = new HashMap<String, Double>();;

	/*public static void main(String[] args) {

		// 初始化 WordDistribution
		TSWordDistribution wD = new TSWordDistribution(file);
		// 取出 两类参数
		wordDistribution = wD.getWordDistribution();
		wordFreguency = wD.getWordFreguency();
		// 初始化 TF_IDF 类
		TF_IDF x = new TF_IDF(wordDistribution, wordFreguency);
		HashMap<String, Double> y = x.getTfidfHashMap();
		// 循环输出
		for (Entry<String, Double> outY : y.entrySet()) {
			System.out.println(outY.getKey() + " : " + outY.getValue());
		}
	}*/

	public HashMap<String, Double> getTfidfHashMap() {
		return tfidfHashMap;
	}

	public TF_IDF(HashMap<String, Double> wordDistribution, HashMap<String, HashMap<String, Double>> wordFreguency) {
		int count0;
		// 遍历 wordFreguency
		for (Entry<String, HashMap<String, Double>> entryFreguency : wordFreguency.entrySet()) {
			// 文件名
			String k0 = entryFreguency.getKey();
			// System.out.println(k0);
			// 文件对应的词项
			HashMap<String, Double> v0 = entryFreguency.getValue();
			// System.out.println("文本个数" + wordFreguency.size());

			// **********获取 tfidf 值
			HashMap<String, Double> re = calculate(wordDistribution, v0, wordFreguency.size());

			// 对 tfidf 的 HashMap 排序
			List<Entry<String, Double>> list0 = new ArrayList<Entry<String, Double>>(re.entrySet());
			Collections.sort(list0, new Comparator<Map.Entry<String, Double>>() {
				// 降序排序
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// return o1.getValue().compareTo(o2.getValue());
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			// 根据 tfidf值 取出每个文件的前20关键词
			count0 = 0;
			for (Map.Entry<String, Double> mapping0 : list0) {
				// System.out.println(mapping0.getKey() + " : " +
				// mapping0.getValue());
				tfidfHashMap.put(mapping0.getKey(), (double) 0);
				count0++;
				if (count0 == 20) {
					// System.out.println("------------");
					break;
				}
			}
		}
	}

	public static HashMap<String, Double> calculate(HashMap<String, Double> d0, HashMap<String, Double> v1, int sum) {
		// 定义返回值
		HashMap<String, Double> re = new HashMap<String, Double>();
		// ciPin: 词在文章中的次数 即 value
		// maxCiPin: 该文出现最多的词在文章中的出现次数
		// tF
		// iDF
		// ciWen 出现该词的文档个数
		// tfIdf
		String key;
		Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdf = null;
		int count1 = 0;
		// 初始化为 List<> 进行降序排序
		List<Entry<String, Double>> list1 = new ArrayList<Entry<String, Double>>(v1.entrySet());
		Collections.sort(list1, new Comparator<Map.Entry<String, Double>>() {
			// 降序排序
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (Map.Entry<String, Double> mapping1 : list1) {
			// key 及 value
			key = mapping1.getKey();
			ciPin = mapping1.getValue();
			// 取出频率最高的词
			if (count1 == 0) {
				maxCiPin = mapping1.getValue();
			}
			tF = ciPin / maxCiPin;
			ciWen = d0.get(key);
			iDF = Math.log(d0.size()) / Math.log(2) - Math.log(ciWen) / Math.log(2);
			tfIdf = tF * iDF*10;
			 /*System.out.println(key + ":" + ciPin + "\nciPin:" + ciPin +
			"\ntF:" + tF + "\nciWen:" + ciWen + "\nIdf:"
			+ iDF + "\ntfIdf:" + tfIdf);*/
			re.put(key, tfIdf);
			count1++;
		}
		return re;
	}
}
