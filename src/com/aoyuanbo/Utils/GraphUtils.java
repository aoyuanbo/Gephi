package com.aoyuanbo.Utils;

import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.layout.api.LayoutController;
import org.gephi.preview.api.G2DTarget;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

public class GraphUtils {
	
	private static ProjectController pc;
	private static Workspace workspace;
	private static GraphModel graphModel;
	private static PreviewController previewController;
	private static PreviewModel previewModel;
	private static AttributeColumnsController attributeColumnsController;
	private static LayoutController layoutController;
	public static G2DTarget target;
	
	public static ProjectController getProjectController(){
		if(pc==null){
			pc=Lookup.getDefault().lookup(ProjectController.class);
		}
		return pc;
	}
	
	public static Workspace getWorkspace(){
		if(workspace==null){
			workspace=pc.getCurrentWorkspace();
		}
		return workspace;
	}
	
	public static GraphModel getGraphModel(){
		if(graphModel==null || workspace==null){
			workspace=pc.getCurrentWorkspace();
			graphModel=Lookup.getDefault().lookup(GraphController.class).getGraphModel(workspace);
		}
		return graphModel;
	}
	
	public static PreviewController getPreviewController(){
		if(previewController==null){
			previewController=Lookup.getDefault().lookup(PreviewController.class);
		}
		return previewController;
	}
	
	public static PreviewModel getPreviewModel(){
		if(previewModel==null){
			previewModel=Lookup.getDefault().lookup(PreviewController.class).getModel();
		}
		return previewModel;
	}
	
	public static AttributeColumnsController getAttributeColumnsController(){
		if(attributeColumnsController==null){
			attributeColumnsController=Lookup.getDefault().lookup(AttributeColumnsController.class);
		}
		return attributeColumnsController;
	}
	
	public static  LayoutController getLayoutController() {
		if(layoutController==null){
			layoutController=Lookup.getDefault().lookup(LayoutController.class);
		}
		return layoutController;
	}

}
