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
	private File file; // ��������ı�
	private static BaseVectors bvs; // ѵ�����ı���������
	private TxtVector tv; // �������ı���������Ľ��
	private static TSWordDistribution trsWD; // ѵ��������ֲ������
	private ArrayList<String> baseWords; // ѧϰ��õĴ����
	private ArrayList<TxtVector> baseVectors;
	private HashMap fileWordCount;

	public KNNClassiFier() {

		this.trsWD = new TSWordDistribution(new File("data/TrainingSet"));
		this.baseWords = new ArrayList<String>();
		// ���ѵ����ѧϰ��õĴ����
		TF_IDF ti = new TF_IDF(trsWD.getWordDistribution(), trsWD.getWordFreguency(), trsWD.getWordClassDistribution());
		HashMap<String, Double> y = ti.tfidfHashMap();
		// ͳ�ƴ���ϸ���
		int sum = 0;
		for (Entry<String, Double> outY : y.entrySet()) {
			baseWords.add(outY.getKey());
			sum++;
			System.out.println(outY.getKey());
		}
		System.out.println(sum + "������");
		// ���ѵ������������
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
		// �Դ������ļ�Ԥ����
		TxtPreprocessing tp = new TxtPreprocessing(file);
		fileWordCount = tp.getWordFreguency();

		// ���������ı�������
		TxtToVector ttv = new TxtToVector(file.getName(), fileWordCount, baseWords, false);
		TxtVector fileTV = ttv.getVector();

		// ��������ı���ѵ�����������ı���ŷʽ����
		HashMap<String, Double> distance = getDistance(fileTV);
		// System.out.println(distance.size());

		// ȡ����������ı������ n ��ѵ���ı�
		List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(distance.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			// ��������
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
		// �ж����
		HashMap<String, Double> kind = new HashMap<String, Double>();
		String kindd = "";

		for (String l : List) {
			kindd = l.split("[.]")[0].split("-")[1];
			if (kind.containsKey(kindd)) {
				kind.put(kindd, kind.get(kindd) + 1.0);
			} else {
				kind.put(kindd, 1.0);
			}
		}

		List<Entry<String, Double>> list1 = new ArrayList<Entry<String, Double>>(kind.entrySet());
		Collections.sort(list1, new Comparator<Map.Entry<String, Double>>() {
			// ��������
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		tv.setKind(list1.get(0).getKey());
		// System.out.println(list1.toString() + list1.get(0).getKey());
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
