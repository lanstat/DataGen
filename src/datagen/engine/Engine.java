/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

import datagen.Database;
import generator.Seeder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author juan.garson
 */
public abstract class Engine {
    protected HashMap<String, DataType> dataTypes;
    protected HashMap<String, String> queries;
    
    public void populate(){
        ArrayList<Table> tables;
        Seeder seeder;
        String query;
        try{
            tables = sort();
            seeder = new Seeder();
            
            for(Table table: tables){
                if(table.getCount()>0){
                    for(Column column : table.getColumns()){
                        if(!column.foreignKey){
                            column.gen = seeder.generate(null, table.getCount(), column.type, column.getOptions());
                        }else{
                            column.gen = column.referenceColumn.gen;
                        }
                    }
                    query = queries.get(table.getName());
                    for(int i=0;i<table.getCount();i++){
                        System.out.println(String.format(query, table.getRow(i)));
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private ArrayList<Table> sort(){
        ArrayList<Table> tables;
        ArrayList<Table> response;
        ArrayList<String> names;
        try{
            tables = (ArrayList<Table>)Database.getInstance().getTables().clone();
            response = new ArrayList<>();
            names = new ArrayList<>();
            
            while(!tables.isEmpty()){
                for(Table t : tables){
                    if(!t.hasReferences()){
                        response.add(t);
                        names.add(t.getName());
                    }else{
                        if(names.containsAll(t.getReferences())){
                            response.add(t);
                            names.add(t.getName());
                        }
                    }
                }
                tables.removeAll(response);
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
        
    public void connect(String database, String url, String port, String user, String password) throws ClassNotFoundException, SQLException{
        Database.getInstance().setName(database);
    }
    
    public abstract void obtainTables();
    public abstract void insert();
    public abstract void obtainInfomationFromTable();
    public abstract void close();
    public abstract void createQueries();
}
