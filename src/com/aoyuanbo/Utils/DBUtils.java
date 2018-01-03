package com.aoyuanbo.Utils;

import javax.swing.JOptionPane;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

public class DBUtils {
	
	private static volatile DBUtils instance;
	private Driver driver;
	private Session session;
	private String url="bolt://localhost:7687";
	private String userName="neo4j";
	private String password="123";
	private static  boolean isSuccess=false;
	
	public static void setSuccess(boolean isSuccess) {
		DBUtils.isSuccess = isSuccess;
	}

	public Driver getDriver() {
		return driver;
	}	

	public Session getSession() {
		return session;
	}
	
	public static boolean isSuccess() {
		return isSuccess;
	}
	
	private DBUtils(){}
	
	//单例模式
	public static DBUtils getInstance() {
		if(instance==null){
			synchronized (DBUtils.class) {
				if(instance==null)
					instance=new DBUtils();
			}
		}
		return instance;
	}
	
	public void connectToNeo4j(String url,String userName,String password){
		
		if (url.equals(this.url) || url.equals("bolt://localhost:7687") || url.equals("bolt://127.0.0.1:7687")) {
			if(userName.equals(this.userName)){
				if(password.equals(this.password)){
					try {
						driver = GraphDatabase.driver(url, AuthTokens.basic(userName, password));
						session = driver.session();
						isSuccess=true;
					} catch (ServiceUnavailableException e) {
						JOptionPane.showMessageDialog(null, "服务未启动，请启动服务");
					}
				}else {
					JOptionPane.showMessageDialog(null, "密码错误");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "用户名错误");
			}
		}else {
			JOptionPane.showMessageDialog(null, "主机地址错误");
		}
		
	}
}
