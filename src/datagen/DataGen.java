/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen;

import datagen.engine.DataType;
import datagen.engine.Engine;
import datagen.engine.MysqlEngine;
import generator.Seeder;
import java.util.HashMap;

/**
 *
 * @author juan.garson
 */
public class DataGen {
   static final String DB_URL = "jdbc:mysql://127.0.0.1/test";

   static final String USER = "root";
   static final String PASS = "";
   /*
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
            
            System.out.println("Definiendo opciones personalizadas...");
           
            System.out.println("Poblando...");
            engine.populate();
           
            engine.close();
            *Seeder seed = new Seeder();
            HashMap<String, Object> map = new HashMap<>();
            
            map.put("negative", false);
            map.put("max", 10);
            
            for(String item : seed.generate("asd", 10, DataType.Char, map)){
                System.out.println(item);
            }*
            System.out.println("Termine!");
        }catch(Exception e){
            //e.printStackTrace();
        }
    }*/
    
}
