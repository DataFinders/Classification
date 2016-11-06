package org.makespace.Kmeans;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;

public class test {

	public static void main(String[] args) throws LoadModelException {
		long time1 = System.currentTimeMillis();
		CNFactory factory = CNFactory.getInstance("models");
		long time2= System.currentTimeMillis();
		String[] words = factory.seg("据印度媒体报道，莫迪此访致力于加强同越南在国防工业领域的合作，预计印度将向越南出口包括“黑鲨”反潜艇鱼雷和“布拉莫斯”超音速巡航导弹在内的一系列武器装备。“布拉莫斯”超音速巡航导弹由印度与俄罗斯联合研制，具备超音速突防能力。8月下旬，印度决定在中印边境部署这款导弹。对此，中国国防部发言人吴谦表示，“我们希望印方多做有利于中印边境地区和平稳定的事情");
		long time3 = System.currentTimeMillis();
		for(int i=0;i<words.length;i++){
			System.out.print(words[i]+" | ");
		}
		
		System.out.println((time2-time1));
		System.out.println((time3-time2));

	}

}
