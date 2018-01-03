package Neo4j;


import javax.swing.JOptionPane;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

class Neo4jdriver{
	private Driver driver;
	private Session session;
	
	public Driver getDriver() {
		return driver;
	}

	public Session getSession() {
		return session;
	}

	public Neo4jdriver(String url,String userName,String password){
		if (url.equals("bolt://localhost:7687")) {
			if(userName.equals("neo4j")){
				if(password.equals("123")){
					if (driver == null) {
						driver = GraphDatabase.driver(url, AuthTokens.basic(userName, password));
					}
					session = driver.session();
				}else {
					JOptionPane.showMessageDialog(null, "�������");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "�û�������");
			}
		}else {
			JOptionPane.showMessageDialog(null, "������ַ����");
		}
	}
}