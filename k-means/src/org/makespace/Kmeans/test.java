package org.makespace.Kmeans;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;

public class test {

	public static void main(String[] args) throws LoadModelException {
		long time1 = System.currentTimeMillis();
		CNFactory factory = CNFactory.getInstance("models");
		long time2= System.currentTimeMillis();
		String[] words = factory.seg("��ӡ��ý�屨����Ī�ϴ˷������ڼ�ǿͬԽ���ڹ�����ҵ����ĺ�����Ԥ��ӡ�Ƚ���Խ�ϳ��ڰ��������衱��Ǳͧ���׺͡�����Ī˹��������Ѳ���������ڵ�һϵ������װ����������Ī˹��������Ѳ��������ӡ�������˹�������ƣ��߱�������ͻ��������8����Ѯ��ӡ�Ⱦ�������ӡ�߾������������Դˣ��й���������������ǫ��ʾ��������ϣ��ӡ��������������ӡ�߾�������ƽ�ȶ�������");
		long time3 = System.currentTimeMillis();
		for(int i=0;i<words.length;i++){
			System.out.print(words[i]+" | ");
		}
		
		System.out.println((time2-time1));
		System.out.println((time3-time2));

	}

}
