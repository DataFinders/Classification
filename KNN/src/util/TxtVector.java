package util;

/**
 * 
 * @author zy �洢�ı�������������ݽṹ�������ı������ı��������ı����
 */
public class TxtVector {

	private String fileName; // �ı���
	private double[] vector; // �ı�����
	private String kind; // �ı����

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
