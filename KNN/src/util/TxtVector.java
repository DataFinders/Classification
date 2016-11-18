package util;

/**
 * 
 * @author zy 存储文本向量化后的数据结构，包括文本名、文本向量、文本类别
 */
public class TxtVector {

	private String fileName; // 文本名
	private double[] vector; // 文本向量
	private String kind; // 文本类别

	public TxtVector(String fileName, boolean isTRSet) {
		this.fileName = fileName;
		if (isTRSet)
			this.kind = fileName.split("[.]")[0].split("-")[1];
		else
			kind = null;
	}

	public String getFileName() {
		return fileName;
	}

	public double[] getVector() {
		return vector;
	}

	public void setVector(double[] vector) {
		this.vector = vector;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void displayVector() {
		System.out.print(fileName + ":");
		for (double d : vector) {

			System.out.print(d + " ");
		}
		System.out.println();
	}
}
