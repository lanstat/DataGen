/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.ui;

import datagen.engine.Table;
import javax.swing.JPanel;

/**
 *
 * @author juan.garson
 */
public class DataTabbed extends JPanel{
    
    private Tab current;
    
    private Tab[] listing;
    
    /**
     * Creates new form DataTabbed
     */
    public DataTabbed() {
        init();
    }
    
    private void init(){
        listing = new Tab[3];
        listing[0] = new DatabaseTab();
        listing[1] = new TableTab();
        listing[2] = new ColumnTab();
        
        current = listing[0];
    }
    
    public void updateContent(Object data){
        current = data == null? listing[0] : data instanceof Table? listing[1] : listing[2];
        if(current != null){
            removeAll();
        }else{
            current = listing[0];
        }
        
        current.refreshContent(data);
        
        add(current);
        revalidate();
        repaint();
    }       
    
    private void saveData(){
        
    }
}

