package com.aoyuanbo.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.graph.api.GraphModel;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.layout.plugin.fruchterman.FruchtermanReingold;
import org.gephi.layout.plugin.random.RandomLayout;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.neo4j.driver.v1.exceptions.ClientException;

import com.aoyuanbo.Utils.DBUtils;
import com.aoyuanbo.Utils.GraphAttrUtils;
import com.aoyuanbo.Utils.GraphUtils;
import com.aoyuanbo.Utils.LayoutTabUtils;
import com.aoyuanbo.action.OpenGraphAction;
import com.aoyuanbo.action.PreviewSketch;
import com.aoyuanbo.myLayout.AttrGatherLayout;

import Neo4j.ExportToGML;
import Neo4j.ExtractGml;
import Neo4j.SaveToNeo4j;

public class Frame {

	public JPanel getDisplay() {
		return display;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	//定义界面上的一些控件
	private static Frame window = null;
	private JFrame frame;
	private final JPanel infopanel = new JPanel();
	private final JPanel display = new JPanel();
	private final JMenu File = new JMenu();
	private final JMenuItem openFileItem = new JMenuItem();
	private final JMenuItem condbItem = new JMenuItem();
	private final JMenuItem saveToNeo4jItem = new JMenuItem();
	private final JMenuItem quitItem = new JMenuItem();
	private final JMenu Layout = new JMenu();
	private final JMenuItem forceLayoutItem = new JMenuItem();
	private final JMenuItem yiFanhuLayoutItem = new JMenuItem();
	private final JMenuItem randomLayoutItem = new JMenuItem();
	private final JMenuItem MyLayoutItem = new JMenuItem();
	private final JMenuItem fruchtermanLayoutItem = new JMenuItem();
	private final JMenu LabelSet = new JMenu();
	private final JMenu nodeLabelMenu=new JMenu();
	private final JMenuItem showNodeTextItem=new JMenuItem();
	private final JMenuItem hideNodeTextItem=new JMenuItem();
	private final JMenuItem nodeTextFontItem=new JMenuItem();
	private final JMenu edgeLabelMenu=new JMenu();
	private final JMenuItem showEdgeTextItem=new JMenuItem();
	private final JMenuItem hideEdgeTextItem=new JMenuItem();
	private final JMenuItem edgeTextFontItem=new JMenuItem();
	private final JMenu NodeSet = new JMenu();
	private final JMenuItem rectangleItem = new JMenuItem();
	private final JMenuItem roundItem = new JMenuItem();
	private final JMenuItem roundRectangleItem = new JMenuItem();
	private final JMenu EdgeSet = new JMenu();
	private final JMenuItem curveItem = new JMenuItem();
	private final JMenuItem straightItem = new JMenuItem();
	private final JMenuItem thicknessItem = new JMenuItem();
	private final JMenuItem edgeColorItem = new JMenuItem();
	private final JMenu ActionSet = new JMenu();
	private final JMenu Control = new JMenu();
	private final JMenuItem zoomToFitItem = new JMenuItem();
	private final JMenu help = new JMenu();
	private final JMenuItem menuItem_20 = new JMenuItem();
	private final JMenuItem importFileItem = new JMenuItem("\u5BFC\u5165");
	private static JButton runButton;
	private static JButton backgroundColorButton;
	private static boolean itemIsEnable = false;

	//定义与图相关的一些变量
	private static ProjectController pc;
	private static Workspace workspace;
	private static GraphModel graphModel;
	private static PreviewController previewController;
	private static PreviewModel previewModel;
	private static AttributeColumnsController attributeColumnsController;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new Frame();
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
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ProjectController pc = GraphUtils.getProjectController();
		pc.newProject();
		Workspace workspace = GraphUtils.getWorkspace();

		frame = new JFrame("主界面");
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				backgroundColorButton.setLocation(30, frame.getHeight()-100);
				runButton.setLocation(30, frame.getHeight()-150);
			}
		});
		frame.setBounds(300, 100, 550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		File.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		File.setMnemonic('F');
		File.setText("\u6587\u4EF6(F)");

		menuBar.add(File);
		openFileItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		openFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openItemActionPerformed(e);
			}
		});
		openFileItem.setText("\u6253\u5F00");

		File.add(openFileItem);
		importFileItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		importFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importFileActionPerformed(e);
			}

		});

		File.add(importFileItem);
		condbItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		condbItem.setText("\u8FDE\u63A5\u6570\u636E\u5E93...");
		condbItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (DBUtils.getInstance().getDriver()==null) {
					DBConnFrame dbConnFrame = new DBConnFrame();
				}else {
					JOptionPane.showMessageDialog(null, "已经连接到数据库");
				}
	
			}
		});
		
		
		File.add(condbItem);
		
		JMenuItem disconnDBItem = new JMenuItem("\u65AD\u5F00\u6570\u636E\u5E93\r\n");
		disconnDBItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		disconnDBItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(DBUtils.isSuccess()){
						DBUtils.getInstance().getSession().close();
						DBUtils.getInstance().getDriver().close();
						DBUtils.setSuccess(false);
						JOptionPane.showMessageDialog(null, "成功关闭数据库连接");
					}else {
						JOptionPane.showMessageDialog(null, "数据库没有连接");
					}
				} catch (ClientException e) {
					// TODO 自动生成的 catch 块
					JOptionPane.showMessageDialog(null, "数据库已经关闭");
				}
			}
		});
		File.add(disconnDBItem);
		saveToNeo4jItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(DBUtils.isSuccess() && GraphUtils.getPreviewModel()!=null){
					ExportToGML exportToGML=new ExportToGML();
					ExtractGml extractGml=new ExtractGml();
					SaveToNeo4j saveToNeo4j=new SaveToNeo4j();
					exportToGML.exportGML();
					File file=exportToGML.getFile();
					String result=null;
					if(exportToGML.isSucess()){					
						result=extractGml.readGML(file.toString());
						System.out.println(result);
						if (DBUtils.isSuccess()) {
							saveToNeo4j.saveNode(extractGml.getNode(result));
							saveToNeo4j.saveEdge(extractGml.getEdge(result));
						JOptionPane.showMessageDialog(null, "完成存储");
						}else {
							JOptionPane.showMessageDialog(null, "数据库未连接");							
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "出错，请稍后再试");
					}
				}
			}
		});
		saveToNeo4jItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		saveToNeo4jItem.setText("\u5B58\u50A8\u5230Neo4j");

		File.add(saveToNeo4jItem);
		
		JMenu mnNewMenu = new JMenu("\u5BFC\u51FA");
		File.add(mnNewMenu);
		quitItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		quitItem.setText("\u9000\u51FA");

		File.add(quitItem);
		Layout.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		Layout.setMnemonic('L');
		Layout.setText("\u5E03\u5C40\u7B97\u6CD5(L)");

		menuBar.add(Layout);
		forceLayoutItem.setEnabled(false);
		forceLayoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forceLayoutActionPerformed(e);
				
			}
		});
		forceLayoutItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		forceLayoutItem.setText("\u529B\u5BFC\u5411\u5E03\u5C40");

		Layout.add(forceLayoutItem);
		yiFanhuLayoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				yiFanhuLayoutActionPerformed(e);
			}

			
		});
		yiFanhuLayoutItem.setEnabled(false);
		yiFanhuLayoutItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		yiFanhuLayoutItem.setText("YiFanhu\u5E03\u5C40");

		Layout.add(yiFanhuLayoutItem);
		randomLayoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randomLayoutActionPerformed(e);
			}
		});
		randomLayoutItem.setEnabled(false);
		randomLayoutItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		randomLayoutItem.setText("\u968F\u673A\u5E03\u5C40");

		Layout.add(randomLayoutItem);
		MyLayoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayoutActionPerformed(e);
			}
		});
		MyLayoutItem.setEnabled(false);
		MyLayoutItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		MyLayoutItem.setText("\u5C5E\u6027\u805A\u96C6\u5E03\u5C40");

		Layout.add(MyLayoutItem);
		fruchtermanLayoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fruchtermanLayoutActionPerformed(e);
			}
		});
		fruchtermanLayoutItem.setEnabled(false);
		fruchtermanLayoutItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		fruchtermanLayoutItem.setText("FruchtermanReingold\u5E03\u5C40");

		Layout.add(fruchtermanLayoutItem);
		LabelSet.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		LabelSet.setMnemonic('T');
		LabelSet.setText("\u6807\u7B7E\u914D\u7F6E(T)");

		menuBar.add(LabelSet);
		nodeLabelMenu.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		nodeLabelMenu.setEnabled(false);
		
		nodeLabelMenu.setText("\u8282\u70B9\u6807\u7B7E");
		LabelSet.add(nodeLabelMenu);
		showNodeTextItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		
		showNodeTextItem.setText("\u663E\u793A\u6587\u672C");
		showNodeTextItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GraphAttrUtils.showNodeLabel();
			}
		});
		nodeLabelMenu.add(showNodeTextItem);
		hideNodeTextItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		hideNodeTextItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GraphAttrUtils.hideNodeLabel();
			}
		});
		
		hideNodeTextItem.setText("\u4E0D\u663E\u793A\u6587\u672C");
		nodeLabelMenu.add(hideNodeTextItem);
		nodeTextFontItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		nodeTextFontItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.setNodeFont();
			}
		});
		
		nodeTextFontItem.setText("\u6587\u672C\u5B57\u4F53");
		nodeLabelMenu.add(nodeTextFontItem);
		edgeLabelMenu.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		edgeLabelMenu.setEnabled(false);
		
		edgeLabelMenu.setText("\u8FB9\u6807\u7B7E");
		LabelSet.add(edgeLabelMenu);
		showEdgeTextItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		showEdgeTextItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.showEdgeLabel();
			}
		});
		
		showEdgeTextItem.setText("\u663E\u793A\u6587\u672C");
		edgeLabelMenu.add(showEdgeTextItem);
		hideEdgeTextItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		hideEdgeTextItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.hideEdgeLabel();
			}
		});
		
		hideEdgeTextItem.setText("\u4E0D\u663E\u793A\u6587\u672C");
		edgeLabelMenu.add(hideEdgeTextItem);
		edgeTextFontItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		edgeTextFontItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.setEdgeFont();
			}
		});
		
		edgeTextFontItem.setText("\u6587\u672C\u5B57\u4F53");
		edgeLabelMenu.add(edgeTextFontItem);
		NodeSet.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		NodeSet.setMnemonic('N');
		NodeSet.setText("\u8282\u70B9\u914D\u7F6E(N)");

		menuBar.add(NodeSet);
		rectangleItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		rectangleItem.setText("\u77E9\u5F62");
		rectangleItem.setEnabled(false);

		NodeSet.add(rectangleItem);
		roundItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		roundItem.setText("\u5706\u5F62");
		roundItem.setEnabled(false);

		NodeSet.add(roundItem);
		roundRectangleItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		roundRectangleItem.setText("\u5706\u89D2\u77E9\u5F62");
		roundRectangleItem.setEnabled(false);

		NodeSet.add(roundRectangleItem);
		EdgeSet.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		EdgeSet.setMnemonic('S');
		EdgeSet.setText("\u8FB9\u914D\u7F6E(S)");

		menuBar.add(EdgeSet);
		curveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.edgeIsCurved();
			}
		});
		curveItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		curveItem.setText("\u66F2\u7EBF\u8FB9");
		curveItem.setEnabled(false);

		EdgeSet.add(curveItem);
		straightItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.edgeNotCurved();
			}
		});
		straightItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		straightItem.setText("\u76F4\u7EBF\u8FB9");
		straightItem.setEnabled(false);

		EdgeSet.add(straightItem);
		ActionSet.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		ActionSet.setMnemonic('A');
		ActionSet.setText("Action\u914D\u7F6E(A)");
		thicknessItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.edgeThickness();
			}
		});
		
		thicknessItem.setText("\u5BBD\u5EA6");
		thicknessItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		EdgeSet.add(thicknessItem);
		edgeColorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.edgeColor();
			}
		});
		
		edgeColorItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		edgeColorItem.setText("\u989C\u8272");		
		EdgeSet.add(edgeColorItem);
		
		menuBar.add(ActionSet);
		Control.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		Control.setMnemonic('C');
		Control.setText("\u63A7\u5236\u5668(C)");

		menuBar.add(Control);
		zoomToFitItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		zoomToFitItem.setText("\u9002\u5E94\u5C4F\u5E55\u663E\u793A");
		zoomToFitItem.setEnabled(false);

		Control.add(zoomToFitItem);
		help.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		help.setMnemonic('H');
		help.setText("\u5E2E\u52A9(H)");

		menuBar.add(help);
		menuItem_20.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menuItem_20.setText("\u5173\u4E8E");

		help.add(menuItem_20);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 135, 401, 0 };
		gridBagLayout.rowHeights = new int[] { 341, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_infopanel = new GridBagConstraints();
		gbc_infopanel.ipady = 10;
		gbc_infopanel.fill = GridBagConstraints.BOTH;
		gbc_infopanel.gridx = 0;
		gbc_infopanel.gridy = 0;
		gbc_infopanel.ipadx = 100;
		gbc_infopanel.ipady = 100;
		gbc_infopanel.weightx = 0;
		gbc_infopanel.weighty = 100;
		infopanel.setBackground(Color.YELLOW);
		infopanel.setLayout(null);
		frame.getContentPane().add(infopanel, gbc_infopanel);
		
		runButton = new JButton("运行");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runActionPerformed(e);
			}
		});
		runButton.setFont(new Font("新宋体", Font.PLAIN, 12));
		runButton.setBounds(30, 250, 80, 25);
		infopanel.add(runButton);
		
		backgroundColorButton = new JButton("\u80CC\u666F\u989C\r\n\u8272");
		backgroundColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphAttrUtils.backgroundColor();
			}
		});
		backgroundColorButton.setFont(new Font("新宋体", Font.PLAIN, 12));
		backgroundColorButton.setBounds(30, 300, 80, 25);
		infopanel.add(backgroundColorButton);
		
		display.setBackground(Color.BLUE);
		display.setLayout(new BorderLayout());
		GridBagConstraints gbc_display = new GridBagConstraints();
		gbc_display.fill = GridBagConstraints.BOTH;
		gbc_display.gridx = 1;
		gbc_display.gridy = 0;
		gbc_display.weightx = 100;
		gbc_display.weighty = 100;
		gbc_display.ipadx = 300;
		gbc_display.ipady = 700;
		frame.getContentPane().add(display, gbc_display);

		forceLayoutItem.setEnabled(itemIsEnable);
		yiFanhuLayoutItem.setEnabled(itemIsEnable);
		randomLayoutItem.setEnabled(itemIsEnable);
		fruchtermanLayoutItem.setEnabled(itemIsEnable);
		rectangleItem.setEnabled(itemIsEnable);
		roundItem.setEnabled(itemIsEnable);
		roundRectangleItem.setEnabled(itemIsEnable);
		curveItem.setEnabled(itemIsEnable);
		straightItem.setEnabled(itemIsEnable);
		thicknessItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		thicknessItem.setEnabled(itemIsEnable);
		edgeColorItem.setEnabled(itemIsEnable);
		zoomToFitItem.setEnabled(itemIsEnable);
		
		
		

	}

	public void showItem(boolean itemIsEnable) {

		saveToNeo4jItem.setEnabled(itemIsEnable);
		forceLayoutItem.setEnabled(itemIsEnable);
		yiFanhuLayoutItem.setEnabled(itemIsEnable);
		randomLayoutItem.setEnabled(itemIsEnable);
		MyLayoutItem.setEnabled(itemIsEnable);
		fruchtermanLayoutItem.setEnabled(itemIsEnable);
		nodeLabelMenu.setEnabled(itemIsEnable);
		edgeLabelMenu.setEnabled(itemIsEnable);
		rectangleItem.setEnabled(itemIsEnable);
		roundItem.setEnabled(itemIsEnable);
		roundRectangleItem.setEnabled(itemIsEnable);
		curveItem.setEnabled(itemIsEnable);
		straightItem.setEnabled(itemIsEnable);
		thicknessItem.setEnabled(itemIsEnable);
		edgeColorItem.setEnabled(itemIsEnable);
		zoomToFitItem.setEnabled(itemIsEnable);
		this.getFrame().repaint();
	}

	protected void forceLayoutActionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		LayoutTabUtils.setMyLayout(new ForceAtlasLayout(null));		
	}
	
	protected void yiFanhuLayoutActionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		LayoutTabUtils.setMyLayout(new YifanHuLayout(null, new StepDisplacement(20.0f)));
	}
	
	protected void randomLayoutActionPerformed(ActionEvent e){
		LayoutTabUtils.setMyLayout(new RandomLayout(null,1000));
	}
	
	protected void fruchtermanLayoutActionPerformed(ActionEvent e){
		LayoutTabUtils.setMyLayout(new FruchtermanReingold(null));
	}
	
	protected void  myLayoutActionPerformed(ActionEvent e) {
		LayoutTabUtils.setMyLayout(new AttrGatherLayout(null));
	}
	
	private void runActionPerformed(ActionEvent e) {

		System.out.println(LayoutTabUtils.getMyLayout());

		Thread thread=LayoutTabUtils.whichLayoutAction(LayoutTabUtils.getMyLayout());
		System.out.println(thread.getClass());

		if(runButton.getText().equals("运行")){
			LayoutTabUtils.isRun=true;
            thread.start();
            System.out.println("启动");
            runButton.setText("停止");
			
		}else {
			if (thread != null)
				LayoutTabUtils.isRun=false;
                thread.stop();  
//            thread = null;
            runButton.setText("运行");
		}
	
	}

	
	
	private void openItemActionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
		JFileChooser choose = new JFileChooser();
		choose.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("GEXF文件（*.gexf）", "gexf"));
		choose.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("GML文件（*.gml）", "gml"));
		int op = choose.showOpenDialog(this.getFrame());
		if (op == JFileChooser.CANCEL_OPTION) {
		} else {
			String[] args = null;
			// File类表示文件或路径
			File f = choose.getSelectedFile();
			OpenGraphAction openGraph = new OpenGraphAction(f);
			display.add(openGraph.getPreviewSketch(), BorderLayout.CENTER);
			showItem(true);
			try {
				System.out.println(new String("文件名为".getBytes("UTF-8")) + ":" + f.getName());// 之所以这么麻烦是因为在run
				// as中的arguments添加了-Dfile...
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

		}
	}

	private void importFileActionPerformed(ActionEvent e) {
		String[] args = null;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ImportFileFrame.main(args, window);
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void sendGraphToDisplay(PreviewSketch previewSketch) {

		display.add(previewSketch, BorderLayout.CENTER);
		showItem(true);
	}
}
