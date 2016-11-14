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
		
		float startTime = System.currentTimeMillis();
		File file24 = new File("E:\\实验室资料\\数据挖掘\\分类算法\\k-近邻\\测试集\\军事-24\\testall");
		File file14 = new File("E:\\实验室资料\\数据挖掘\\分类算法\\k-近邻\\测试集\\体育-14\\testall");
		KNNClassiFier kcf = new KNNClassiFier();
		File[] files = file24.listFiles();
		double count = 0;
		for(File file:files){
			if(kcf.getTV(file).getKind().equals("24"))
				count++;
		}

		float endTime = System.currentTimeMillis();
		System.out.println("准确率为："+(count/files.length*100)+"%");
		System.out.println("耗时："+(endTime-startTime)+"s");
	}
	
}
