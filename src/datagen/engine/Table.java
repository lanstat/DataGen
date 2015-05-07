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
    
    public Table(String name){
        this.name = name;
        columns = new ArrayList<>();
    }
    
    public void addColumn(String field, String typeName, DataType type, int length, boolean primaryKey, boolean nullable){
        columns.add(new Column(field, typeName, type, length, primaryKey, nullable));
    }
    
    public void addColumn(Column column){
        columns.add(column);
    }
    
    public String getName(){
        return name;
    }

    @Override
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
    }
}
