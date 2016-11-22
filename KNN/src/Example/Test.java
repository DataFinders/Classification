package Example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.KNNClassiFier;

public class Test {
	public static void main(String[] args) {

		float startTime = System.currentTimeMillis();
		File file24 = new File("C:\\军事-24\\testall");
		File file14 = new File("C:\\体育-14\\testall");
		File file13 = new File("C:\\医学-13\\testall");

		KNNClassiFier kcf = new KNNClassiFier();

		//
		List<File> clas = new ArrayList<File>();
		clas.add(file14);
		clas.add(file24);
		clas.add(file13);

		String[] classs = { "14", "24", "13" };// 文本种类，顺序与上方相同

		String outResult = "";// 输出结果记录
		// 执行
		for (int i = 0; i < clas.size(); i++) {
			File[] files = clas.get(i).listFiles();
			double count = 0;
			for (File file : files) {
				if (kcf.getTV(file).getKind().equals(classs[i]))
					count++;
			}
			outResult += classs[i] + "类准确率为：" + (count / files.length * 100) + "%\n";
		}

		float endTime = System.currentTimeMillis();
		System.out.println(outResult);// 输出结果
		System.out.println("耗时：" + (endTime - startTime) + "s");
	}
}
