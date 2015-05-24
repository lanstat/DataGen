/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen;

import datagen.engine.Table;
import java.util.ArrayList;

/**
 *
 * @author juan.garson
 */
public class Database {
    private final ArrayList<Table> tables;
    private String name;
    private static Database instance;
    
    private Database(){
        tables = new ArrayList<>();
    }
    
    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        
        return instance;
    }
    
    public Table getTable(String name){
        Table response;
        try{
            response = null;
            for(Table table : tables){
                if(table.getName().equals(name)){
                    response = table;
                    break;
                }
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    public ArrayList<Table> getTables(){
        return tables;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
