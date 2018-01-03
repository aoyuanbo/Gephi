package com.aoyuanbo.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.gephi.preview.api.G2DTarget;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;

import com.aoyuanbo.Utils.GraphUtils;
import com.aoyuanbo.action.ImportCSVFileAction;
import com.aoyuanbo.action.PreviewSketch;

public class ShowDataFrame extends JFrame {
	
	private static Frame mainFrame;
	private static PreviewController previewController=GraphUtils.getPreviewController();
	private static PreviewSketch previewSketch;
	
	private JTable showTable;
	private JScrollPane showScrollPane;
	private JButton saveButton;
	private JButton finishButton;
	private DefaultTableModel defaultTableModel;
	private File savePath;
	
	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
		int rowCount = this.defaultTableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			if (this.defaultTableModel.getValueAt(i, 0) == null)
				this.defaultTableModel.removeRow(i);
		}
		showTable.setModel(this.defaultTableModel);
	}

	public ShowDataFrame(DefaultTableModel defaultTableModel) {
		super();
		showTable = new JTable();
		if (defaultTableModel == null) {
			showTable.setModel(this.defaultTableModel);
		} else {
			int rowCount = defaultTableModel.getRowCount();
			for (int i = rowCount - 1; i >= 0; i--) {
				if (defaultTableModel.getValueAt(i, 0) == null && defaultTableModel.getValueAt(i, 1) == null)
					defaultTableModel.removeRow(i);
			}

			showTable.setModel(defaultTableModel);
		}
		showScrollPane = new JScrollPane(showTable);
		showScrollPane.setSize(500, 500);
		
		saveButton = new JButton("保存");
		saveButton.setBounds(150, 415, 100, 30);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveActionPerformed();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});

		finishButton = new JButton("完成");
		finishButton.setBounds(450, 400, 100, 30);
		finishButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					finishActionPerformed();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
			}
		});
		
		this.setBounds(400, 100, 700, 500);
		this.setLayout(null);
		this.add(showScrollPane);
		this.add(saveButton);
		this.add(finishButton);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				showScrollPane.setSize(getWidth() - 15, getHeight() - 100);
				saveButton.setLocation(getWidth() / 6, getHeight() - 85);
				finishButton.setLocation(getWidth() * 2 / 3, getHeight() - 85);
			}

		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				windowClosed(e);
				DefaultTableModel tableModel = (DefaultTableModel) showTable.getModel();
				tableModel.setRowCount(0);
			}

		});

		this.show();
	}

	public void saveActionPerformed() throws IOException {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.FILES_ONLY);
		fc.setDialogTitle("选择文件");
		fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV文件（*.csv）", "csv"));
		fc.setMultiSelectionEnabled(false);
		int value=fc.showSaveDialog(fc);
		if (value==JFileChooser.APPROVE_OPTION) {
			savePath = fc.getSelectedFile();
			FileOutputStream fileOutputStream = new FileOutputStream(savePath);
			String string = "";
			for (int i = 0; i < showTable.getColumnCount(); i++) {
				string += showTable.getColumnName(i) + ',';
			}
			string = string.substring(0, string.length() - 1);
			string += "\r\n";
			fileOutputStream.write(string.getBytes());
			string = "";
			for (int i1 = 0; i1 < showTable.getRowCount(); i1++) {
				for (int j = 0; j < showTable.getColumnCount(); j++) {
					string += (String) showTable.getValueAt(i1, j) + ',';
				}
				string = string.substring(0, string.length() - 1);
				string += "\r\n";
				fileOutputStream.write(string.getBytes());
				string = "";
			}
			fileOutputStream.close();
			JOptionPane.showMessageDialog(null, "保存成功");
		}else {
			//点击取消，什么都不做
		}
	}
	
	public void finishActionPerformed() throws IOException{
		ShowDataFrame.this.dispose();
		mainFrame=ImportExcelFrame.getMainframe();		
		if(savePath==null){
			savePath=File.createTempFile("temp", ".csv");
			FileOutputStream fileOutputStream = new FileOutputStream(savePath);
			String string = "";
			for (int i = 0; i < showTable.getColumnCount(); i++) {
				string += showTable.getColumnName(i) + ',';
			}

			string = string.substring(0, string.length() - 1);
			string += "\r\n";
			fileOutputStream.write(string.getBytes());
			string = "";
			for (int i = 0; i < showTable.getRowCount(); i++) {
				for (int j = 0; j < showTable.getColumnCount(); j++) {
					string += (String) showTable.getValueAt(i, j) + ',';
				}
				string = string.substring(0, string.length() - 1);
				string += "\r\n";
				fileOutputStream.write(string.getBytes());
				string = "";
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			
		}
		ImportCSVFileAction importFileAction=new ImportCSVFileAction();
		if(showTable.getColumnName(0).equals("Id")){
			importFileAction.processFile("点CSV文件", ImportExcelFrame.getGraphType(), ',', ImportExcelFrame.getEncodingFormat(), savePath);
			GraphUtils.target=(G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
			PreviewModel previewModel = previewController.getModel();
	        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.green));
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.8f));
	        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, new Font("宋体", Font.PLAIN, 16));
	        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.GREEN));
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
			if(previewSketch==null){
				previewSketch = new PreviewSketch(GraphUtils.target);
			}
	        previewController.refreshPreview();
	        mainFrame.sendGraphToDisplay(previewSketch);
	        savePath.delete();
	        savePath=null;
		}else {
			importFileAction.processFile("边CSV文件", ImportExcelFrame.getGraphType(), ',', ImportExcelFrame.getEncodingFormat(), savePath);			
			GraphUtils.target=(G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
			PreviewModel previewModel = previewController.getModel();
	        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.green));
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.8f));
	        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, new Font("宋体", Font.PLAIN, 16));
	        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.GREEN));
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
	        previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
			if(previewSketch==null){
				previewSketch = new PreviewSketch(GraphUtils.target);
			}
	        previewController.refreshPreview();
	        mainFrame.sendGraphToDisplay(previewSketch);
	        savePath.delete();
	        savePath=null;
		}
	}
}
