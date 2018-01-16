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

import com.aoyuanbo.GetData;

public class IndexAction {
	
	private File indexFile;		//�����ļ�·��
	private File dataFile;		//�����ļ�·��
	
	/** 
	 * @param dataFile �����ļ�·��
	 */
	public IndexAction(File dataFile) {
		super();
		this.dataFile = dataFile;
		this.indexFile=new File("src\\lucene");		
	}
	
	/**
	 * 
	 * @param indexFile		�����ļ�·��
	 * @param dataFile		�����ļ�·��
	 */
	public IndexAction(File indexFile, File dataFile) {
		super();
		this.indexFile = indexFile;
		this.dataFile = dataFile;
	}

	public void createIndex() throws FileNotFoundException, IOException{

		//����Directory,����λ��(�ڴ���ļ�)
		Directory dir=FSDirectory.open(indexFile.toPath());
		
		Analyzer analyzer=new WhitespaceAnalyzer();
		//��������������
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		
		IndexWriter writer=new IndexWriter(dir, config);
		//����Document
		Document doc=null;
		//Document���Field
		//1.��Ҫ����������ļ�
//		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		String [][] data;
		data = GetData.getData(dataFile, 0);
		//2.�ļ�����д��doc��
		for(int i=0;i<data.length;i++){
			doc=new Document();
			doc.add(new TextField("name", data[i][0], Field.Store.YES));
			doc.add(new TextField("birth", data[i][2],Field.Store.YES));
			doc.add(new TextField("phone", data[i][3], Field.Store.YES));
			doc.add(new TextField("address", data[i][4], Field.Store.YES));
			writer.addDocument(doc);
		}		
		writer.close();
		dir.close();
	}	
}
