/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

import java.util.ArrayList;

/**
 *
 * @author juan.garson
 */
public class Table {
    private final ArrayList<Column> columns;
    private final String name;
    private int count;
    private final ArrayList<String> reference;
    
    public Table(String name){
        this.name = name;
        columns = new ArrayList<>();
        reference = new ArrayList<>();
    }
    
    public Column getColumn(String name){
        Column response;
        try{
            response = null;
            for(Column column: columns){
                if(column.field.equals(name)){
                    response = column;
                    break;
                }
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    public String[] getRow(int position){
        String[] response;
        try{
            response = new String[columns.size()];
            for(int i=0;i<columns.size();i++){
                response[i] = columns.get(i).gen[position];
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            response = null;
        }
        return response;
    }
    
    public boolean hasReferences(){
        return reference.size() > 0;
    }
    
    public ArrayList<String> getReferences(){
        return reference;
    }
    
    public void addColumn(Column column){
        columns.add(column);
    }
    
    public String getName(){
        return name;
    }
    
    public void setCount(int count){
        this.count = count;
    }
    
    public int getCount(){
        return count;
    }
    
    public ArrayList<Column> getColumns(){
        return columns;
    }

    /*@Override
    public String toString() {
        StringBuilder builder;
        builder = new StringBuilder();
        builder.append("Table : ");
        builder.append(name);
        builder.append("\n");
        for(Column col : columns){
            builder.append(col.toString());
            builder.append("\n");
        }
        return builder.toString();
    }*/
    
    @Override
    public String toString() {
        return name;
    }
}
