package com.aoyuanbo.action;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchAction {
	
	private File indexFile;
	private IndexSearcher searcher;
	
	
	
	public void setIndexFile(File indexFile) {
		this.indexFile = indexFile;
	}



	public IndexSearcher getSearcher() {
		return searcher;
	}



	/**
	 * 
	 * @param searchText 查询的内容
	 * @throws Exception
	 */
	public TopDocs search(String searchText,String field) throws Exception{

		//创建Directory，获取索引
		Directory directory=FSDirectory.open(indexFile.toPath());
		//创建IndexReader，读取索引
		IndexReader reader=DirectoryReader.open(directory);
		//创建索引查找器
		searcher=new IndexSearcher(reader);
		//创建查询分词器
		Analyzer analyzer=new StandardAnalyzer();
		//创建查询解析器
		QueryParser queryParser=new QueryParser(field, analyzer);
		Query query=queryParser.parse(searchText);
		TopDocs docs=searcher.search(query, 10);
//		System.out.println(docs.totalHits);
//		for (ScoreDoc scoreDoc : docs.scoreDocs) {
//
//			Document document=searcher.doc(scoreDoc.doc);
//
//			System.out.println(document.get("address"));
//		}
		reader.close();
		directory.close();
		return docs;
	}
}
