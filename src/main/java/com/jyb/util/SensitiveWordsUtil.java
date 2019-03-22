package com.jyb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 使用DFA算法实现敏感词过滤工具栏
 * @author jyb
 * @date 2019年3月21日12:20:17
 */
public class SensitiveWordsUtil {
	public static String filePath = "D:\\dictionary.txt";//敏感词库文件路径
	//public static String filePath = "/home/dictionary.txt";
	public static Set<String> words;
	public static Map<String,String> wordMap;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则
	static{
		SensitiveWordsUtil.words = readTxtByLine(filePath);
		addBadWordToHashMap(SensitiveWordsUtil.words);
	}
	
	
	/**
	 * 获取敏感词文件
	 * @param path
	 * @return
	 */
	 public static Set<String> readTxtByLine(String path){  
    	Set<String> keyWordSet = new HashSet<String>();
        File file=new File(path);  
        if(!file.exists()){      //文件流是否存在
        	return keyWordSet;
        }
        BufferedReader reader=null;  
        String temp=null;  
        try{  
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));  
            while((temp=reader.readLine())!=null){  
                keyWordSet.add(temp);
            }  
        } catch(Exception e){  
            e.printStackTrace();  
        } finally{  
            if(reader!=null){  
                try{  
                    reader.close();  
                }catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }
        return keyWordSet;
    }
	 
	   /**
	     * 检查文字中是否包含敏感字符，检查规则如下：<br>
	     * @param txt
	     * @param beginIndex
	     * @param matchType
	     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	     * @version 1.0
	     */
	    @SuppressWarnings({ "rawtypes"})
	    public static int checkBadWord(String txt,int beginIndex,int matchType){
	        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
	        int matchFlag = 0;     //匹配标识数默认为0
	        char word = 0;
	        Map nowMap = wordMap;
	        for(int i = beginIndex; i < txt.length() ; i++){
	            word = txt.charAt(i);
	            nowMap = (Map) nowMap.get(word);     //获取指定key
	            if(nowMap != null){     //存在，则判断是否为最后一个
	                matchFlag++;     //找到相应key，匹配标识+1 
	                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
	                    flag = true;       //结束标志位为true   
	                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
	                        break;
	                    }
	                }
	            }
	            else{     //不存在，直接返回
	                break;
	            }
	        }
	        /*“粉饰”匹配词库：“粉饰太平”竟然说是敏感词
	         * “个人”匹配词库：“个人崇拜”竟然说是敏感词
	         * if(matchFlag < 2 && !flag){     
	            matchFlag = 0;
	        }*/
	        if(!flag){     
	            matchFlag = 0;
	        }
	        return matchFlag;
	    }
	    
	    /**
		 * 判断文字是否包含敏感字符
		 * @param txt  文字
		 * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
		 * @return 若包含返回true，否则返回false
		 * @version 1.0
		 */
		public static boolean isContaintBadWord(String txt,int matchType){
			boolean flag = false;
			for(int i = 0 ; i < txt.length() ; i++){
				int matchFlag = checkBadWord(txt, i, matchType); //判断是否包含敏感字符
				if(matchFlag > 0){    //大于0存在，返回true
					flag = true;
				}
			}
			return flag;
		}
		
		 /**
		 * 替换敏感字字符
		 * @param txt
		 * @param matchType
		 * @param replaceChar 替换字符，默认*
		 * @version 1.0
		 */
		public static String replaceBadWord(String txt,int matchType,String replaceChar){
			String resultTxt = txt;
			Set<String> set = getBadWord(txt, matchType);     //获取所有的敏感词
			Iterator<String> iterator = set.iterator();
			String word = null;
			String replaceString = null;
			while (iterator.hasNext()) {
				word = iterator.next();
				replaceString = getReplaceChars(replaceChar, word.length());
				resultTxt = resultTxt.replaceAll(word, replaceString);
			}
			
			return resultTxt;
		}
		
		/**
		 * 获取文字中的敏感词
		 * @param txt 文字
		 * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
		 * @return
		 * @version 1.0
		 */
		public static Set<String> getBadWord(String txt , int matchType){
			Set<String> sensitiveWordList = new HashSet<String>();
			for(int i = 0 ; i < txt.length() ; i++){
				int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
				if(length > 0){    //存在,加入list中
					sensitiveWordList.add(txt.substring(i, i+length));
					i = i + length - 1;    //减1的原因，是因为for会自增
				}
			}
			
			return sensitiveWordList;
		}
		
		/**
		 * 获取替换字符串
		 * @param replaceChar
		 * @param length
		 * @return
		 * @version 1.0
		 */
		private static String getReplaceChars(String replaceChar,int length){
			String resultReplace = replaceChar;
			for(int i = 1 ; i < length ; i++){
				resultReplace += replaceChar;
			}
			
			return resultReplace;
		}
		
		/**
		 * TODO 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
		 * @param keyWordSet 敏感词库
		 * @author jyb
		 * @date 2019年3月21日12:19:47
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static void addBadWordToHashMap(Set<String> keyWordSet) {
	        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
	        String key = null;  
	        Map nowMap = null;
	        Map<String, String> newWorMap = null;
	        //迭代keyWordSet
	        Iterator<String> iterator = keyWordSet.iterator();
	        while(iterator.hasNext()){
	            key = iterator.next();    //关键字
	            nowMap = wordMap;
	            for(int i = 0 ; i < key.length() ; i++){
	                char keyChar = key.charAt(i);       //转换成char型
	                Object wordMap = nowMap.get(keyChar);       //获取
	                
	                if(wordMap != null){        //如果存在该key，直接赋值
	                    nowMap = (Map) wordMap;
	                }
	                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
	                    newWorMap = new HashMap<String,String>();
	                    newWorMap.put("isEnd", "0");     //不是最后一个
	                    nowMap.put(keyChar, newWorMap);
	                    nowMap = newWorMap;
	                }
	                
	                if(i == key.length() - 1){
	                    nowMap.put("isEnd", "1");    //最后一个
	                }
	            }
	        }
	    }
		
		
		//大部分敏感词汇在10个以内，直接返回缓存的字符串
	    public static String[] starArr={"*","**","***","****","*****","******","*******","********","*********","**********"};
	 
	    /**
	     * 生成n个星号的字符串
	     * @param length
	     * @return
	     */
	    private static String getStarChar(int length) {
	        if (length <= 0) {
	            return "";
	        }
	        //大部分敏感词汇在10个以内，直接返回缓存的字符串
	        if (length <= 10) {
	            return starArr[length - 1];
	        }
	 
	        //生成n个星号的字符串
	        char[] arr = new char[length];
	        for (int i = 0; i < length; i++) {
	            arr[i] = '*';
	        }
	        return new String(arr);
	    }
	    
	    
	    /**
	     * 过滤字符串中的敏感词汇
	     * @param content   文本
	     * @param sensitiveWord   敏感词汇
	     * @return
	     */
	    public  static String filterSensitiveWords(String content, String sensitiveWord) {
	        if (content == null || sensitiveWord == null) {
	            return content;
	        }
	        //获取和敏感词汇相同数量的星号
	        String starChar = getStarChar(sensitiveWord.length());
	 
	        //替换敏感词汇
	        return content.replace(sensitiveWord,starChar);
	    }
	    
		/*public static void main(String[] args) {
			System.out.println("敏感词的数量：" + SensitiveWordsUtil.wordMap.size());
			String sensitiveWords = "你全家都是畜生习近平太多的伤感日朱恩杰你死全家的怀你全家都是畜生也许只局限于饲养基地 荧幕中的情节，主人公尝操你妈试着去用操你妈某种方式渐渐操你妈的很潇洒地释自杀指南怀那操你妈些自己经历的伤感。"
					+ "然后法轮功 我们操你妈操你妈操x你妈操你妈操你妈操你妈操你妈操你妈操你妈操你妈操你妈操你妈操你妈操你妈的扮演的操你妈角色就是跟随着曹尼玛喜红客联盟 怒哀乐而过于牵强的把自己的情感也操你妈附加于银幕情节中，然后感动操你妈就流泪，"
					+ "难过就躺在某操你妈一个人的怀里尽毒品情的日你吓人阐述心扉或者手机卡复制器一个人一操你妈杯红酒一部电影在夜三级片 深人静的晚上，关上电话静操你妈静的发呆着。";
	        System.out.println("待检测语句字数：" + sensitiveWords.length());
	        long beginTime = System.currentTimeMillis();
			Set<String> set = SensitiveWordsUtil.getBadWord(sensitiveWords,2);
			long endTime = System.currentTimeMillis();
			System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
			System.out.println("总共消耗时间为：" + (endTime - beginTime));
			String mgc ="";
			for (String strSet : set) {
				mgc+=strSet+",";
			}
			mgc = mgc.substring(0,mgc.length()-1);
			String[] mgcArray = mgc.split(",");
			String  strMgc = "";
		    for (int i = 0; i < mgcArray.length; i++) {
		    	if(i==0){
		    		strMgc = filterSensitiveWords(sensitiveWords,mgcArray[i]);
		    	}else{
		    		strMgc  = filterSensitiveWords(strMgc,mgcArray[i]);	
		    	}
			}
		    System.out.println(strMgc);
		}*/
		
}
