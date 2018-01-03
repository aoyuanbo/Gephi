package com.aoyuanbo.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.gephi.preview.api.G2DTarget;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;

import com.aoyuanbo.PreviewPanel;
import com.aoyuanbo.Utils.GraphUtils;
import com.aoyuanbo.action.ImportCSVFileAction;
import com.aoyuanbo.action.PreviewSketch;




public class ImportFileFrame {

	public PreviewSketch getPreviewSketch() {
		return previewSketch;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	private static Frame mainFrame;
	private static PreviewController previewController=GraphUtils.getPreviewController();
	private static PreviewSketch previewSketch;
	
	private JFrame frame;
	private JPanel panel;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	
	private JComboBox fileTypeComboBox;
	private JComboBox graphTypeComboBox;
	private JComboBox separatorComboBox;
	private JComboBox encodingFormatComboBox;
	
	private JButton openButton;
	private JButton nextButton;
	private JButton cancelButton;
	private JButton preButton;
	private JButton finishButton;
	
	private String fileType="边CSV文件";
	private String graphType="有向图";
	private String separator="逗号";
	private Character cSeparator=',';
	private String encodingFormat="GBK";
	private JTextField txtFile;
	private JFileChooser choose; 
	private File file;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args,Frame frame) {
		mainFrame=frame;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ImportFileFrame window = new ImportFileFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImportFileFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("导入文件");
		frame.setBounds(500, 100, 500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 494, 377);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel attrSettingPanel = new JPanel();
		attrSettingPanel.setBounds(0, 0, 492, 377);
		panel.add(attrSettingPanel);
		attrSettingPanel.setLayout(null);
		
		label_1 = new JLabel("\u5BFC\u5165\u6587\u4EF6\u7C7B\u578B\uFF1A");
		label_1.setBounds(41, 30, 105, 19);
		label_1.setVerticalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setEnabled(true);
		attrSettingPanel.add(label_1);
		
		fileTypeComboBox = new JComboBox();
		fileTypeComboBox.setBounds(210, 29, 98, 23);
		fileTypeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileType=(String) fileTypeComboBox.getSelectedItem();
			}
		});
		fileTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"\u8FB9CSV\u6587\u4EF6", "\u70B9CSV\u6587\u4EF6", "EXCEL\u6587\u4EF6"}));
		fileTypeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		attrSettingPanel.add(fileTypeComboBox);
		
		label_2 = new JLabel("\u5BFC\u5165\u56FE\u7C7B\u578B\uFF1A");
		label_2.setBounds(41, 98, 105, 19);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		attrSettingPanel.add(label_2);
		
		graphTypeComboBox = new JComboBox();
		graphTypeComboBox.setBounds(210, 97, 98, 23);
		graphTypeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphType=(String) graphTypeComboBox.getSelectedItem();
			}
		});
		graphTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"\u6709\u5411\u56FE", "\u65E0\u5411\u56FE"}));
		graphTypeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		attrSettingPanel.add(graphTypeComboBox);
		
		label_3 = new JLabel("\u5206\u9694\u7B26\uFF1A");
		label_3.setBounds(41, 152, 105, 19);
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		attrSettingPanel.add(label_3);
		
		separatorComboBox = new JComboBox();
		separatorComboBox.setBounds(210, 151, 98, 23);
		separatorComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				separator=(String) separatorComboBox.getSelectedItem();
				switch (separator) {
				case "逗号":
					cSeparator=',';
					break;
				case "分号":
					cSeparator=';';
					break;
				case "制表符Tab":
					cSeparator='	';
					break;
				case "空格":
					cSeparator=' ';
					break;
				default: 
					break;
				}
			}
		});
		separatorComboBox.setModel(new DefaultComboBoxModel(new String[] {"\u9017\u53F7", "\u5206\u53F7", "\u5236\u8868\u7B26Tab", "\u7A7A\u683C"}));
		separatorComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		attrSettingPanel.add(separatorComboBox);
		
		label_5 = new JLabel("\u9009\u62E9\u4E00\u4E2A\u6587\u4EF6\u8F93\u5165\uFF1A");
		label_5.setBounds(41, 278, 172, 19);
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		attrSettingPanel.add(label_5);
		
		label_4 = new JLabel("\u7F16\u7801\u683C\u5F0F\uFF1A");
		label_4.setBounds(41, 211, 105, 19);
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		attrSettingPanel.add(label_4);
		
		encodingFormatComboBox = new JComboBox();
		encodingFormatComboBox.setBounds(210, 210, 98, 23);
		encodingFormatComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				encodingFormat=(String) encodingFormatComboBox.getSelectedItem();
			}
		});
		encodingFormatComboBox.setModel(new DefaultComboBoxModel(new String[] {"GBK", "UTF-8"}));
		encodingFormatComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		attrSettingPanel.add(encodingFormatComboBox);
		
		txtFile = new JTextField();
		txtFile.setBounds(41, 326, 233, 20);
		txtFile.setColumns(10);
		attrSettingPanel.add(txtFile);
		
		openButton = new JButton("......");
		openButton.setBounds(326, 324, 69, 20);
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e2) {
					System.err.println("错误");
				}
				choose = new JFileChooser();
				if (fileType.equals("边CSV文件")||fileType.equals("点CSV文件")) {
					choose.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV文件（*.csv）", "csv"));
				} else {
					choose.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("EXCEL文件（*.xls）", "xls"));
					choose.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("EXCEL文件（*.xlsx）", "xlsx"));
				}
				int op = choose.showOpenDialog(ImportFileFrame.this.getFrame());
				if (op == JFileChooser.CANCEL_OPTION) {
					System.out.println("取消");
				} else {
					// File类表示文件或路径
					file= choose.getSelectedFile();
					txtFile.setText(file.toString());
					
					try {
						System.out.println(new String("文件名为".getBytes("UTF-8")) + ":" + file.getName());// 之所以这么麻烦是因为在run
						// as中的arguments添加了-Dfile...
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		openButton.setVerticalAlignment(SwingConstants.BOTTOM);
		openButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
		attrSettingPanel.add(openButton);
		
		nextButton = new JButton("\u4E0B\u4E00\u6B65");
		nextButton.setBounds(96, 412, 83, 25);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!new File(txtFile.getText()).exists()){
					JOptionPane.showMessageDialog(null, "文件路径不对");
				}else {
					if(fileType.equals("EXCEL文件")){
//						Runnable runnable=new Runnable() {
//							
//							@Override
//							public void run() {
//								// TODO 自动生成的方法存根
//								ImportExcel.importExcel(graphType,encodingFormat,file);
//								frame.dispose();
//							}
//						};
//						Thread importExcelThread=new Thread(runnable);
//						importExcelThread.start();
						ImportExcelFrame.setMainframe(mainFrame);
						ImportExcelFrame.importExcel(graphType,encodingFormat,file);
						frame.dispose();
					}else {
						PreviewPanel previewPanel=new PreviewPanel();
						previewPanel.setAttr(fileType, graphType, separator, encodingFormat, file);
						PreviewPanel.MyTable myTable=previewPanel.new MyTable();
						JTable jTable=new JTable(myTable);
						jTable.getTableHeader().setVisible(false);
						JScrollPane jScrollPane=new JScrollPane(jTable);
						jScrollPane.setSize(490, 390);
						previewPanel.add(jScrollPane);
						panel.remove(attrSettingPanel);
						panel.add(previewPanel);
						nextButton.setVisible(false);
						preButton.setVisible(true);
						cancelButton.setVisible(false);
						finishButton.setVisible(true);
						frame.validate();
					}
					
										
				}
			}
		});
		nextButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		frame.getContentPane().add(nextButton);
		
		preButton=new JButton("\u4e0a\u4e00\u6b65");
		preButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove(panel.getComponent(0));
				panel.add(attrSettingPanel);
				nextButton.setVisible(true);
				preButton.setVisible(false);
				cancelButton.setVisible(true);
				finishButton.setVisible(false);
				frame.repaint();
			}
		});
		preButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		preButton.setBounds(96, 412, 83, 25);
		preButton.setVisible(false);
		frame.getContentPane().add(preButton);
		
		cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.setBounds(285, 412, 83, 25);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImportFileFrame.this.getFrame().dispose();
			}
		});
		cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		frame.getContentPane().add(cancelButton);
		
		finishButton=new JButton("\u5b8c\u6210");
		finishButton.setBounds(285, 412, 83, 25);
		finishButton.setVisible(false);
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ImportCSVFileAction importFileAction=new ImportCSVFileAction();
				importFileAction.processFile(fileType, graphType, cSeparator, encodingFormat, file);				
				GraphUtils.target=(G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
				PreviewModel previewModel = previewController.getModel();
		        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
		        previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.green));
		        previewModel.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.8f));
		        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, new Font("宋体", Font.PLAIN, 16));
		        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.GREEN));
		        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
		        previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);					//透明度
				if(graphType.equals("无向图")){
					previewModel.getProperties().putValue(PreviewProperty.ARROW_SIZE, 0.0f);
				}
		        
		        if(previewSketch==null){
					previewSketch = new PreviewSketch(GraphUtils.target);
				}
		        previewController.refreshPreview();
		        mainFrame.sendGraphToDisplay(previewSketch);
			}
		});
		finishButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		frame.getContentPane().add(finishButton);
	}

}
