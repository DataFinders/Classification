package org.makespace.Kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.corpus.StopWords;
/*
 * ������
 */
class Matrix{
	public String[][] fileName;//�ļ���  + ��������
	public double matrix[][]; //��������
	public int  dimension ; //����ά��
	public int length;      //�������������ı�����
	//���캯��
	public Matrix(int dimension ,int length){
		this.dimension = dimension;
		this.length = length;
		this.matrix = new double[length][dimension];
		this.fileName = new String[length][2];
	}
}

public class TxtKmeans {
	public static void main(String[] args) throws Exception{
		
		long startTime = System.currentTimeMillis();
		
		//����k�ĸ���
		int k_number = 4;
		
		CNFactory factory = CNFactory.getInstance("models");
		StopWords sw= new StopWords("models/stopwords");
        CWSTagger seg = new CWSTagger("models/seg.m");

		File file = new File("D:\\Reduce\\test");
		File[] files = file.listFiles();
		
		//����ÿ���ı��Ĺؼ��ʼ�
		String[][] txtWords = getTxtWords(files,factory,sw,seg);
		System.out.println("���ı��ؼ��ʣ�");
		for(int i=0;i<files.length;i++)
			System.out.println(txtWords[i][0]+":"+txtWords[i][1]);
		
		//���ɹؼ��ʼ�
		String[] keyList = getKeyList(txtWords,files.length);
		System.out.print("�ı��ؼ��ʼ��ϣ�[");
		for(int i=1;i<keyList.length;i++)
			System.out.print(keyList[i]+" ");
		System.out.println("]");
		
		//������������
		Matrix matrix = getMatrix(txtWords, keyList, files.length, keyList.length);
		System.out.println("��������");
		for(int i=0;i<files.length;i++){
			System.out.print(matrix.fileName[i][0]+"-->");
			for(int j=0;j<matrix.dimension;j++)
				System.out.print(matrix.matrix[i][j]+"  ");
			System.out.println("---"+matrix.dimension);
			}
		
		//���ɳ�ʼ����
		double[][] k = new double[k_number][keyList.length];
		//��ʼ����Ϊ0-100ƽ���ֲ�
		for(int n=0;n<k_number;n++){
			for(int i=(n==0?0:(keyList.length)*n/k_number+1);i<(keyList.length)*(n+1)/k_number;i++){
				k[n][i] = (i<=(keyList.length)/k_number*(n+1)?100:0);
			}
		}
		//��ʼ����Ϊ�Խ���
		/*for(int n=0;n<k_number;n++){
			for(int i=0;i<keyList.length;i++)
				k[n][i] = 100/(k_number-1)*n;	
		}*/
		System.out.println("��ʼ���ģ�");
		for(int i=0;i<k_number;i++){
			System.out.print("k"+i+":");
			for(int j=0;j<keyList.length;j++)
				System.out.print(k[i][j]+",");
			System.out.println();
		}
		
		//�������¾��ʵ�֮�����С�ڵ���0.1�������������100
		for(int i=0;i<100;i++){
			
			System.out.println("��"+(i+1)+"����");
			
			//�������������������ĵľ��벢���з���
			iteration(k, matrix,k_number);
			
			
			System.out.println("��ǰ�ı�����ֲ���");
			for(int j=0;j<files.length;j++)
				System.out.println(matrix.fileName[j][0]+" belong to ��cluster"+matrix.fileName[j][1]);
			//��¼��ǰ����
			double[][] tmp_k = k;
			
			//�����ʵ�
			k = getNewK(matrix,k_number);
			
			for(int i1=0;i1<k_number;i1++){
				System.out.print("k"+i1+":");
				for(int j=0;j<keyList.length;j++)
					System.out.print(k[i1][j]+",");
				System.out.println();
			}
			//���¾��ʵ�����С��0.1��ֹͣ����
			int tmp = 1;
			for(int n=0;n<k_number;n++){
				if(getDistance(tmp_k[n], k[n])>0.1)
					tmp = 0;
			}
			if(tmp == 1)
				break;
			
			
		}
		
		//������
		String[] result = new String[k_number];
		for(int i=0;i<files.length;i++){
			for(int n=0;n<k_number;n++){
				if(matrix.fileName[i][1].equals(""+(n+1))){
					result[n] += matrix.fileName[i][0]+" ";
					result[n] = result[n].replace("null", "");
					break;
				}
			}
		}
		System.out.println("�����");
		for(int n=0;n<k_number;n++)
			System.out.println("cluster"+(n+1)+"��" + result[n]);
		
		long endTime = System.currentTimeMillis();
		System.out.println("��ʱ��" + (endTime-startTime)/1000+"s");
	}
	
	
	
	
	
