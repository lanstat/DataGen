/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen.engine;

import datagen.Database;
import datagen.Utils;
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
        dataTypes.put("double", DataType.Floating);
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
                    switch(resultSet.getString(4)){
                        case "PRI":
                            column.primaryKey = true;
                            break;
                        case "MUL":
                            column.foreignKey = true;
                            break;
                    }
                        
                    proccessDataType(column.typeName, column);
                    proccessOptions(column, resultSet.getString(6));
                    table.addColumn(column);
                    
                    //if(!column.autoIncrement){
                        fields.append(column.field);
                        fields.append(",");

                        values.append(generateValueField(column));
                        values.append(",");
                    //}
                    
                }
                                
                fields.deleteCharAt(fields.length()-1);
                fields.append(") VALUES (");
                values.deleteCharAt(values.length()-1);
                values.append(");");
                
                resultSet.close();
                
                queries.put(table.getName(), fields.toString() + values.toString());
            }
            statement.close();
            
            proccessForeignReferences();
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
    
    private void proccessForeignReferences(){
        Statement statement;
        ResultSet resultSet;
        Database database;
        Table base;
        Table reference;
        String baseName;
        String referenceName;
        Column column;
        try{
            statement = connection.createStatement();
            database = Database.getInstance();
            referenceName = "";
            baseName = "";
            base = null;
            reference = null;
            
            resultSet = statement.executeQuery("select REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME, TABLE_NAME, COLUMN_NAME " +
                                               "from INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                                               "where REFERENCED_TABLE_NAME is not null and " +
                                               "TABLE_SCHEMA = '"+database.getName()+"' " +
                                               "order by Table_name;");
            
            while(resultSet.next()){
                if(!baseName.equals(resultSet.getString(3))){
                    base = database.getTable(resultSet.getString(3));
                    baseName = base.getName();
                }
                if(!referenceName.equals(resultSet.getString(1))){
                    reference = database.getTable(resultSet.getString(1));
                    referenceName = reference.getName();
                }
                column = base.getColumn(resultSet.getString(4));
                column.referenceColumn = reference.getColumn(resultSet.getString(2));
                column.foreignKey = true;
                column.columnName = reference.getName();
                if(!base.getReferences().contains(referenceName)){
                    base.getReferences().add(referenceName);
                }
            }
            
            resultSet.close();
            statement.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    private void proccessOptions(Column column, String options){
        String[] split;
        try{
            split = options.split(",");
            if(Utils.arrayContains(split, "auto_increment"))
                column.autoIncrement = true;
        }catch(Exception e){
            System.err.println(e.getMessage());
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
    public void connect(String database, String host, String port, String user, String password) throws ClassNotFoundException, SQLException{
        super.connect(database, host, port, user, password);
        Class.forName(driver);
        connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
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

    @Override
    public void createQueries() {
        StringBuilder fields;
        StringBuilder values;
        try{
            queries.clear();
            
            for(Table table : Database.getInstance().getTables()){
                fields = new StringBuilder();
                values = new StringBuilder();
                
                fields.append("INSERT INTO ");
                fields.append(table.getName());
                fields.append(" (");
                
                for(Column column : table.getColumns()){
                    fields.append(column.field);
                    fields.append(",");
                    values.append(generateValueField(column));
                    values.append(",");
                }
                                
                fields.deleteCharAt(fields.length()-1);
                fields.append(") VALUES (");
                values.deleteCharAt(values.length()-1);
                values.append(");");
                
                queries.put(table.getName(), fields.toString() + values.toString());
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
}

