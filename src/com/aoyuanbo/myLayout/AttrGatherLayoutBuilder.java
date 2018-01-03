package com.aoyuanbo.myLayout;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutUI;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@ServiceProvider(service=LayoutBuilder.class)
public class AttrGatherLayoutBuilder implements LayoutBuilder{
    
    private MyLayoutBuilderUI UI=new MyLayoutBuilderUI();
    
    @Override
    public String getName() {
        return NbBundle.getMessage(AttrGatherLayoutBuilder.class, "name"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LayoutUI getUI() {
        return UI; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AttrGatherLayout buildLayout() {
        return new AttrGatherLayout(this); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private class MyLayoutBuilderUI implements LayoutUI{

        @Override
        public String getDescription() {
            return NbBundle.getMessage(AttrGatherLayout.class, "description"); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Icon getIcon() {
            return null; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public JPanel getSimplePanel(Layout layout) {
            return null; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getQualityRank() {
            return 2; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getSpeedRank() {
            return 3; //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
