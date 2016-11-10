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

		// ��ʼ�� WordDistribution
		TSWordDistribution wD = new TSWordDistribution(file);
		// ȡ�� �������
		wordDistribution = wD.getWordDistribution();
		wordFreguency = wD.getWordFreguency();
		// ��ʼ�� TF_IDF ��
		TF_IDF x = new TF_IDF(wordDistribution, wordFreguency);
		HashMap<String, Double> y = x.getTfidfHashMap();
		// ѭ�����
		for (Entry<String, Double> outY : y.entrySet()) {
			System.out.println(outY.getKey() + " : " + outY.getValue());
		}
	}*/

	public HashMap<String, Double> getTfidfHashMap() {
		return tfidfHashMap;
	}

	public TF_IDF(HashMap<String, Double> wordDistribution, HashMap<String, HashMap<String, Double>> wordFreguency) {
		int count0;
		// ���� wordFreguency
		for (Entry<String, HashMap<String, Double>> entryFreguency : wordFreguency.entrySet()) {
			// �ļ���
			String k0 = entryFreguency.getKey();
			// System.out.println(k0);
			// �ļ���Ӧ�Ĵ���
			HashMap<String, Double> v0 = entryFreguency.getValue();
			// System.out.println("�ı�����" + wordFreguency.size());

			// **********��ȡ tfidf ֵ
			HashMap<String, Double> re = calculate(wordDistribution, v0, wordFreguency.size());

			// �� tfidf �� HashMap ����
			List<Entry<String, Double>> list0 = new ArrayList<Entry<String, Double>>(re.entrySet());
			Collections.sort(list0, new Comparator<Map.Entry<String, Double>>() {
				// ��������
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// return o1.getValue().compareTo(o2.getValue());
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			// ���� tfidfֵ ȡ��ÿ���ļ���ǰ20�ؼ���
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
		// ���巵��ֵ
		HashMap<String, Double> re = new HashMap<String, Double>();
		// ciPin: ���������еĴ��� �� value
		// maxCiPin: ���ĳ������Ĵ��������еĳ��ִ���
		// tF
		// iDF
		// ciWen ���ָôʵ��ĵ�����
		// tfIdf
		String key;
		Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdf = null;
		int count1 = 0;
		// ��ʼ��Ϊ List<> ���н�������
		List<Entry<String, Double>> list1 = new ArrayList<Entry<String, Double>>(v1.entrySet());
		Collections.sort(list1, new Comparator<Map.Entry<String, Double>>() {
			// ��������
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (Map.Entry<String, Double> mapping1 : list1) {
			// key �� value
			key = mapping1.getKey();
			ciPin = mapping1.getValue();
			// ȡ��Ƶ����ߵĴ�
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
