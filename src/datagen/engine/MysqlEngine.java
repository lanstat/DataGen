/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

import datagen.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author juan.garson
 */
public class MysqlEngine extends Engine{
    
    private final String driver = "com.mysql.jdbc.Driver";
    private Connection connection;
    
    public MysqlEngine(){
        dataTypes = new HashMap<>();
        queries = new HashMap<>();
        init();
    }    
    
    private void init(){
        dataTypes.put("int", DataType.Integer);
        dataTypes.put("varchar", DataType.String);
        dataTypes.put("datetime", DataType.Timestamp);
        dataTypes.put("date", DataType.Date);
        dataTypes.put("time", DataType.Time);
    }

    @Override
    public void obtainTables() {
        Statement statement;
        ResultSet resultSet;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW TABLES;");
            while(resultSet.next()){
                Database.getInstance().getTables().add(new Table(resultSet.getString(1)));
                System.out.println(resultSet.getString(1));
            }
            resultSet.close();
            statement.close();
        }catch(Exception ex){
            
        }
    }

    @Override
    public void obtainInfomationFromTable() {
        Statement statement;
        ResultSet resultSet;
        Column column;
        StringBuilder fields;
        StringBuilder values;
        try{
            queries.clear();
            
            statement = connection.createStatement();
            for(Table table : Database.getInstance().getTables()){
                resultSet = statement.executeQuery("SHOW COLUMNS FROM "+table.getName()+";");
                fields = new StringBuilder();
                values = new StringBuilder();
                
                fields.append("INSERT INTO ");
                fields.append(table.getName());
                fields.append(" (");
                
                while(resultSet.next()){
                    column = new Column();
                    column.field = resultSet.getString(1);
                    column.typeName = resultSet.getString(2);
                    column.nullable = resultSet.getString(3).equals("YES");
                    column.primaryKey = resultSet.getString(4).equals("PRI");
                    proccessDataType(column.typeName, column);
                    table.addColumn(column);
                    
                    fields.append(column.field);
                    fields.append(",");
                    
                    values.append(generateValueField(column));
                    values.append(",");
                }
                                
                fields.deleteCharAt(fields.length()-1);
                fields.append(") VALUES (");
                values.deleteCharAt(values.length()-1);
                values.append(");");
                
                resultSet.close();
                System.out.println(fields.toString() + values.toString());
                System.out.println(table.toString());
                
                queries.put(table.getName(), fields.toString() + values.toString());
            }
            statement.close();
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
    
    private String generateValueField(Column column){
        String response;
        switch(column.type){
            case Integer:
            case FixedPoint:
            case Floating:
                response = "%s";
                break;
            default:
                response = "'%s'";
        }
        return response;
    }
    
    private void proccessDataType(String typeName, Column column){
        String[] data;
        try{
            data = typeName.split("\\(");
            if(dataTypes.containsKey(data[0])){
                column.type = dataTypes.get(data[0]);
            }else{
                column.type = DataType.None;
            }
            if(data.length>1){
                column.length = Integer.parseInt(data[1].replace(")", "").trim());
            }
        }catch(Exception ex){
            
        }
    }

    @Override
    public void connect(String database, String user, String password) throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        connection = DriverManager.getConnection(database, user, password);
    }    

    @Override
    public void close() {
        try{
            connection.close();
        }catch(Exception e){
            
        }
    }

    @Override
    public void insert() {
        try{
            
        }catch(Exception e){
            
        }
    }
}

