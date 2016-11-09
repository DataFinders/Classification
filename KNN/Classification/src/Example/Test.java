package Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.chenlb.mmseg4j.example.MMseg4j;

import util.TSWordDistribution;
import util.TxtVector;

public class Test {
	public static void main(String[] args) {
		/*HashMap<String,Double> map = new HashMap<String,Double>();
		map.put("上海", 1.0);
		map.put("北京",1.0);
		String key = "北京";
		if(map.containsKey(key)){
			map.put(key, map.get(key)+1);
		}else{
			map.put(key,1.0);
		}
		Set<String> keys = map.keySet();
		for(String s:keys){
			System.out.println(s+":"+map.get(s));
		}*/
		
		File file = new File("data/TS");
		HashMap<String,Double> base = new HashMap<String,Double>();
		base.put("武器",1.0);
		base.put("战争",1.0);
		base.put("军队",1.0);
		base.put("美国",1.0);
		base.put("装备",1.0);
		base.put("战场",1.0);
		base.put("作战",1.0);
		base.put("实验室",1.0);
		base.put("导弹",1.0);
		TSWordDistribution tsd = new TSWordDistribution(file); 
		HashMap map = tsd.getWordFreguency();
		Set<String> keys = map.keySet();
		for(String key:keys){
			HashMap m = (HashMap) map.get(key);
			TxtVector tv = new TxtVector(m, base);
			double[] vector = tv.getVector();
			System.out.print(key+":---");
			for(double d:vector)
				System.out.print(d+" ");
			System.out.println();
		}
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
		
	}
	
}
