/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen;

import datagen.engine.Engine;
import datagen.engine.MysqlEngine;

/**
 *
 * @author juan.garson
 */
public class DataGen {
   static final String DB_URL = "jdbc:mysql://192.168.70.32/test";

   static final String USER = "root";
   static final String PASS = "123Jg456";
   
    public static void main(String[] args) {
        Engine engine;
        try{
            engine = new MysqlEngine();
           
            System.out.println("Conectando a la base de datos...");
            engine.connect(DB_URL, USER, PASS);

            System.out.println("Obteniendo listado de tablas...");
            engine.obtainTables();
           
            System.out.println("Procesando informacion de la tablas...");
            engine.obtainInfomationFromTable();
           
            System.out.println("Poblando...");
            engine.populate();
           
            engine.close();
        }catch(Exception e){
            //e.printStackTrace();
        }
        System.out.println("Termine!");
    }
    
}