	/*
	 * ���ҹؼ���
	 */
		public static ArrayList<String> GetKeyword(String News,int keywordsNumber,StopWords sw,CWSTagger seg) throws Exception{
	        ArrayList<String> keywords=new ArrayList<String>();
	        AbstractExtractor key = new WordExtract(seg,sw);
	        Map<String,Integer> ans = key.extract(News, keywordsNumber);
	        for (Map.Entry<String, Integer> entry : ans.entrySet()) {
	           String keymap = entry.getKey().toString();
	           String value = entry.getValue().toString();
	           keymap = keymap + "=" + value;
	           keywords.add(keymap);
	        }
	       return keywords;
	    }

	/*
	 * ����ÿ���ı��Ĺؼ��ʼ�
	 */

	public static String[][] getTxtWords(File[] files,CNFactory factory,StopWords sw,CWSTagger seg) throws Exception {
		String[][] keyList = new String[files.length][2];
		//System.out.println(files.length);
		for(int i=0;i<files.length;i++)
		{
			@SuppressWarnings("resource")
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(files[i]),"UTF-8"));
			String temp=null;
			StringBuffer sb= new StringBuffer();
			temp=br.readLine();
			while(temp!=null)
				{
					sb.append(temp+" ");
					temp=br.readLine();
				}
			String words = factory.tag2String(sb.toString());
			
			//��ϴ����
			words = words.replace("��", "");
			words = words.replace("��", "");
			words = words.replace("& ", "");
			words = words.replace("&&&&", "");
			words = words.replace("��", "");
			words = words.replace("��", "");
			words = words.replace("��", "");
			words = words.replace("��", "");
			words = words.replace("��", "");
			words = words.replace("��", "");
			words = words.replace("\r", "");
			words = words.replace("\n", "");
			words = words.replace("\r\n", "");
			words = words.replace("\n\r", "");
			words = words.replace("nbsp", "");
			words = words.replace("&nbsp&nbsp&nbsp&nbsp", "");
			
