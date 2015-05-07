/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

import datagen.Database;
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
        
        tables = (ArrayList<Table>)Database.getInstance().getTables().clone();
        
        for(Table table : tables){
            
            System.out.println(String.format(queries.get(table.getName()), generate(), "celia", generate()));
        }
    }
    
    final String alphabeth = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numbers = "0123456789";
    final String characters = ",./!@#$%^&*()[]-=<>?:{}_+";
    final String alpha_numbers = alphabeth + numbers;
    final String alpha_chars = alphabeth + characters;
    final String numbers_chars = numbers + characters;
    final String all_chars = alpha_numbers + characters;
    
    private String generate(){
        String base = alphabeth;
        int max = base.length();
        StringBuilder response;
        
        response = new StringBuilder();
        for(int i=0; i<8; i++){
            response.append(base.charAt((int)(Math.random()*max)));
        }
        return response.toString();
    }
        
    public abstract void connect(String database, String user, String password) throws ClassNotFoundException, SQLException;
    public abstract void obtainTables();
    public abstract void insert();
    public abstract void obtainInfomationFromTable();
    public abstract void close();
}
