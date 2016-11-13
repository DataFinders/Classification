package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class stopWords {
	public static ArrayList<String> stopWords = new ArrayList<String>();
	static {
		File f = new File("data/stopwords.dic");
		byte[] by  = new byte[(int) f.length()];
		try {
			FileInputStream in = new FileInputStream(f);
			in.read(by, 0, by.length);
			String s = new String(by, "utf-8");
			s = s.replace("\r", "");
			String[] words = s.split("\n");
			for(String word:words)
				stopWords.add(word);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
