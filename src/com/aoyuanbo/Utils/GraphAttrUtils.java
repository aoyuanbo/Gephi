package com.aoyuanbo.Utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;

import com.aoyuanbo.action.JFontChooser;

public class GraphAttrUtils {
	private static PreviewController previewController = GraphUtils.getPreviewController();
	private static PreviewModel previewModel = previewController.getModel();

	public static void showNodeLabel() {
		previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
	}

	public static void hideNodeLabel() {
		previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.FALSE);

	}

	public static void setNodeFont() {
		JFontChooser nodeFont=new JFontChooser();
		nodeFont.showDialog(null, 500, 200);
		previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, nodeFont.getSelectedfont());
		previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(nodeFont.getSelectedcolor()));
	}

	public static void showEdgeLabel() {				
		previewModel.getProperties().putValue(PreviewProperty.SHOW_EDGE_LABELS, Boolean.TRUE);

	}

	public static void hideEdgeLabel() {
		previewModel.getProperties().putValue(PreviewProperty.SHOW_EDGE_LABELS, Boolean.FALSE);

	}

	public static void setEdgeFont() {
		JFontChooser nodeFont=new JFontChooser();
		nodeFont.showDialog(null, 500, 200);
		previewModel.getProperties().putValue(PreviewProperty.EDGE_LABEL_FONT, nodeFont.getSelectedfont());
		previewModel.getProperties().putValue(PreviewProperty.EDGE_LABEL_COLOR, new DependantOriginalColor(nodeFont.getSelectedcolor()));
		
	}
	//设置边是为曲线
	public static void edgeIsCurved(){
		previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.TRUE);
	}
	//设置边是直线
	public static void edgeNotCurved(){
		previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
	}
	
	public static void edgeColor(){
		Color edgeColor = new JColorChooser().showDialog(null, null, Color.black);
		previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(edgeColor));
	}
	
	public static void edgeThickness(){
		JFrame jFrame=new JFrame();
		JPanel jPanel=new JPanel();
		JSlider jSlider=new JSlider();
		jSlider.setMinimum(0);
		jSlider.setMaximum(100);
		jSlider.setValue(10);
		JLabel jLabel=new JLabel("目前刻度："+jSlider.getValue());
		
		jSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if((JSlider)e.getSource()== jSlider){
					jLabel.setText("目前刻度："+jSlider.getValue());
				}
				
			}
		});
		JButton jButton=new JButton("确定");
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				float f=(float)jSlider.getValue();
				previewModel.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, f);
				jFrame.dispose();
			}
		});
		jPanel.add(jSlider);
		jPanel.add(jLabel);
		jFrame.add(jPanel);
		jPanel.add(jButton);
		jFrame.setBounds(500, 500, 400, 80);
		jFrame.setUndecorated(false);
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.show();
	}
	
	public static void backgroundColor(){
		Color edgeColor = new JColorChooser().showDialog(null, null, Color.black);
		previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, edgeColor);
	}
}
