package com.aoyuanbo.Neo4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.plugin.ExporterGML;
import org.openide.util.Lookup;

import com.aoyuanbo.Utils.GraphUtils;



public class ExportToGML {
    
	private ExportController ec = Lookup.getDefault().lookup(ExportController.class);

	private ExporterGML exporterGML;
	private FileWriter fileWriter;
	private File file;
	private boolean isSucess=false;
	public boolean isSucess() {
		return isSucess;
	}

	public File getFile() {
		return file;
	}

	public boolean isExistGraph(){
		if(GraphUtils.getPreviewModel()==null)
			return false;
		else {
			return true;
		}
	}
	
	synchronized public void exportGML(){
		
		if(isExistGraph()){
			exporterGML=new ExporterGML();
			exporterGML.setExportColor(false);
			exporterGML.setExportCoordinates(false);
			exporterGML.setExportEdgeSize(false);
			exporterGML.setExportNodeSize(false);
			exporterGML.setExportLabel(true);
			exporterGML.setExportNotRecognizedElements(true);
			exporterGML.setWorkspace(GraphUtils.getWorkspace());
			
			file=new File("src/temp.gml");
			try {
//				fileWriter=new FileWriter(file);
				ec.exportFile(file, exporterGML);
				isSucess=true;
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
//			exporterGML.setWriter(fileWriter);
			
		}else {
			JOptionPane.showMessageDialog(null, "图谱不存在");
		}
					
	}
	
}
