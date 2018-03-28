package com.aoyuanbo.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.aoyuanbo.Utils.GetData;

public class IndexAction {
	
	private File indexFile;		//索引文件路径
	private File dataFile;		//数据文件路径
	
	/** 
	 * @param dataFile 数据文件路径
	 */
	public IndexAction(File dataFile) {
		super();
		this.dataFile = dataFile;
		this.indexFile=new File("src\\lucene");		
	}
	
	/**
	 * 
	 * @param indexFile		索引文件路径
	 * @param dataFile		数据文件路径
	 */
	public IndexAction(File indexFile, File dataFile) {
		super();
		this.indexFile = indexFile;
		this.dataFile = dataFile;
	}

	public void createIndex() throws FileNotFoundException, IOException{

		//创建Directory,索引位置(内存和文件)
		Directory dir=FSDirectory.open(indexFile.toPath());
		
		Analyzer analyzer=new WhitespaceAnalyzer();
		//创建索引生成器
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		
		IndexWriter writer=new IndexWriter(dir, config);
		//创建Document
		Document doc=null;
		//Document添加Field
		//1.需要添加索引的文件
//		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		String [][] data;
		data = GetData.getData(dataFile, 0);
		//2.文件内容写入doc中
		for(int i=0;i<data.length;i++){
			doc=new Document();
			doc.add(new TextField("name", data[i][0], Field.Store.YES));
//			doc.add(new TextField("birth", data[i][2],Field.Store.YES));
			doc.add(new TextField("phone", data[i][3], Field.Store.YES));
			doc.add(new TextField("address", data[i][4], Field.Store.YES));
			writer.addDocument(doc);
		}		
		writer.close();
		dir.close();
	}	
}
