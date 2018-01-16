package com.aoyuanbo.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.aoyuanbo.Utils.IndexUtils;
import com.aoyuanbo.action.IndexAction;

public class IndexDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 427165584034224587L;
	private final JPanel contentPanel = new JPanel();
	private JTextField fileText;
	private JTextField indexText;
	
	private File dataFile=null;
	private File indexFile=new File("C:\\Users\\lenovo\\Documents");;
	
	private IndexAction indexAction=null;

	public File getDataFile() {
		return dataFile;
	}

	public File getIndexFile() {
		return indexFile;
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		//设置界面样式
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//				| UnsupportedLookAndFeelException e2) {
//			System.err.println("错误");
//		}
//		try {
//			IndexDialog dialog = new IndexDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	} 

	/**
	 * Create the dialog.
	 */
	public IndexDialog() {
		//设置界面样式
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			System.err.println("错误");
		}
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] {30, 30, 30, 30, 30, 30, 0, 30};
			gbl_panel.rowHeights = new int[] {30, 0, 30, 0, 30, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblNewLabel = new JLabel("\u6587\u4EF6\u4F4D\u7F6E");
				GridBagConstraints fileLabel = new GridBagConstraints();
				fileLabel.insets = new Insets(0, 0, 5, 5);
				fileLabel.gridx = 1;
				fileLabel.gridy = 1;
				panel.add(lblNewLabel, fileLabel);
			}
			{
				fileText = new JTextField();
				GridBagConstraints gbc_fileText = new GridBagConstraints();
				gbc_fileText.gridwidth = 4;
				gbc_fileText.insets = new Insets(0, 0, 5, 5);
				gbc_fileText.fill = GridBagConstraints.HORIZONTAL;
				gbc_fileText.gridx = 3;
				gbc_fileText.gridy = 1;
				panel.add(fileText, gbc_fileText);
				fileText.setColumns(10);
			}
			{
				JButton fileButton = new JButton("\u6253\u5F00\u6587\u4EF6");
				fileButton.setSize(20, 10);
				//打开文件按钮动作
				fileButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						JFileChooser jFileChooser=new JFileChooser();
						jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						jFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("EXCEL文件（*.xls）", "xls"));
						jFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("EXCEL文件（*.xlsx）", "xlsx"));
						jFileChooser.showDialog(new JLabel(), "选择文件");
						dataFile=jFileChooser.getSelectedFile();
						if (dataFile!=null) {
							fileText.setText(dataFile.toString());
						}						
					}
				});
				GridBagConstraints gbc_fileButton = new GridBagConstraints();
				gbc_fileButton.insets = new Insets(0, 0, 5, 0);
				gbc_fileButton.gridx = 7;
				gbc_fileButton.gridy = 1;
				panel.add(fileButton, gbc_fileButton);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("\u7D22\u5F15\u4F4D\u7F6E");
				GridBagConstraints indexLabel = new GridBagConstraints();
				indexLabel.insets = new Insets(0, 0, 0, 5);
				indexLabel.gridx = 1;
				indexLabel.gridy = 4;
				panel.add(lblNewLabel_1, indexLabel);
			}
			{
				indexText = new JTextField("C:\\Users\\lenovo\\Documents");
				GridBagConstraints gbc_indexText = new GridBagConstraints();
				gbc_indexText.gridwidth = 4;
				gbc_indexText.insets = new Insets(0, 0, 0, 5);
				gbc_indexText.fill = GridBagConstraints.HORIZONTAL;
				gbc_indexText.gridx = 3;
				gbc_indexText.gridy = 4;
				panel.add(indexText, gbc_indexText);
				indexText.setColumns(10);
			}
			{
				JButton indexButton = new JButton("\u7D22\u5F15\u8DEF\u5F84");
				indexButton.setSize(30, 20);
				//索引路径选择
				indexButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser jFileChooser=new JFileChooser();
						jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jFileChooser.showDialog(new JLabel(), "选择路径");		
						indexFile=jFileChooser.getSelectedFile();
						if(indexFile!=null){
							indexText.setText(indexFile.toString());
						}
					}
				});
				GridBagConstraints gbc_indexButton = new GridBagConstraints();
				gbc_indexButton.gridx = 7;
				gbc_indexButton.gridy = 4;
				panel.add(indexButton, gbc_indexButton);
			}
		}
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u5B8C\u6210");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(fileText.getText().trim().equals("")){
							JOptionPane.showMessageDialog(null, "请选择数据文件");
						}else {
							if (dataFile.getName().endsWith("xls") || dataFile.getName().endsWith("xlsx")) {
								indexAction = new IndexAction(indexFile, dataFile);
								try {
									IndexUtils.setIndexFile(indexFile);
									indexAction.createIndex();
									JOptionPane.showMessageDialog(null, "索引构建完毕");
									IndexDialog.this.dispose();
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}else {
								JOptionPane.showMessageDialog(null, "文件类型不符合");
							}
						}
					}
				});
				buttonPanel.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				//取消按钮
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						IndexDialog.this.dispose();
					}
				});
				buttonPanel.add(cancelButton);
			}
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
