# Classification
## 分类算法
### 一.选取训练集
	暂时使用搜狗文档
---
## 二.TF-IDF预处理（赵琰）
### a)WordDistribution类解决
#### 1.`HashMap`<String,Double> wordDistribution;  
	key：词项  value：包含该词项的文本书
#### 2.`HashMap`<String,HashMap<String, Double>> wordFreguency;
	key：文本名k1   value{HashMap<String, Double> key：词项  value：在文本k1出现的次数}
## 三 计算TF-IDF（秦龙）
---
## 四.生成初始特征值向量（秦龙）
---
## 五.文档向量化（赵琰）
---
## 六.计算距离、分类（赵琰）
---