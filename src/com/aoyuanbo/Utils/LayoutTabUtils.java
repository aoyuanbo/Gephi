package com.aoyuanbo.Utils;

import org.gephi.graph.api.GraphModel;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.layout.plugin.fruchterman.FruchtermanReingold;
import org.gephi.layout.plugin.random.RandomLayout;
import org.gephi.layout.spi.Layout;
import org.gephi.preview.api.PreviewController;

import com.aoyuanbo.myLayout.AttrGatherLayout;

public class LayoutTabUtils {
	public  GraphModel getGraphModel() {
		return graphModel;
	}

	private static  GraphModel graphModel=null;
	private static PreviewController previewController=null;
	private static Layout myLayout;
	public static boolean isRun=false;
	
	public static void setMyLayout(Layout myLayout) {
		LayoutTabUtils.myLayout = myLayout;
	}

	public static Layout getMyLayout() {
		return myLayout;
	}

	public static void forceLayoutAction()  {
		previewController=GraphUtils.getPreviewController();
		graphModel=GraphUtils.getGraphModel();
		if(myLayout==null){
			myLayout=new ForceAtlasLayout(null);
		}
		myLayout.setGraphModel(graphModel);
		 ((ForceAtlasLayout) myLayout).setAdjustSizes(true);
		 ((ForceAtlasLayout) myLayout).setAttractionStrength(10.);		//吸引力强度		 
		 ((ForceAtlasLayout) myLayout).setInertia(0.1);					//惯性
		 ((ForceAtlasLayout) myLayout).setRepulsionStrength(200.0); 	//斥力速度
		 ((ForceAtlasLayout) myLayout).setMaxDisplacement(10.0);		//最大位移量
		 ((ForceAtlasLayout) myLayout).setAdjustSizes(true);			//由尺寸调整
		 ((ForceAtlasLayout) myLayout).setFreezeBalance(true); 			//设置自动稳定
		 ((ForceAtlasLayout) myLayout).setFreezeStrength(80.0);			//设置自动稳定的强度
		 ((ForceAtlasLayout) myLayout).setFreezeInertia(0.2); 			//设置自动稳定的敏感度
		 ((ForceAtlasLayout) myLayout).setGravity(30.0);
		 ((ForceAtlasLayout) myLayout).setSpeed(10.0);

		 

	}
	
	public static Thread whichLayoutAction(Layout layout){
		 if(layout instanceof ForceAtlasLayout){
			LayoutTabUtils.forceLayoutAction();
			ForceLayoutAction f=new ForceLayoutAction();
			return f;
		}else if(layout instanceof FruchtermanReingold) {
			LayoutTabUtils.fruchtermanLayoutAction();
			FruchtermanLayoutAction f=new FruchtermanLayoutAction();
			return f;
		}else if(layout instanceof RandomLayout){
			LayoutTabUtils.randomLayoutAction();
			RandomLayoutAction r=new RandomLayoutAction();
			return r;
		}else if(layout instanceof YifanHuLayout){
			LayoutTabUtils.yiFanhuLayoutAction();
			YiFanhuLayoutAction y=new YiFanhuLayoutAction();
			return y;
		}else {
			LayoutTabUtils.attrGatherLayoutAction();
			AttrGatherLayoutAction a=new AttrGatherLayoutAction();
			return a;
		}
		
	}
	
//	public static void circleLayouAction(){
//		CircleLayouAction c=new CircleLayouAction();
//		c.start();
//	}
	
	public static void randomLayoutAction() {
		previewController=GraphUtils.getPreviewController();
		graphModel=GraphUtils.getGraphModel();
		if(myLayout==null){
			myLayout=new RandomLayout(null,1000);//第二个参数是空间大小
		}
		myLayout.setGraphModel(graphModel);
	}
	
//	public static void gridLayoutAction() {
//		GridLayoutAction g=new GridLayoutAction();
//		g.start();
//	}
	
	public static void fruchtermanLayoutAction() {
		previewController=GraphUtils.getPreviewController();
		graphModel=GraphUtils.getGraphModel();
		if(myLayout==null){
			myLayout=new FruchtermanReingold(null);
		}
		myLayout.setGraphModel(graphModel);
		 ((FruchtermanReingold) myLayout).setArea(1000.f);
		 ((FruchtermanReingold) myLayout).setGravity(10.0);
		 ((FruchtermanReingold) myLayout).setSpeed(500.0);
	}
	
