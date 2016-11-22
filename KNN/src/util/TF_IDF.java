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
	// HashMap<String, HashMap<String, Double>> baseTF_idf_df =
	// x.baseTF_idf_df();
	//
	// for (Entry<String, HashMap<String, Double>> d : baseTF_idf_df.entrySet())
	// {
	// System.out.print(d.getKey() + ":");
	// for (Entry<String, Double> mapping0 : d.getValue().entrySet()) {
	// System.out.print(mapping0.getKey() + ":" + mapping0.getValue() + "--");
	// }
	// System.out.print("\n");
	// }
	//
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
			// ���� tfidfֵ ȡ��ÿ���ļ���ǰ20�ؼ���
			count0 = 0;
			for (Map.Entry<String, Double> mapping0 : list0) {
				/*
				 * System.out.println(mapping0.getKey() + " : " +
				 * mapping0.getValue());
				 */
				tfidfHashMap.put(mapping0.getKey(), 0.0);
				count0++;
				if (count0 == 20) {
					// System.out.println("------------");
					break;
				}
			}
			// Set<String> set = tfidfHashMap.keySet();
			// for (String s : set) {
			// System.out.println(s);
			// }
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
			// key �� value
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
		HashMap<String, Double> calculate = new HashMap<String, Double>();
		// �����ı�Ԥ������
		HashMap<String, Double> in = new HashMap<String, Double>();

		// �ı�Ԥ����
		TSWordDistribution testGroup = new TSWordDistribution(file);
		HashMap<String, Double> testDistribution = testGroup.getWordDistribution();
		HashMap<String, HashMap<String, Double>> testFreguency = testGroup.getWordFreguency();
		// �������Լ�
		for (Entry<String, HashMap<String, Double>> entryFreguency : testFreguency.entrySet()) {
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