			String[] tmpWords = words.split(" ");
			words = "";
			for(int k=0;k<tmpWords.length;k++){
				String[] word = tmpWords[k].split("/");
				if(word[1].equals("����") || word[1].equals("����"))//ֻ�������ʺͶ���
				{
					words += word[0] + " ";
				}
			}
			words = clearRepeat(words);
			keyList[i][0] = files[i].getName();
			keyList[i][1] = words;
		}
		return keyList;
	}
	/*
	 * ȥ��ÿ���ı��ʼ����ظ��Ĵʣ���Ϊÿ���ʰ��ճ���Ƶ�ʸ���Ȩ��
	 */
	private static String clearRepeat(String words){
		String[] startWords = words.split(" ");//��ʼ�ʼ����ʼ��д��ڴ����ظ��Ĵ�
		String[] Words = new String[startWords.length];//���ڱ����ʼ�ʼ���ȥ�ظ���Ĵʼ�
		int[] value = new int[startWords.length];//���ڱ���ÿ���ʵ�Ȩ�أ��±���Words��Ӧ
		int j = 0;//��¼Words[]�����±�
		for(int i = 0;i<startWords.length;i++){
			int k;//���ڽ���tmpWords[i]��Words�еĶ�Ӧ���±�
			if(startWords[i].equals("") || startWords[i].equals("��")){ //ȥ��ֵΪ�ջ�Ϊ���ǡ��������FNLP���Ա�ע���ǡ��ƺ������ʻ��߶��ʣ���Ҫ�ڴ˴�ȥ��
				continue;
			}else if((k=inArray(Words, startWords[i])) != -1){//���tmpWords[i]��Words�У���ôʶ�ӦȨ�ؼ�10
				value[k] += 10;
			}else{//���tmpWords[i]����Words�У��򽫸ôʼ���Words�����У���Ӧ�ĳ�ʼȨ��Ϊ10
				value[j] = 10;
				Words[j++] = startWords[i];
			}
		}
		int sortValue[][] = sortArray(value, j);
		String wordList ="";//�ַ���������ϲ�Ȩ�غ�Ĵʼ�����ʽΪ��word_1=value_1 word_2=value_2 ..... word_j=value_j ��
		for(int i = 0; i<j;i++){
			String tmp = Words[sortValue[i][1]]+"="+sortValue[i][0];
			wordList += tmp+" ";
		}
		return wordList;
	}
	/*
	 * �ж�word�Ƿ���String����words�У�����ڷ�����words�����е��±꣬�����򷵻�-1
	 */
	private static int inArray(String[] words,String word){
		for(int i = 0;i<words.length;i++){
			if(words[i] == null){
				break;
			}else if(words[i].equals(word)){
				return i;
			}
		}
		return -1;
	}
	/*
	 * ��value���齵��������һ����ά���飬��һά��valueԪ�ؽ���������;�ڶ�ά��valueԪ�ض�Ӧ��ԭ�±�
	 */
	private static int[][] sortArray(int[] value,int length){
		int[][] sortValue = new int[length][2];
		for(int i=0;i<length;i++){
			sortValue[i][0] = value[i];
			sortValue[i][1] = i;
		}
		for(int i=0;i<length;i++){
			int k = i;
			for(int j=i+1;j<length;j++){
				if(sortValue[k][0] <= sortValue[j][0])
					k = j;
			}
			int t0 = sortValue[k][0];
			sortValue[k][0] = sortValue[i][0];
			sortValue[i][0] = t0;
			int t1 = sortValue[k][1];
			sortValue[k][1] = sortValue[i][1];
			sortValue[i][1] = t1;
		}
		return sortValue;
	}
	/*
	 * ��ȡ�ı��������ɹؼ��ʼ�
	 */
	public static String[] getKeyList(String[][] txtKeys,int filesLength ) throws Exception{
		String keyList = new String();
		for(int i=0;i<filesLength;i++){
			String[] keys = txtKeys[i][1].split(" ");
			for(int k=0;k<keys.length;k++){
				String k_v[] = keys[k].split("=");
				if(keyList.indexOf(k_v[0]) == -1)
					keyList = keyList + " " + k_v[0];
			}
		}
		String[] keylists = keyList.split(" ");
		String[] keyLists = new String[keylists.length-1];
		for(int i=0;i<keylists.length-1;i++)
			keyLists[i] = keylists[i+1];
		return keyLists;
	}
	
	/*
	 * ���ɾ���
	 */
	public static Matrix getMatrix(String[][] txtKeys,String[] keyList,int filesLength,int dimension) {
		Matrix matrix = new Matrix(dimension, filesLength);
		for(int i=0;i<filesLength;i++){
			matrix.fileName[i][0] = txtKeys[i][0]; 
			for(int k=0;k<keyList.length;k++){
				String[] keys = txtKeys[i][1].split(" ");
				double tmp = 0;
				for(int j=0;j<keys.length;j++)
				{
					String[] k_v = keys[j].split("=");
					if(keyList[k].equals(k_v[0]))
						tmp = (double)Integer.parseInt(k_v[1]) ;
				}
				matrix.matrix[i][k]= tmp;
			}
			matrix.fileName[i][1] = "";
		}
		return matrix;
	}
	
	/*
	 * �������������������ĵľ��룬�����з���
	 */
	public static void iteration(double[][] k,Matrix matrix,int k_number) throws UnsupportedEncodingException{
		for(int i=0;i<matrix.length;i++){
			double[][] cluster = new double[k_number][2];//{{1,0},{2,0},{3,0}};
			for(int n=0;n<k_number;n++){
				cluster[n][0] = (double)(n+1);
				cluster[n][1] = 0;
			}
				
			//���㵱ǰ����������ʵ�ľ���
			for(int j=0;j<k_number;j++)
				cluster[j][1] = getDistance(matrix.matrix[i], k[j]);
			
			//��ѯ��̾���
			double distance = getDistance(matrix.matrix[i], k[0]);
			for(int j=1;j<k_number;j++){
				distance = Math.min(getDistance(matrix.matrix[i], k[j]),distance);
			}
			
			//����
			for(int j=0;j<k_number;j++)
				if(cluster[j][1] == distance)
					matrix.fileName[i][1] = ""+(int)cluster[j][0];
		}
	}
	
	/*
	 * ����������֮��ľ���
	 */
	private static double getDistance(double[]  vector,double[] k) throws UnsupportedEncodingException{
		double sum = 0;
		for(int i=0;i<vector.length;i++){
			sum += Math.pow((k[i]-vector[i]), 2);
		}
		return Math.sqrt(sum);
	}
	
	/*
	 * �����ʵ�
	 */
	private static double[][] getNewK(Matrix matrix,int k_number) {
		double[][] k = new double[k_number][matrix.dimension];
		for(int i=0;i<matrix.dimension;i++){
			double[] clu_sum = new double[k_number];//{0,0,0}
			int[] clu_num =new int[k_number] ;//{0,0,0};
			for(int j=0;j<matrix.length;j++){
				for(int n=0;n<k_number;n++)
					if(matrix.fileName[j][1].equals(""+(n+1))){
						clu_sum[n] += matrix.matrix[j][i];
						clu_num[n]++;
						break;
					}
			}
			for(int n=0;n<k_number;n++){
				if(clu_num[n] != 0)
					k[n][i] = clu_sum[n]/clu_num[n];
				else{
					k[n][i] = 0; 
					}
			}
		}
		return k;
	}
	
}
