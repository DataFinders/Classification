package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import util.TF_IDF;
import util.TSWordDistribution;
import util.TxtPreprocessing;
import util.TxtToVector;
import util.TxtVector;

public class KNNClassiFier {
	private File file;	//��������ı�
	private BaseVectors bvs;	//ѵ�����ı���������
	private TxtVector tv;	//�������ı���������Ľ��
	private TSWordDistribution trsWD ; //ѵ��������ֲ������
	private ArrayList<String> baseWords;	//ѧϰ��õĴ����
	private ArrayList<TxtVector> baseVectors;
	private HashMap fileWordCount;
	
	public KNNClassiFier(File file){
		this.file = file;
		this.trsWD = new TSWordDistribution(new File("data/TrainingSet"));
		baseWords = new ArrayList<String>();
		tv = new TxtVector(file.getName(), false);
		run();
	} 
	
	private void run(){
		
		//���ѵ����ѧϰ��õĴ����
		TF_IDF ti = new TF_IDF(trsWD.getWordDistribution(), trsWD.getWordFreguency());
		HashMap<String, Double> y = ti.getTfidfHashMap();
		//Set<String> keys = y.keySet();
		int i=0;
		for (Entry<String, Double> outY : y.entrySet()) {
			baseWords.add(outY.getKey());
		}
		
		//���ѵ������������
		bvs = new BaseVectors(baseWords, trsWD.getWordFreguency());
		baseVectors = bvs.getBaseVectors();
		
		//�Դ������ļ�Ԥ����
		TxtPreprocessing tp = new TxtPreprocessing(file);
		fileWordCount = tp.getWordFreguency();
		
		//���������ı�������
		TxtToVector ttv = new TxtToVector(file.getName(), fileWordCount, baseWords, false);
		TxtVector fileTV = ttv.getVector();
		
		//��������ı���ѵ�����������ı���ŷʽ����
		HashMap<String,Double> distance = getDistance(fileTV);
		//System.out.println(distance.size());
		
		//ȡ����������ı������10��ѵ���ı�
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
			if (count0 == 10) {
				// System.out.println("------------");
				break;
			}
		}
		//System.out.println(count0);
		//�ж����
		int count_14=0,count_24=0;
		for(String l:List){
			if(l.endsWith("14.txt"))
				count_14++;
			else
				count_24++;
		}
		System.out.println(count_14+"---"+count_24);
		tv.setKind((count_14>=count_24)?(""+14):(""+24));
		
	}
	
	private HashMap<String,Double> getDistance(TxtVector fileTV){
		HashMap<String,Double> distance = new HashMap<>();
		double[] fileVector = fileTV.getVector();
		System.out.println(baseVectors.size());
		for(TxtVector baseVector:baseVectors){
			double[] vector = baseVector.getVector();
			double sum = 0;
			for(int i=0;i<vector.length;i++){
				sum += (fileVector[i]-vector[i])*(fileVector[i]-vector[i]);
			}
			distance.put(baseVector.getFileName(), sum);
		}
		return distance;
	}
	
	public TxtVector getTV(){
		return this.tv;
	}
}
