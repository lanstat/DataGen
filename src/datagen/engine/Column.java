/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

import java.util.HashMap;


/**
 *
 * @author juan.garson
 */
public class Column{
    public String field;
    public String typeName;
    public DataType type;
    public int length;
    public boolean primaryKey;
    public boolean nullable;
    public String mask;
    public boolean autoIncrement;
    public boolean foreignKey;
    public Column referenceColumn;
    public String[] gen;
    private HashMap<String, Object> options;
    public String columnName;
    
    public final static String AUTOINCREMENT = "autoincrement";
    public final static String PIVOT = "pivot";
    public final static String MODE = "mode";
    public final static String REF_ORIGIN = "refOrigin";
    public final static String REF_DEST = "refDest";

    public Column(){
        this.field = "";
        this.typeName = "";
        this.type = DataType.None;
        this.length = 0;
        this.primaryKey = false;
        this.nullable = false;
        this.autoIncrement = false;
        this.foreignKey = false;
        referenceColumn = null;
        options = new HashMap<>();
    }
    
    public HashMap<String, Object> getOptions(){
        try{
            if(!options.containsKey(AUTOINCREMENT))
                options.put(AUTOINCREMENT, autoIncrement);
        }catch(Exception e){
            System.err.println("Column.getOptions(): "+e.getLocalizedMessage());
        }
        return options;
    }

    public Column(String field, String typeName, DataType type, int length, boolean primaryKey, boolean nullable) {
        this.field = field;
        this.typeName = typeName;
        this.type = type;
        this.length = length;
        this.primaryKey = primaryKey;
        this.nullable = nullable;
        this.autoIncrement = false;
        this.foreignKey = false;
    }

    /*@Override
    public String toString() {
        return "Column{" + "field=" + field + ", typeName=" + typeName + ", type=" + type + ", length=" + length + ", primaryKey=" + primaryKey + ", nullable=" + nullable + '}';
    }*/
    @Override
    public String toString(){
        return field;
    }
}
