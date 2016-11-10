# Classification
## 分类算法
## 一.选取训练集
	暂时使用搜狗文档
## 二.TF-IDF预处理（赵琰）
### 使用WordDistribution类解决
#### 1.HashMap\<String,Double\> wordDistribution;  
	key：词项  value：包含该词项的文本书
#### 2.HashMap\<String,HashMap\<String, Double\>\> wordFreguency;
	key：文本名k1   value{HashMap<String, Double> key：词项  value：在文本k1出现的次数}
## 三.计算TF-IDF（秦龙）
	单个文件的TF-IDF值
>`HashMap\<String, Double\>` calculate ( `HashMap\<String, Double\>` d0, `HashMap\<String, Double\>` v1, int sum )
>>d0  = wordDistribution
>>v1  = wordFreguency 的 value
>>sum = 文件数量
>>`返回此文件的TF-IDF值`
>
## 四.生成初始特征值向量（秦龙）
>TF_IDF(`HashMap\<String, Double\>` wordDistribution, `HashMap\<String, HashMap\<String, Double\>\>` wordFreguency)
>>wordDistribution = wordDistribution
>>wordFreguency = wordFreguency
>
	for（所有文本）{
		去重 前20个
	}
	return tfidfHashMap
## 五.文档向量化（赵琰）
## 六.计算距离、分类（赵琰）