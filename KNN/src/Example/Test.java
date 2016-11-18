package Example;

import java.io.File;

import main.KNNClassiFier;

public class Test {
	public static void main(String[] args) {

		float startTime = System.currentTimeMillis();
		File file24 = new File("C:\\军事-24\\testall");
		File file14 = new File("C:\\体育-14\\testall");
		KNNClassiFier kcf = new KNNClassiFier();
		File[] files = file14.listFiles();
		double count = 0;
		for (File file : files) {
			if (kcf.getTV(file).getKind().equals("14"))
				count++;
		}
		float endTime = System.currentTimeMillis();
		System.out.println("准确率为：" + (count / files.length * 100) + "%");
		System.out.println("耗时：" + (endTime - startTime) + "s");
	}
}
