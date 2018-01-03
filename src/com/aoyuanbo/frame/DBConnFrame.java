package com.aoyuanbo.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.aoyuanbo.Utils.DBUtils;

public class DBConnFrame extends JFrame {
	
	private final Font font = new Font(null, Font.PLAIN, 14);
	private JLabel urlLabel;
	private JLabel userLabel;
	private JLabel pwdLabel;
	private JTextField urlTextField;
	private JTextField userTextField;
	private JPasswordField pwdField;
	private JButton connButton;
	private JButton cancelButton;	

	private String url,userName,password;
	
	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	void init(){
		this.getContentPane().setLayout(null);
		urlLabel=new JLabel("主    机");
		urlLabel.setFont(font);
		urlLabel.setBounds(40, 20, 80, 30);
		userLabel=new JLabel("用户名");
		userLabel.setFont(font);
		userLabel.setBounds(40, 80, 80, 30);
		pwdLabel=new JLabel("密    码");
		pwdLabel.setFont(font);
		pwdLabel.setBounds(40, 140, 80, 30);
		urlTextField=new JTextField("bolt://127.0.0.1:7687");
		urlTextField.setFont(font);
		urlTextField.setBounds(150, 20, 200, 30);
		userTextField=new JTextField();
		userTextField.setFont(font);
		userTextField.setBounds(150, 80, 200, 30);
		pwdField=new JPasswordField();
		pwdField.setBounds(150, 140, 200, 30);
		connButton=new JButton("连接");
		connButton.setBounds(60, 200, 80, 30);
		connButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				connActionPerformed();
				
			}
		});
		cancelButton=new JButton("取消");
		cancelButton.setBounds(250, 200, 80, 30);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	protected void connActionPerformed() {
		url=urlTextField.getText().toString();
		userName=userTextField.getText().toString();
		password=String.valueOf(pwdField.getPassword());
		DBUtils dbUtils=DBUtils.getInstance();
		dbUtils.connectToNeo4j(url, userName, password);
		if(DBUtils.isSuccess()==true){
			dispose();
			JOptionPane.showMessageDialog(null, "连接成功");			
		}
	}
	

	public DBConnFrame() {
		init();
		this.add(urlLabel);
		this.add(userLabel);
		this.add(pwdLabel);
		this.add(urlTextField);
		this.add(userTextField);
		this.add(pwdField);
		this.add(connButton);
		this.add(cancelButton);
		this.setSize(400, 300);
		this.setLocationRelativeTo(getOwner());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		DBConnFrame dbFrame=new DBConnFrame();
	}
}
