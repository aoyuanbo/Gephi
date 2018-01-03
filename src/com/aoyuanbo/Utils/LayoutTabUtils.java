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
		 ((ForceAtlasLayout) myLayout).setAttractionStrength(10.);		//������ǿ��		 
		 ((ForceAtlasLayout) myLayout).setInertia(0.1);					//����
		 ((ForceAtlasLayout) myLayout).setRepulsionStrength(200.0); 	//�����ٶ�
		 ((ForceAtlasLayout) myLayout).setMaxDisplacement(10.0);		//���λ����
		 ((ForceAtlasLayout) myLayout).setAdjustSizes(true);			//�ɳߴ����
		 ((ForceAtlasLayout) myLayout).setFreezeBalance(true); 			//�����Զ��ȶ�
		 ((ForceAtlasLayout) myLayout).setFreezeStrength(80.0);			//�����Զ��ȶ���ǿ��
		 ((ForceAtlasLayout) myLayout).setFreezeInertia(0.2); 			//�����Զ��ȶ������ж�
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
			myLayout=new RandomLayout(null,1000);//�ڶ��������ǿռ��С
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
			myLayout=new YifanHuLayout(null,new StepDisplacement(20.0f)); 		//�ڶ����������ó��Բ���
		}
		myLayout.setGraphModel(graphModel);
		((YifanHuLayout) myLayout).setInitialStep(20.0f); 						//���ó��Բ���
		((YifanHuLayout) myLayout).setQuadTreeMaxLevel(10);						//�Ĳ�����ߵȼ�
		((YifanHuLayout) myLayout).setConvergenceThreshold(0.0001f);;			//������ֵ
		((YifanHuLayout) myLayout).setOptimalDistance(100.0f);;					//��Ѿ���
		((YifanHuLayout) myLayout).setStepRatio(0.95f);;						//������
		((YifanHuLayout) myLayout).setRelativeStrength(0.2f);					//��Ӧǿ��
		((YifanHuLayout) myLayout).setAdaptiveCooling(true);					//����Ӧ��ȴ
		((YifanHuLayout) myLayout).setBarnesHutTheta(1.2f);						//����
		
	}
	
	public static void attrGatherLayoutAction(){
		previewController=GraphUtils.getPreviewController();
		graphModel=GraphUtils.getGraphModel();
		if(myLayout==null){
			myLayout=new AttrGatherLayout(null); 		//�ڶ����������ó��Բ���
		}
		myLayout.setGraphModel(graphModel);
		((AttrGatherLayout) myLayout).setArea((float) 10000);
		((AttrGatherLayout) myLayout).setSpeed((double) 1);
		
	}
	
	
	private static class ForceLayoutAction extends Thread{
		 public void run(){
			 myLayout.initAlgo();
			 System.out.println("��ʼ�����");
				
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
