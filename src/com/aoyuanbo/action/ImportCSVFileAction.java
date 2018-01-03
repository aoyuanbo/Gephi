package com.aoyuanbo.action;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.graph.api.GraphModel;

import com.aoyuanbo.Utils.GraphUtils;
import com.csvreader.CsvReader;

public class ImportCSVFileAction {
	
	public AttributeColumnsController getAttributeColumnsController() {
		return attributeColumnsController;
	}


	public GraphModel getGraphModel() {
		return graphModel;
	}


	public String getFileType() {
		return fileType;
	}


	public String getGraphType() {
		return graphType;
	}


	public Character getSeparator() {
		return separator;
	}


	public String getEncodingFormat() {
		return encodingFormat;
	}


	public File getFile() {
		return file;
	}


	public String[] getEdgeColumnNames() {
		return edgeColumnNames;
	}


	public String[] getNodeColumnNames() {
		return nodeColumnNames;
	}


	public Class[] getEdgeColumnType() {
		return edgeColumnType;
	}


	public Class[] getNodeColumnType() {
		return nodeColumnType;
	}


	private String fileType;
	private String graphType;
	private Character separator;
	private String encodingFormat;
	private File file;
	private String [] edgeColumnNames;
	private String [] nodeColumnNames;
	private Class [] edgeColumnType;
	private Class [] nodeColumnType;
	
    private AttributeColumnsController attributeColumnsController=GraphUtils.getAttributeColumnsController();
	private GraphModel graphModel=GraphUtils.getGraphModel();     
	
	public void processFile(String fileType,String graphType,Character separator,String encodingFormat,File file) {
		
		this.fileType=fileType;
		this.graphType=graphType;
		this.separator=separator;
		this.encodingFormat=encodingFormat;
		this.file=file;
		
		CsvReader csvReader=null;
		try {
			csvReader = new CsvReader(file.toString());
			csvReader.readHeaders();
			String head=csvReader.getRawRecord();
			if(fileType.equals("边CSV文件")){
				edgeColumnNames=head.split(String.valueOf(separator));
				Class []temp=new Class[edgeColumnNames.length];
				for(int i=0; i<edgeColumnNames.length; i++){
					temp[i]=String.class;
				}
				edgeColumnType=temp;
		        attributeColumnsController.importCSVToEdgesTable(graphModel.getGraph(), file, ',', Charset.forName(encodingFormat), edgeColumnNames, edgeColumnType, true);
		       
			}else if (fileType.equals("点CSV文件")) {
				nodeColumnNames=head.split(String.valueOf(separator));
				Class []temp=new Class[nodeColumnNames.length];
				for(int i=0; i<nodeColumnNames.length; i++){
					temp[i]=String.class;
				}
				nodeColumnType=temp;
		        attributeColumnsController.importCSVToNodesTable(graphModel.getGraph(), file, ',', Charset.forName(encodingFormat), nodeColumnNames, nodeColumnType, false);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}	finally {
			csvReader.close();
		}	
        
		
	}
	
	
}
