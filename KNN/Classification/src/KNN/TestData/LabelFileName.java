package KNN.TestData;

import java.io.File;

public class LabelFileName {
	public static void main(String[] args) {
		File file = new File("E:\\实验室资料\\数据挖掘\\分类算法\\k-近邻\\训练集");
		File[] files = file.listFiles();
		for(File f:files){
				if(!f.getName().contains("-")){
				String newname = f.getName().split("[.]")[0]+"-14.txt";
				String path = f.getParent();
				File fnew = new File(path+"/"+newname);
				f.renameTo(fnew);
			}
		}
	}
}
