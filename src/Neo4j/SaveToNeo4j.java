package Neo4j;
import static org.neo4j.driver.v1.Values.parameters;

import java.util.List;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import com.aoyuanbo.Utils.DBUtils;



public class SaveToNeo4j {
	private List<String> nodes=null;
	private List<String> edges=null;
	private Driver neo4jdriver=DBUtils.getInstance().getDriver();;
	private Session session=neo4jdriver.session();

	public void setSession(Session session) {
		this.session = session;
	}

	public void conNeo4j() {
//		neo4jdriver = new Neo4jdriver("bolt://localhost:7687","neo4j","123");
		neo4jdriver=DBUtils.getInstance().getDriver();
		session=neo4jdriver.session();
	}
	
	public void closeCon(){
		session.close();
		neo4jdriver.close();
	}
	
	public void saveNode(List<String> list){
		nodes=list;
		String temp[]=null;
		for(String node:nodes){
			temp=node.split(" ");
			session.run("CREATE (n:Node {id:$id,label:$label})", parameters("id",temp[1],"label",temp[3]));
			for(int i=4;i+1<temp.length;i+=2){
			session.run("MATCH (n:Node) where n.id=$id set n+={"+temp[i]+":$value}",parameters("id",temp[0],"value",temp[i+1]));	
			}
		}
	}
	
	public void saveEdge(List<String> list){
			edges = list;
			String temp[] = null;
			for (String edge : edges) {
				temp = edge.split(" ");
				session.run(
						"MATCH(n:Node),(m:Node) where n.id=$source and m.id=$target CREATE (n)-[:edge{id:$id}]->(m)",
						parameters("source", temp[3], "target", temp[5], "id", temp[0]));
				for (int i = 6; i + 1 < temp.length; i += 2) {
					session.run("MATCH ()-[r:edge]->() where r.id=$id set r+={" + temp[i] + ":$value}",
							parameters("id", temp[0], "value", temp[i + 1]));
				}
			} 
	}
}
