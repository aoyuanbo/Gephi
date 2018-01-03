package Neo4j;
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
	//��ȡGML�ļ���ת�����ַ���
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		result=sbBuffer.toString().replaceAll("\\s{2,}", " ");
		return result;
	}
	
	//��ȡGML�ַ�������ȡnode��Ϣ���洢��list��
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
	//��ȡGML�ַ�������ȡedge��Ϣ���洢��list��	
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
	
	
}
