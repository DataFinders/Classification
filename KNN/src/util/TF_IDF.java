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
	public HashMap<String, Double> wordDistribution;
	public static HashMap<String, HashMap<String, Double>> wordFreguency;
	public static HashMap<String, HashMap<String, Double>> wordClassDistribution;
	// wordFreguency 的子 hashmap
	public HashMap<String, Double> wordFreguencySon;
	public static HashMap<String, Double> clssss;

	// public static void main(String[] args) {
	// // 初始化 WordDistribution
	// TSWordDistribution wD = new TSWordDistribution(new
	// File("data/TrainingSet"));
	// // 取出 两类参数
	// HashMap<String, Double> Distribution = wD.getWordDistribution();
	// HashMap<String, HashMap<String, Double>> Freguency =
	// wD.getWordFreguency();
	// HashMap<String, HashMap<String, Double>> classFreguency =
	// wD.getWordClassDistribution();
	// // 初始化TF_IDF 类
	// TF_IDF x = new TF_IDF(Distribution, Freguency, classFreguency);
	// List<String> word = x.noWorkWord();
	// for (String tmp : word) {
	// System.out.println(tmp);
	// }
	// System.out.println("无用词" + word.size());
	// System.out.println("词总数" + wordClassDistribution.size());
	// }

	public TF_IDF(HashMap<String, Double> wordDistribution, HashMap<String, HashMap<String, Double>> wordFreguency,
			HashMap<String, HashMap<String, Double>> wordClassDistribution) {
		// 存入基本参数
		this.wordDistribution = wordDistribution;
		TF_IDF.wordFreguency = wordFreguency;
		TF_IDF.wordClassDistribution = wordClassDistribution;
		// 识别出类的数量，及文本数
		clssss = new HashMap<String, Double>();
		for (Entry<String, HashMap<String, Double>> names : wordFreguency.entrySet()) {
			//
			String keyy = names.getKey();
			String kindd = keyy.split("[.]")[0].split("-")[1];
			if (clssss.containsKey(kindd)) {
				clssss.put(kindd, clssss.get(kindd) + 1.0);
			} else {
				clssss.put(kindd, 1.0);
			}
		}
	}

	// 去除关键词
	public List<String> noWorkWord() {
		List<String> noWorkWords = new ArrayList<String>();
		for (Entry<String, HashMap<String, Double>> n1 : wordClassDistribution.entrySet()) {
			// 存放value
			List<Double> num = new ArrayList<Double>();
			System.out.println("\n----------------------");
			for (Entry<String, Double> n2 : n1.getValue().entrySet()) {
				num.add(n2.getValue());
				System.out.print(n2.getKey() + ":" + n2.getValue() + " ");
			}
			// sd-方差，avg-平均值
			double sd = 0.0, avg = 0.0, abc = 0.0;
			for (int i = 0; i < num.size(); i++) {
				abc += num.get(i);
			}
			avg = abc / clssss.size();
			System.out.println();
			System.out.println("avg:" + avg);
			for (int i = 0; i < num.size(); i++) {
				sd += Math.pow((avg - num.get(i)) / abc, 2);
			}
			System.out.println("和 :" + sd);
			sd = Math.sqrt((sd / clssss.size()));
			System.out.println("#" + n1.getKey() + ":" + sd + "\n-------------");
			// 标准差范围
			if (sd >= 0 && sd < 0.2) {
				noWorkWords.add(n1.getKey());
			}
		}
		return noWorkWords;
	}

	// 使用 calculate()计算训练集词项集合 ----> tf-idf-df
	public HashMap<String, Double> tfidfHashMap() {
		// 初始特征值向量
		HashMap<String, Double> tfidfHashMap = new HashMap<String, Double>();
		// 遍历 wordFreguency
		int count0;
		for (Entry<String, HashMap<String, Double>> entryFreguency : wordFreguency.entrySet()) {
			// 文件名
			String k0 = entryFreguency.getKey();
			HashMap<String, Double> v0 = entryFreguency.getValue();
			// System.out.println("文本个数" + wordFreguency.size());

			// **********获取 tf_id_fdf 值
			HashMap<String, Double> re = calculate(k0, wordDistribution, v0, wordFreguency.size());

			// 对 tfidf 的 HashMap 排序
			List<Entry<String, Double>> list0 = new ArrayList<Entry<String, Double>>(re.entrySet());
			Collections.sort(list0, new Comparator<Map.Entry<String, Double>>() {
				// 降序排序
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// return o1.getValue().compareTo(o2.getValue());
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			// 根据 tfidf值 取出每个文件的前 count0 = n个关键词
			count0 = 0;
			for (Map.Entry<String, Double> mapping0 : list0) {
				tfidfHashMap.put(mapping0.getKey(), 0.0);
				count0++;
				if (count0 == 30) {
					// System.out.println("------------");
					break;
				}
			}
		}
		// 去除关键词
		List<String> nww = noWorkWord();
		for (String delete : nww) {
			tfidfHashMap.remove(delete);
		}
		return tfidfHashMap;
	}

	// 训练集单个文本的 tf-idf 值
	public static HashMap<String, Double> calculate(String fileName, HashMap<String, Double> d0,
			HashMap<String, Double> v1, int sum) {
		// 定义返回值
		HashMap<String, Double> re = new HashMap<String, Double>();

		HashMap<String, Double> classs = new HashMap<String, Double>();
		// ciPin: 词在文章中的次数 即 value
		// maxCiPin: 该文出现最多的词在文章中的出现次数
		// tF
		// iDF
		// ciWen 出现该词的文档个数
		// tfIdf
		String key, kind;
		Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdfdf = null, df = null;
		int count1 = 0;
		kind = fileName.split("[.]")[0].split("-")[1];

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
			// key词项 及 value次数
			key = mapping1.getKey();
			ciPin = mapping1.getValue();
			// 取出频率最高的词
			if (count1 == 0) {
				maxCiPin = mapping1.getValue();
			}
			classs = wordClassDistribution.get(key);
			tF = ciPin / maxCiPin;
			ciWen = d0.get(key);
			iDF = Math.log(d0.size()) / Math.log(2) - Math.log(ciWen) / Math.log(2);
			df = Math.log(classs.get(kind)) / Math.log(2) - Math.log(clssss.get(kind)) / Math.log(2);
			tfIdfdf = (tF * iDF) / (-df);

			/*
			 * System.out.println(key + ":" + "\nciPin:" + ciPin + "\nmaxCiPin:"
			 * + maxCiPin + "\ntF:" + tF + "\nciWen:" + ciWen + "\nIdf:" + iDF +
			 * "\ndf" + df + "\ntfIdfdf:" + tfIdfdf);
			 */

			re.put(key, tfIdfdf);
			count1++;
		}
		return re;
	}

	// 训练集tf_idf_df值
	public HashMap<String, HashMap<String, Double>> baseTF_idf_df() {
		HashMap<String, Double> vv;
		HashMap<String, HashMap<String, Double>> baseTF_idf_df = new HashMap<String, HashMap<String, Double>>();
		for (Entry<String, HashMap<String, Double>> entryFreguency : wordFreguency.entrySet()) {
			String k1 = entryFreguency.getKey();
			HashMap<String, Double> v0 = entryFreguency.getValue();
			// **********获取 tfidf 值
			HashMap<String, Double> re = calculate(k1, wordDistribution, v0, wordFreguency.size());

			// 对 tfidf 的 HashMap 排序
			vv = new HashMap<String, Double>();
			for (Entry<String, Double> mapping0 : re.entrySet()) {
				vv.put(mapping0.getKey(), mapping0.getValue());
			}
			baseTF_idf_df.put(k1, vv);
		}
		return baseTF_idf_df;
	}

	// 单个测试文件的tfidf值
	public HashMap<String, Double> calculateOne(File file) {
		// 定义测试集返回值
		HashMap<String, Double> calculateOne = new HashMap<String, Double>();
		// 文本预处理结果
		HashMap<String, Double> in = new HashMap<String, Double>();

		// 文本预处理
		TxtPreprocessing txtp = new TxtPreprocessing(file);

		in = txtp.getWordFreguency();

		// ciPin: 词在文章中的次数 即 value
		// maxCiPin: 该文出现最多的词在文章中的出现次数
		// tF
		// iDF
		// ciWen 出现该词的文档个数
		// tfIdf
		String key;
		Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdf = null;
		int count2 = 0;
		// 初始化为 List<> 进行降序排序
		List<Entry<String, Double>> list2 = new ArrayList<Entry<String, Double>>(in.entrySet());
		Collections.sort(list2, new Comparator<Map.Entry<String, Double>>() {
			// 降序排序
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (Map.Entry<String, Double> mapping1 : list2) {
			// key 及 value
			key = mapping1.getKey();
			ciPin = mapping1.getValue();
			// 取出频率最高的词
			if (count2 == 0) {
				maxCiPin = mapping1.getValue();
			}
			tF = ciPin / maxCiPin;
			ciWen = wordDistribution.get(key);
			iDF = Math.log(wordDistribution.size()) / Math.log(2) - Math.log(ciWen) / Math.log(2);
			tfIdf = tF * iDF;
			/*
			 * System.out.println(key + ":" + ciPin + "\nciPin:" + ciPin +
			 * "\ntF:" + tF + "\nciWen:" + ciWen + "\nIdf:" + iDF + "\ntfIdf:" +
			 * tfIdf);
			 */
			calculateOne.put(key, tfIdf);
			count2++;
		}
		return calculateOne;
	}

	// 测试集（包含多个测试文件）tf_idf值
	public HashMap<String, HashMap<String, Double>> calculateAll(File file) {
		// 定义测试集返回值
		HashMap<String, HashMap<String, Double>> calculateAll = new HashMap<String, HashMap<String, Double>>();

		// 文本预处理
		TSWordDistribution testGroup = new TSWordDistribution(file);
		HashMap<String, Double> testDistribution = testGroup.getWordDistribution();
		HashMap<String, HashMap<String, Double>> testFreguency = testGroup.getWordFreguency();
		// 遍历测试集
		for (Entry<String, HashMap<String, Double>> entryFreguency : testFreguency.entrySet()) {
			HashMap<String, Double> calculate = new HashMap<String, Double>();
			HashMap<String, Double> in = new HashMap<String, Double>();
			// ciPin: 词在文章中的次数 即 value
			// maxCiPin: 该文出现最多的词在文章中的出现次数
			// tF
			// iDF
			// ciWen 出现该词的文档个数
			// tfIdf
			String key;
			Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdf = null;
			int count2 = 0;
			// 取出 value 值
			in = entryFreguency.getValue();
			// 排序
			List<Entry<String, Double>> list3 = new ArrayList<Entry<String, Double>>(in.entrySet());
			Collections.sort(list3, new Comparator<Map.Entry<String, Double>>() {
				// 降序排序
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// return o1.getValue().compareTo(o2.getValue());
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			for (Map.Entry<String, Double> entry : list3) {
				// key 及 value
				key = entry.getKey();
				ciPin = entry.getValue();
				// 取出频率最高的词
				if (count2 == 0) {
					maxCiPin = entry.getValue();
				}
				tF = ciPin / maxCiPin;
				ciWen = wordDistribution.get(key) + testDistribution.get(key);
				iDF = Math.log(wordDistribution.size() + testFreguency.size()) / Math.log(2)
						- Math.log(ciWen) / Math.log(2);
				tfIdf = tF * iDF;
				/*
				 * System.out.println(key + ":" + ciPin + "\nciPin:" + ciPin +
				 * "\ntF:" + tF + "\nciWen:" + ciWen + "\nIdf:" + iDF +
				 * "\ntfIdf:" + tfIdf);
				 */
				calculate.put(key, tfIdf);

				count2++;
			}
			calculateAll.put(entryFreguency.getKey(), calculate);
		}
		return calculateAll;
	}

}
