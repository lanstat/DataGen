/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.ui;

import datagen.Database;
import datagen.engine.Column;
import datagen.engine.Table;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author juan.garson
 */
public class DatabaseExplorer implements TreeModel{
    private final Database database;
    
    public DatabaseExplorer(){
        database = Database.getInstance();
    }

    @Override
    public Object getRoot() {
        return database.getName();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Object child;
        try{
            if(parent instanceof Table){
                child = ((Table)parent).getColumns().get(index);
            }else{
                child = database.getTables().get(index);
            }
        }catch(Exception e){
            child = null;
        }
        return child;
    }

    @Override
    public int getChildCount(Object parent) {
        int size;
        try{
            if(parent instanceof Table){
                size = ((Table)parent).getColumns().size();
            }else{
                size = database.getTables().size();
            }
        }catch(Exception e){
            size = 0;
        }
        return size;
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof Column? true : false;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int index = 0;
        for(Column column : ((Table)parent).getColumns()){
            if(column == child) 
                break;
            index++;
        }
        return index;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }
}
