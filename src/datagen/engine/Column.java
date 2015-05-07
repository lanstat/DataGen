/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

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

    public Column(){}

    public Column(String field, String typeName, DataType type, int length, boolean primaryKey, boolean nullable) {
        this.field = field;
        this.typeName = typeName;
        this.type = type;
        this.length = length;
        this.primaryKey = primaryKey;
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return "Column{" + "field=" + field + ", typeName=" + typeName + ", type=" + type + ", length=" + length + ", primaryKey=" + primaryKey + ", nullable=" + nullable + '}';
    }
}
