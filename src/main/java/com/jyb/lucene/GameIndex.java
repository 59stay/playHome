package com.jyb.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jyb.entity.GameInformation;
import com.jyb.util.DateUtil;
import com.jyb.util.StringUtil;


/**
 * 游戏资源索引类
 * @author Administrator
 * 参考地址:https://pan.baidu.com/s/1zlzmJGP3nXpPIdA-UAYvsg
 */
@Component("gameIndex")
public class GameIndex {

	private Directory dir=null;
	
	@Value("${lucenePath}")
	private String lucenePath;
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		//获取索引文件
		dir=FSDirectory.open(Paths.get(lucenePath));
		//SmartChineseAnalyzer中文分词对象
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		//创建分析器实例
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		//创建写索引实例
		IndexWriter writer=new IndexWriter(dir, iwc);
	 	System.out.println("写入了"+writer.numDocs()+"文档");
		return writer;
	}
	
	/**
	 * 添加游戏索引
	 * @param blog
	 */
	public void addIndex(GameInformation game){
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try{
			IndexWriter writer=getWriter();
			Document doc=new Document();//创建索引文档
			doc.add(new StringField("id",String.valueOf(game.getId()),Field.Store.YES));//StringField不会进行分词的
			doc.add(new StringField("auditStatus",String.valueOf(game.getAuditStatus()),Field.Store.YES)); 
			doc.add(new StringField("isUseful",String.valueOf(game.getIsUseful()),Field.Store.YES)); 
			doc.add(new StringField("gamePicture",String.valueOf(game.getGamePicture()),Field.Store.YES));//StringField不会进行分词的
			doc.add(new TextField("gameName",game.getGameName(),Field.Store.YES));//TextField会做分词处理
			doc.add(new StringField("gameCreationTime",DateUtil.formatDate(game.getGameCreationTime(), "yyyy-MM-dd"),Field.Store.YES));
			doc.add(new TextField("gameDescribe",game.getGameDescribe(),Field.Store.YES));
			writer.addDocument(doc);//将信息添加到索引中
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 更新游戏索引
	 * @param article
	 * @throws Exception
	 */
	public void updateIndex(GameInformation game){
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try{
			IndexWriter writer=getWriter();
			Document doc=new Document();//创建索引文档
			doc.add(new StringField("id",String.valueOf(game.getId()),Field.Store.YES));//加yes指将信息存储到索引中，不加就不存
			doc.add(new StringField("auditStatus",String.valueOf(game.getAuditStatus()),Field.Store.YES)); 
			doc.add(new StringField("isUseful",String.valueOf(game.getIsUseful()),Field.Store.YES)); 
			doc.add(new StringField("gamePicture",String.valueOf(game.getGamePicture()),Field.Store.YES));
			doc.add(new TextField("gameName",game.getGameName(),Field.Store.YES));
			doc.add(new StringField("gameCreationTime",DateUtil.formatDate(game.getGameCreationTime(), "yyyy-MM-dd"),Field.Store.YES));
			doc.add(new TextField("gameDescribe",game.getGameDescribe(),Field.Store.YES));
			writer.updateDocument(new Term("id", String.valueOf(game.getId())), doc);//修改索引文档
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 删除游戏资源索引
	 * @param blogId
	 * @throws Exception
	 */
	public void deleteIndex(String id){
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try{
			IndexWriter writer=getWriter();
			System.out.println("删除前:"+writer.numDocs()+"个");
			writer.deleteDocuments(new Term("id",id));
			writer.forceMergeDeletes(); // 强制删除(合并删除的数据，数据量大时，需要在系统空闲的时间做删除处理【思路:写定时器删除】)
			writer.commit();
			System.out.println("最大文档数:"+writer.maxDoc()+"个，实际文档数"+writer.numDocs());
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 查询游戏信息无高亮
	 * @param q 查询关键字
	 * @return
	 * @throws Exception
	 */
/*	public List<GameInformation> searchNoHighLighter(String q)throws Exception{
		dir=FSDirectory.open(Paths.get(lucenePath));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is=new IndexSearcher(reader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		QueryParser parser=new QueryParser("gameName",analyzer);
		Query query=parser.parse(q);
		QueryParser parser2=new QueryParser("gameDescribe",analyzer);
		Query query2=parser2.parse(q);
		booleanQuery.add(query,BooleanClause.Occur.SHOULD);//组合条件查询 or 
		booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
		TopDocs hits=is.search(booleanQuery.build(), 100);
		List<GameInformation> gameList=new LinkedList<GameInformation>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=is.doc(scoreDoc.doc);
			GameInformation game=new GameInformation();
			game.setId(Integer.parseInt(doc.get(("id"))));
			String name=doc.get("gameName");
			game.setGameName(name);
			gameList.add(game);
		}
		return gameList;
	}*/
	
	/**
	 * 查询游戏信息
	 * @param q 查询关键字
	 * @return
	 * @throws Exception
	 */
	public synchronized List<GameInformation> search(String q)throws Exception{
		dir=FSDirectory.open(Paths.get(lucenePath));//获取索引路径
		IndexReader reader = DirectoryReader.open(dir);//读取索引
		IndexSearcher is=new IndexSearcher(reader);//创建索引查询器
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();//创建中文分词器
		QueryParser parser=new QueryParser("gameName",analyzer);//创建查询对象
		String s_game_name = parser.escape(q);
		Query query=parser.parse(s_game_name);//创建解析对象
		QueryParser parser2=new QueryParser("gameDescribe",analyzer);
		String s_game_describe = parser.escape(q);
		Query query2=parser2.parse(s_game_describe);
		booleanQuery.add(query,BooleanClause.Occur.SHOULD);//组合条件查询 or 
		booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
		long startDate = System.currentTimeMillis();
		TopDocs hits=is.search(booleanQuery.build(), 100);//查询前100条数据
		long endDate = System.currentTimeMillis();
		System.out.println("匹配"+q+",总共花费"+(endDate-startDate)+"毫秒查询到"+hits.totalHits+"个记录");
		//设置内容片断(底层根据得分计算显示的)
		QueryScorer scorer=new QueryScorer(query);  
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);  
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);  
		List<GameInformation> gameList=new LinkedList<GameInformation>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){//通过ScoreDoc对象遍历TopDocs对象
			Document doc=is.doc(scoreDoc.doc);//获得TopDocs对象
			Integer as =  Integer.parseInt(doc.get(("auditStatus")));
			Integer iu =  Integer.parseInt(doc.get(("isUseful")));
			if(as==2 && iu==1 ){//审核通过并且未失效的资源
				GameInformation game=new GameInformation();
				game.setId(Integer.parseInt(doc.get(("id"))));
				game.setGamePicture(doc.get("gamePicture"));
				game.setGameCreationTime(DateUtil.formatString(doc.get(("gameCreationTime")),"yyyy-MM-dd") );
				String name=doc.get("gameName");
				String content=StringUtil.stripHtml(doc.get("gameDescribe"));
				if(name!=null){
					TokenStream tokenStream = analyzer.tokenStream("gameName", new StringReader(name));
					String hName=highlighter.getBestFragment(tokenStream, name);
					if(StringUtil.isEmpty(hName)){
						game.setGameName(name);
					}else{
						game.setGameName(hName);					
					}
				}
				if(content!=null){
					TokenStream tokenStream = analyzer.tokenStream("gameDescribe", new StringReader(content)); 
					String hContent=highlighter.getBestFragment(tokenStream, content);
					if(StringUtil.isEmpty(hContent)){
						if(content.length()<=100){
							game.setGameDescribe(content);
						}else{
							game.setGameDescribe(content.substring(0,100));						
						}
					}else{
							game.setGameDescribe(hContent);	
					}
				}
				gameList.add(game);
			}
		}
		return gameList;
	}
}