	public static void yiFanhuLayoutAction() {
		previewController=GraphUtils.getPreviewController();
		graphModel=GraphUtils.getGraphModel();
		if(myLayout==null){
			myLayout=new YifanHuLayout(null,new StepDisplacement(20.0f)); 		//第二个参数设置初试步长
		}
		myLayout.setGraphModel(graphModel);
		((YifanHuLayout) myLayout).setInitialStep(20.0f); 						//设置初试步长
		((YifanHuLayout) myLayout).setQuadTreeMaxLevel(10);						//四叉树最高等级
		((YifanHuLayout) myLayout).setConvergenceThreshold(0.0001f);;			//收敛阈值
		((YifanHuLayout) myLayout).setOptimalDistance(100.0f);;					//最佳距离
		((YifanHuLayout) myLayout).setStepRatio(0.95f);;						//步比率
		((YifanHuLayout) myLayout).setRelativeStrength(0.2f);					//相应强度
		((YifanHuLayout) myLayout).setAdaptiveCooling(true);					//自适应冷却
		((YifanHuLayout) myLayout).setBarnesHutTheta(1.2f);						//西塔
		
	}
	
	public static void attrGatherLayoutAction(){
		previewController=GraphUtils.getPreviewController();
		graphModel=GraphUtils.getGraphModel();
		if(myLayout==null){
			myLayout=new AttrGatherLayout(null); 		//第二个参数设置初试步长
		}
		myLayout.setGraphModel(graphModel);
		((AttrGatherLayout) myLayout).setArea((float) 10000);
		((AttrGatherLayout) myLayout).setSpeed((double) 1);
		
	}
	
	
	private static class ForceLayoutAction extends Thread{
		 public void run(){
			 myLayout.initAlgo();
			 System.out.println("初始化完毕");
				
//				for (int i = 0; i < 2000 && myLayout.canAlgo(); i++) {
//					myLayout.goAlgo();
//					previewController.refreshPreview();
//				}
//				if(myLayout.canAlgo() && isRun){
//					for (int i = 0; i < 200; i++) {
//						myLayout.goAlgo();
//						previewController.refreshPreview();
//					}					
//				}
				while (myLayout.canAlgo() &&isRun) {
					myLayout.goAlgo();
		            previewController.refreshPreview();			
				}
				myLayout.endAlgo();
				System.out.println(GraphUtils.getGraphModel());
				System.out.println("end");
		 }
	}

		
	
	private static class RandomLayoutAction extends Thread{
		public void run(){
			myLayout.initAlgo();
//			for (int i = 0; i < 20000 && isRun; i++) {
//	            myLayout.goAlgo();
//	            previewController.refreshPreview();
//	        }
			while (myLayout.canAlgo() &&isRun) {
				myLayout.goAlgo();
	            previewController.refreshPreview();			
			}
			myLayout.endAlgo();
			System.out.println("end");
		}
	}
	
	
	private static class FruchtermanLayoutAction extends Thread{
		public void run(){
			myLayout.initAlgo();
//			for (int i = 0; i < 20000 && isRun; i++) {
//	            myLayout.goAlgo();
//	            previewController.refreshPreview();
//	        }
			while (myLayout.canAlgo() &&isRun) {
				myLayout.goAlgo();
	            previewController.refreshPreview();			
			}
			myLayout.endAlgo();
			System.out.println("end");
		}
	}
	
	
	private static class YiFanhuLayoutAction extends Thread{
		public void run(){
			myLayout.initAlgo();
//			for (int i = 0; i < 20000 && isRun; i++) {
//	            myLayout.goAlgo();
//	            previewController.refreshPreview();
//	        }
			while (myLayout.canAlgo() &&isRun) {
				myLayout.goAlgo();
	            previewController.refreshPreview();			
			}
			myLayout.endAlgo();
			System.out.println("end");
		}
	}
	
	private static class AttrGatherLayoutAction extends Thread{
		public void run(){
			myLayout.initAlgo();
//			for (int i = 0; i < 20000 && isRun; i++) {
//	            myLayout.goAlgo();
//	            previewController.refreshPreview();
//	        }
			while (myLayout.canAlgo() &&isRun) {
				myLayout.goAlgo();
	            previewController.refreshPreview();			
			}
			myLayout.endAlgo();
			System.out.println("end");
		}
	}
}
