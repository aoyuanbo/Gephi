package com.aoyuanbo.Neo4j;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractGml {
	private List<String> node;
	private List<String> edge;
	private String result;
	//读取GML文件，转换成字符串
	public String readGML(String url){
		
		File file=new File(url);
		StringBuffer sbBuffer=new StringBuffer();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			int i;
			while((i=reader.read())!=-1){
				if(((char)i) != '\n'){
					sbBuffer.append((char)i);
				}else {
					sbBuffer.append(' ');
				}
				
			}
			reader.close();
//			System.out.println(sbBuffer);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		result=sbBuffer.toString().replaceAll("\\s{2,}", " ");
		return result;
	}
	
	//获取GML字符串，抽取node信息，存储在list中
	public List<String> getNode(String gml){
		node=new ArrayList<>();
		String nodeReg="node \\[(.*?)\\]";
		Pattern nodePattern=Pattern.compile(nodeReg);		
		Matcher matcher = null;
		if (result!=null) {
			matcher=nodePattern.matcher(result);
			while(matcher.find()){
				node.add(matcher.group(1).trim());
			}
		}
		return node;	
	}
	//获取GML字符串，抽取edge信息，存储在list中	
	public List<String> getEdge(String gml){
		edge=new ArrayList<>();
		String edgeReg="edge \\[(.*?)\\]";
		Pattern edgePattern=Pattern.compile(edgeReg);		
		Matcher matcher = null;
		if (result!=null) {
			matcher=edgePattern.matcher(result);
			while(matcher.find()){
				edge.add(matcher.group(1).trim());
			}
		}
		return edge;
	}
	
	public static void main(String[] args) {
		String gmlString="C:\\Users\\lenovo\\Desktop\\11.gml";
		ExtractGml extractGml=new ExtractGml();
		extractGml.readGML(gmlString);
		for (String string : extractGml.getNode(gmlString)) {
			String s[]=string.split(" ");
			for (String string2 : s) {
				System.out.print(string2);
				System.out.print("\n");
			}
		}
	}
}
