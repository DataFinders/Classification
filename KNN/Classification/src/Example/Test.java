package Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import com.chenlb.mmseg4j.example.MMseg4j;

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
		
		File file = new File("E:\\实验室资料\\数据挖掘\\分类算法\\k-近邻\\训练集\\10-24.txt");
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
