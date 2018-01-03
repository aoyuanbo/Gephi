package com.aoyuanbo.myLayout;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.layout.plugin.AbstractLayout;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;


public class AttrGatherLayout extends AbstractLayout implements Layout {

    private static final float SPEED_DIVISOR = 800;
    private static final float AREA_MULTIPLICATOR = 10000;
    //Graph
    protected Graph graph;
    //Properties
    private float area=10000;
    private double speed=1;

    public AttrGatherLayout(LayoutBuilder layoutBuilder) {
        super(layoutBuilder);
    }

    @Override
    public void resetPropertiesValues() {
        speed = 1;
        area = 10000;
    }

    @Override
    public void initAlgo() {
    }

    @Override
    public void goAlgo() {
        this.graph = graphModel.getGraphVisible();
        graph.readLock();
        try {
            Node[] nodes = graph.getNodes().toArray();
            Edge[] edges = graph.getEdges().toArray();

            for (Node n : nodes) {
                if (n.getLayoutData() == null || !(n.getLayoutData() instanceof AttrGatherLayoutData)) {
                    n.setLayoutData(new AttrGatherLayoutData());
                }
                AttrGatherLayoutData layoutData = n.getLayoutData();
                layoutData.dx = 0;
                layoutData.dy = 0;
            }

            float maxDisplace = (float) (Math.sqrt(AREA_MULTIPLICATOR * area) / 10f);                   
            float k = (float) Math.sqrt((AREA_MULTIPLICATOR * area) / (1f + nodes.length));        

            for (Node N1 : nodes) {
                for (Node N2 : nodes) {    
                    if (N1 != N2) {
                        float xDist = N1.x() - N2.x();    
                        float yDist = N1.y() - N2.y();
                        float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);    

                        if (dist > 0) {
                            float repulsiveF = k * k / dist;           
                            AttrGatherLayoutData layoutData = N1.getLayoutData();
                            layoutData.dx += xDist / dist * repulsiveF*0.01;      
                            layoutData.dy += yDist / dist * repulsiveF*0.01;
                        }
                    }
                }
            }
            for (Edge E : edges) {
                

                Node Nf = E.getSource();
                Node Nt = E.getTarget();

                float xDist = Nf.x() - Nt.x();
                float yDist = Nf.y() - Nt.y();
                float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);

                float attractiveF = dist * dist / k;

                if (dist > 0) {
                    AttrGatherLayoutData sourceLayoutData = Nf.getLayoutData();
                    AttrGatherLayoutData targetLayoutData = Nt.getLayoutData();
                    sourceLayoutData.dx -= xDist / dist * attractiveF;
                    sourceLayoutData.dy -= yDist / dist * attractiveF;
                    targetLayoutData.dx += xDist / dist * attractiveF;
                    targetLayoutData.dy += yDist / dist * attractiveF;
                }
            }
            for(Node n1 : nodes){
            	for(Node n2 : nodes){
            		if(n1!=n2){
            			if(n1.getAttribute("Type").equals(n2.getAttribute("Type"))){
                          float xDist = n1.x() - n2.x();
                          float yDist = n1.y() - n2.y();
                          float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
          
                          float attractiveF = dist * dist / k;
          
                          if (dist > 0) {
                              AttrGatherLayoutData sourceLayoutData = n1.getLayoutData();
                              AttrGatherLayoutData targetLayoutData = n2.getLayoutData();
                              sourceLayoutData.dx -= xDist / dist * attractiveF;
                              sourceLayoutData.dy -= yDist / dist * attractiveF;
                              targetLayoutData.dx += xDist / dist * attractiveF;
                              targetLayoutData.dy += yDist / dist * attractiveF;
                          }         				
            			}
            		}
            	}           	
            }
            for (Node n : nodes) {
                AttrGatherLayoutData layoutData = n.getLayoutData();
                layoutData.dx *= speed / SPEED_DIVISOR;
                layoutData.dy *= speed / SPEED_DIVISOR;
            }
            for (Node n : nodes) {
                AttrGatherLayoutData layoutData = n.getLayoutData();
                float xDist = layoutData.dx;
                float yDist = layoutData.dy;
                float dist = (float) Math.sqrt(layoutData.dx * layoutData.dx + layoutData.dy * layoutData.dy);
                if (dist > 0 && !n.isFixed()) {
                    float limitedDist = Math.min(maxDisplace * ((float) speed / SPEED_DIVISOR), dist);
                    n.setX((float) ((n.x() + xDist / dist * limitedDist)));
                    n.setY((float) ((n.y() + yDist / dist * limitedDist)));
                }
            }
        } finally {
            graph.readUnlockAll();
        }
    }

    @Override
    public void endAlgo() {
        graph.readLock();
        try {
            for (Node n : graph.getNodes()) {
                n.setLayoutData(null);
            }
        } finally {
            graph.readUnlockAll();
        }
    }

    @Override
    public boolean canAlgo() {
        return true;
    }

    @Override
    public LayoutProperty[] getProperties() {return null;}

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Double getSpeed() {
        return speed;
    }


    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
