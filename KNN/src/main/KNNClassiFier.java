package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import util.TF_IDF;
import util.TSWordDistribution;
import util.TxtPreprocessing;
import util.TxtToVector;
import util.TxtVector;

public class KNNClassiFier {
	private File file; // 待分类的文本
	private static BaseVectors bvs; // 训练集文本向量集合
	private TxtVector tv; // 待分类文本向量化后的结果
	private static TSWordDistribution trsWD; // 训练集词项分布详情况
	private ArrayList<String> baseWords; // 学习获得的词项集合
	private ArrayList<TxtVector> baseVectors;
	private HashMap fileWordCount;

	public KNNClassiFier() {
		this.trsWD = new TSWordDistribution(new File("data/TrainingSet"));
		this.baseWords = new ArrayList<String>();
		// 获得训练集学习获得的词项集合
		TF_IDF ti = new TF_IDF(trsWD.getWordDistribution(), trsWD.getWordFreguency(), trsWD.getWordClassDistribution());
		HashMap<String, Double> y = ti.tfidfHashMap();
		for (Entry<String, Double> outY : y.entrySet()) {
			baseWords.add(outY.getKey());
			System.out.println(outY.getKey());
		}
		// 获得训练集向量集合
		bvs = new BaseVectors(baseWords, ti.baseTF_idf_df());

		baseVectors = bvs.getBaseVectors();

	}

	public TxtVector getTV(File file) {
		this.file = file;

		this.tv = new TxtVector(file.getName(), false);

		run();
		return this.tv;
	}

	private void run() {
		// 对待分类文件预处理
		TxtPreprocessing tp = new TxtPreprocessing(file);
		fileWordCount = tp.getWordFreguency();

		// 将待分类文本向量化
		TxtToVector ttv = new TxtToVector(file.getName(), fileWordCount, baseWords, false);
		TxtVector fileTV = ttv.getVector();

		// 计算测试文本与训练集中所有文本的欧式距离
		HashMap<String, Double> distance = getDistance(fileTV);
		// System.out.println(distance.size());

		// 取出距离测试文本最近的10个训练文本
		List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(distance.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			// 降序排序
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		ArrayList<String> List = new ArrayList<>();
		int count0 = 0;
		for (Map.Entry<String, Double> mapping0 : list) {
			List.add(mapping0.getKey());
			count0++;
			if (count0 == 20) {
				break;
			}
		}
		// 判断类别
		int count_14 = 0, count_24 = 0;
		for (String l : List) {
			// System.out.println(l);
			if (l.endsWith("14.txt"))
				count_14++;
			else
				count_24++;
		}
		tv.setKind((count_14 >= count_24) ? ("" + 14) : ("" + 24));
		// System.out.println(count_14 + ":" + count_24);
		// System.out.println(tv.getFileName() + "---" + tv.getKind());

	}

	private HashMap<String, Double> getDistance(TxtVector fileTV) {
		HashMap<String, Double> distance = new HashMap<>();
		double[] fileVector = fileTV.getVector();
		for (TxtVector baseVector : baseVectors) {
			double[] vector = baseVector.getVector();
			double sum = 0;
			for (int i = 0; i < vector.length; i++) {
				sum += (fileVector[i] - vector[i]) * (fileVector[i] - vector[i]);
			}
			distance.put(baseVector.getFileName(), sum);
		}
		return distance;
	}

}
