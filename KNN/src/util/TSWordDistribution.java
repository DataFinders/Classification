package util;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 * @author zy ������Ҫ���ڻ��ѵ�����д���ֲ�������磺��ø��ĵ����г���ÿ��������ı����������ÿ���ĵ��Ĵ�Ƶ��Ϣ
 *
 */
public class TSWordDistribution {
	private File file;
	private HashMap<String, Double> wordDistribution; // �洢�ĵ�������ֲ���Ϣ
														// ��keyΪ���valueΪ���ֹ��ô�����ı�����
	private HashMap<String, HashMap<String, Double>> wordFreguency; // �洢�ı���Ƶ��Ϣ��keyΪ�ı�����value��һ��HashMap����keyΪ���value���ڸ��ı��г��ֵĴ�����
	private HashMap<String, HashMap<String, Double>> wordClassDistribution;
	private TxtPreprocessing tp; // TxtPreprocessing�࣬���ڶ��ı���Ԥ����

	public TSWordDistribution(File file) {
		this.file = file;
		wordDistribution = new HashMap<String, Double>();
		wordFreguency = new HashMap<String, HashMap<String, Double>>();
		wordClassDistribution = new HashMap<String, HashMap<String, Double>>();
		run();
	}

	/*
	 * ��������
	 */
	private void run() {
		File[] files = file.listFiles();
		for (File f : files) {
			deal(f);
		}
	}

	/*
	 * ���������Դ����ÿһ���ı����д���
	 */
	private void deal(File f) {
		tp = new TxtPreprocessing(f);
		String kind;
		if (!f.getName().contains("-"))
			kind = null;
		else
			kind = f.getName().split("[.]")[0].split("-")[1];
		HashMap<String, Double> m = tp.getWordFreguency(); // ȥ���ظ��Ĵʣ��������Ƶ
		Set<String> keys = m.keySet();
		HashMap<String, Double> map;
		// ����ǰ�ĵ��ķִʽ���������´���ֲ�
		for (String word : keys) { // �������ĵ����ֵ����дʣ��������ظ���
			map = new HashMap<String, Double>();
			if (wordDistribution.containsKey(word)) { // ������ֲ������Ѿ������Ĵʣ���Ĵʵ�value��1
				wordDistribution.put(word, wordDistribution.get(word) + 1.0);
				map = wordClassDistribution.get(word);
				if (kind != null) {
					if (map.containsKey(kind)) {
						map.put(kind, map.get(kind) + 1.0);
					} else {
						map.put(kind, 1.0);
					}
				}

			} else { // ������ֲ����鲻�����Ĵʣ��򽫸Ĵʼ������ֲ����飬����value��ֵΪ1
				wordDistribution.put(word, 1.0);
				if (kind != null) {
					map.put(kind, 1.0);
					wordClassDistribution.put(word, map);
				}
			}
		}
		wordFreguency.put(f.getName(), m); // �洢��ǰ�ĵ��Ĵ�Ƶ�ֲ�
	}

	public HashMap<String, Double> getWordDistribution() {
		return this.wordDistribution;
	}

	public HashMap<String, HashMap<String, Double>> getWordClassDistribution() {
		return this.wordClassDistribution;
	}

	public HashMap<String, HashMap<String, Double>> getWordFreguency() {
		return wordFreguency;
	}

}
