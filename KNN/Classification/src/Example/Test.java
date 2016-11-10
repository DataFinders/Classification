package Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.chenlb.mmseg4j.example.MMseg4j;

import main.BaseVectors;
import main.KNNClassiFier;
import util.TSWordDistribution;
import util.TxtToVector;
//import util.TxtVector;
import util.TxtVector;

public class Test {
	public static void main(String[] args) {
		/*HashMap<String,Double> map = new HashMap<String,Double>();
		map.put("涓婃捣", 1.0);
		map.put("鍖椾含",1.0);
		String key = "鍖椾含";
		if(map.containsKey(key)){
			map.put(key, map.get(key)+1);
		}else{
			map.put(key,1.0);
		}
		Set<String> keys = map.keySet();
		for(String s:keys){
			System.out.println(s+":"+map.get(s));
		}*/
		/*System.out.println();
		File file = new File("data/TrainingSet");
		ArrayList<String> base = new ArrayList<String>();
		base.add("武器");
		base.add("战争");
		base.add("战场");
		base.add("实验室");
		base.add("美国");
		base.add("台湾");
		base.add("导弹");
		base.add("作战");
		base.add("军区");
		TSWordDistribution tsd = new TSWordDistribution(file); 
		HashMap map = tsd.getWordFreguency();
		Set<String> keys = map.keySet();
		for(String key:keys){
			HashMap m = (HashMap) map.get(key);
			TxtToVector tv = new TxtToVector(key,m, base,false);
			TxtVector vector = tv.getVector();
			System.out.print(key+":---");
			double[] vec = vector.getVector();
			for(double d:vec)
				System.out.print(d+" ");
			System.out.println();
		}
		System.out.println("*************************************************************************");
		BaseVectors bvs = new BaseVectors(base, tsd.getWordFreguency());
		ArrayList<TxtVector> baseVectors = bvs.getBaseVectors();
		for(TxtVector baseVector:baseVectors){
			String fileName = baseVector.getFileName();
			double[] vector = baseVector.getVector();
			String kind = baseVector.getKind();
			System.out.print(fileName+"------");
			for(double v:vector)
				System.out.print(v+" ");
			System.out.println();
		}*/
//		System.out.println( Math.log(16)/Math.log(2)-Math.log(2)/Math.log(2));
		/*for(String key:keys){
			System.out.println(key);
			HashMap m = (HashMap) map.get(key);
			Set<String> ks = m.keySet();
			for(String k:ks){
				System.out.print(k+"="+m.get(k)+"");
			}
			System.out.println();
		}*/
		/*HashMap map = tsd.getWordDistribution();
		Set<String> keys = map.keySet();
		int i=0;
		for(String key:keys){
			i++;
			System.out.println(key+":"+map.get(key));
		}
		System.out.println(i);*/
		/*try {
			MMseg4j mmseg = new MMseg4j();
			FileInputStream in = new FileInputStream(file);
			byte[] by = new byte[(int) file.length()];
			in.read(by, 0, by.length);
			String content = new String(by,"utf-8");
			String words[] = mmseg.getResult(content);
			for(String word:words){
				System.out.print(word+"/");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		File file = new File("D:\\84.txt");
		KNNClassiFier kc = new KNNClassiFier(file);
		System.out.println(kc.getTV().getKind());
	}
	
}
