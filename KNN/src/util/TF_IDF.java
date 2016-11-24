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
	// wordFreguency ���� hashmap
	public HashMap<String, Double> wordFreguencySon;
	public static HashMap<String, Double> clssss;

	// public static void main(String[] args) {
	// // ��ʼ�� WordDistribution
	// TSWordDistribution wD = new TSWordDistribution(new
	// File("data/TrainingSet"));
	// // ȡ�� �������
	// HashMap<String, Double> Distribution = wD.getWordDistribution();
	// HashMap<String, HashMap<String, Double>> Freguency =
	// wD.getWordFreguency();
	// HashMap<String, HashMap<String, Double>> classFreguency =
	// wD.getWordClassDistribution();
	// // ��ʼ��TF_IDF ��
	// TF_IDF x = new TF_IDF(Distribution, Freguency, classFreguency);
	// List<String> word = x.noWorkWord();
	// for (String tmp : word) {
	// System.out.println(tmp);
	// }
	// System.out.println("���ô�" + word.size());
	// System.out.println("������" + wordClassDistribution.size());
	// }

	public TF_IDF(HashMap<String, Double> wordDistribution, HashMap<String, HashMap<String, Double>> wordFreguency,
			HashMap<String, HashMap<String, Double>> wordClassDistribution) {
		// �����������
		this.wordDistribution = wordDistribution;
		TF_IDF.wordFreguency = wordFreguency;
		TF_IDF.wordClassDistribution = wordClassDistribution;
		// ʶ���������������ı���
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

	// ȥ���ؼ���
	public List<String> noWorkWord() {
		List<String> noWorkWords = new ArrayList<String>();
		for (Entry<String, HashMap<String, Double>> n1 : wordClassDistribution.entrySet()) {
			// ���value
			List<Double> num = new ArrayList<Double>();
			System.out.println("\n----------------------");
			for (Entry<String, Double> n2 : n1.getValue().entrySet()) {
				num.add(n2.getValue());
				System.out.print(n2.getKey() + ":" + n2.getValue() + " ");
			}
			// sd-���avg-ƽ��ֵ
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
			System.out.println("�� :" + sd);
			sd = Math.sqrt((sd / clssss.size()));
			System.out.println("#" + n1.getKey() + ":" + sd + "\n-------------");
			// ��׼�Χ
			if (sd >= 0 && sd < 0.2) {
				noWorkWords.add(n1.getKey());
			}
		}
		return noWorkWords;
	}

	// ʹ�� calculate()����ѵ��������� ----> tf-idf-df
	public HashMap<String, Double> tfidfHashMap() {
		// ��ʼ����ֵ����
		HashMap<String, Double> tfidfHashMap = new HashMap<String, Double>();
		// ���� wordFreguency
		int count0;
		for (Entry<String, HashMap<String, Double>> entryFreguency : wordFreguency.entrySet()) {
			// �ļ���
			String k0 = entryFreguency.getKey();
			HashMap<String, Double> v0 = entryFreguency.getValue();
			// System.out.println("�ı�����" + wordFreguency.size());

			// **********��ȡ tf_id_fdf ֵ
			HashMap<String, Double> re = calculate(k0, wordDistribution, v0, wordFreguency.size());

			// �� tfidf �� HashMap ����
			List<Entry<String, Double>> list0 = new ArrayList<Entry<String, Double>>(re.entrySet());
			Collections.sort(list0, new Comparator<Map.Entry<String, Double>>() {
				// ��������
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// return o1.getValue().compareTo(o2.getValue());
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			// ���� tfidfֵ ȡ��ÿ���ļ���ǰ count0 = n���ؼ���
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
		// ȥ���ؼ���
		List<String> nww = noWorkWord();
		for (String delete : nww) {
			tfidfHashMap.remove(delete);
		}
		return tfidfHashMap;
	}

	// ѵ���������ı��� tf-idf ֵ
	public static HashMap<String, Double> calculate(String fileName, HashMap<String, Double> d0,
			HashMap<String, Double> v1, int sum) {
		// ���巵��ֵ
		HashMap<String, Double> re = new HashMap<String, Double>();

		HashMap<String, Double> classs = new HashMap<String, Double>();
		// ciPin: ���������еĴ��� �� value
		// maxCiPin: ���ĳ������Ĵ��������еĳ��ִ���
		// tF
		// iDF
		// ciWen ���ָôʵ��ĵ�����
		// tfIdf
		String key, kind;
		Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdfdf = null, df = null;
		int count1 = 0;
		kind = fileName.split("[.]")[0].split("-")[1];

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
			// key���� �� value����
			key = mapping1.getKey();
			ciPin = mapping1.getValue();
			// ȡ��Ƶ����ߵĴ�
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

	// ѵ����tf_idf_dfֵ
	public HashMap<String, HashMap<String, Double>> baseTF_idf_df() {
		HashMap<String, Double> vv;
		HashMap<String, HashMap<String, Double>> baseTF_idf_df = new HashMap<String, HashMap<String, Double>>();
		for (Entry<String, HashMap<String, Double>> entryFreguency : wordFreguency.entrySet()) {
			String k1 = entryFreguency.getKey();
			HashMap<String, Double> v0 = entryFreguency.getValue();
			// **********��ȡ tfidf ֵ
			HashMap<String, Double> re = calculate(k1, wordDistribution, v0, wordFreguency.size());

			// �� tfidf �� HashMap ����
			vv = new HashMap<String, Double>();
			for (Entry<String, Double> mapping0 : re.entrySet()) {
				vv.put(mapping0.getKey(), mapping0.getValue());
			}
			baseTF_idf_df.put(k1, vv);
		}
		return baseTF_idf_df;
	}

	// ���������ļ���tfidfֵ
	public HashMap<String, Double> calculateOne(File file) {
		// ������Լ�����ֵ
		HashMap<String, Double> calculateOne = new HashMap<String, Double>();
		// �ı�Ԥ������
		HashMap<String, Double> in = new HashMap<String, Double>();

		// �ı�Ԥ����
		TxtPreprocessing txtp = new TxtPreprocessing(file);

		in = txtp.getWordFreguency();

		// ciPin: ���������еĴ��� �� value
		// maxCiPin: ���ĳ������Ĵ��������еĳ��ִ���
		// tF
		// iDF
		// ciWen ���ָôʵ��ĵ�����
		// tfIdf
		String key;
		Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdf = null;
		int count2 = 0;
		// ��ʼ��Ϊ List<> ���н�������
		List<Entry<String, Double>> list2 = new ArrayList<Entry<String, Double>>(in.entrySet());
		Collections.sort(list2, new Comparator<Map.Entry<String, Double>>() {
			// ��������
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (Map.Entry<String, Double> mapping1 : list2) {
			// key �� value
			key = mapping1.getKey();
			ciPin = mapping1.getValue();
			// ȡ��Ƶ����ߵĴ�
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

	// ���Լ���������������ļ���tf_idfֵ
	public HashMap<String, HashMap<String, Double>> calculateAll(File file) {
		// ������Լ�����ֵ
		HashMap<String, HashMap<String, Double>> calculateAll = new HashMap<String, HashMap<String, Double>>();

		// �ı�Ԥ����
		TSWordDistribution testGroup = new TSWordDistribution(file);
		HashMap<String, Double> testDistribution = testGroup.getWordDistribution();
		HashMap<String, HashMap<String, Double>> testFreguency = testGroup.getWordFreguency();
		// �������Լ�
		for (Entry<String, HashMap<String, Double>> entryFreguency : testFreguency.entrySet()) {
			HashMap<String, Double> calculate = new HashMap<String, Double>();
			HashMap<String, Double> in = new HashMap<String, Double>();
			// ciPin: ���������еĴ��� �� value
			// maxCiPin: ���ĳ������Ĵ��������еĳ��ִ���
			// tF
			// iDF
			// ciWen ���ָôʵ��ĵ�����
			// tfIdf
			String key;
			Double ciPin = null, maxCiPin = null, tF = null, iDF = null, ciWen = null, tfIdf = null;
			int count2 = 0;
			// ȡ�� value ֵ
			in = entryFreguency.getValue();
			// ����
			List<Entry<String, Double>> list3 = new ArrayList<Entry<String, Double>>(in.entrySet());
			Collections.sort(list3, new Comparator<Map.Entry<String, Double>>() {
				// ��������
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// return o1.getValue().compareTo(o2.getValue());
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			for (Map.Entry<String, Double> entry : list3) {
				// key �� value
				key = entry.getKey();
				ciPin = entry.getValue();
				// ȡ��Ƶ����ߵĴ�
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
