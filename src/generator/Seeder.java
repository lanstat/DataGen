/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generator;

import datagen.engine.Column;
import datagen.engine.DataType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author juan.garson
 */
public class Seeder {
    private final String chr = "%c";
    private final String word = "%w";
    private final String digit = "%d";
    private final String integer = "%i";
    private final String alphabeth = "abcdefghijklmnopqrstuvwxyz";
    private final String alphabeth_upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String alphabeth_both = alphabeth + alphabeth_upper;
    private final String numbers = "0123456789";
    private final String characters = ",./!@#$%^&*()[]-=<>?:{}_+";
    private final String alpha_numbers = alphabeth_both + numbers;
    private final String alpha_chars = alphabeth_both + characters;
    private final String numbers_chars = numbers + characters;
    private final String all_chars = alpha_numbers + characters;
    
    
    public String[] generate(String mask, int count, DataType type){
        return generate(mask, count, type, null);
    }
    
    public String[] generate(String mask, int count, DataType type, HashMap<String, Object> options){
        ArrayList<String> response;
        String[] resp;
        try{
            resp = null;
            switch(type){
                case Char:
                    response = generateChar(mask, count, options);
                    break;
                case String:
                    response = generateChar(mask, count, options);
                    break;
                case Date:
                    response = generateDate(mask, count, options);
                    break;
                case Timestamp:
                    response = generateDateTime(mask, count, options);
                    break;
                case Integer:
                    response = generateInt(mask, count, options);
                    break;
                case Floating:
                    response = generateDouble(mask, count, options);
                    break;
                default:
                    response = null;
            }
            if(response!=null)
                resp = response.toArray(new String[count]);
        }catch(Exception e){
            System.err.println(e.getCause());
            resp = null;
        }
        return resp;
    }
    
    /**
     * 
     * @param mask Mascara a usar
     * @param count Cantidad de elementos a generar
     * @param options Opciones de generacion
     * @return Listado de cadenas generadas
     */
    private ArrayList<String> generateInt(String mask, int count, HashMap<String, Object> options){
        ArrayList<String> response;
        byte mode;
        int max;
        Random rand;
        int seed;
        int pivot;
        boolean autoincrement;
        try{
            response = new ArrayList<>();
            rand = new Random();
            
            mode = 0;
            max = Integer.MAX_VALUE;
            autoincrement = false;
            pivot = 0;
            
            if(options != null){
                mode = options.containsKey("mode")? (byte)options.get("mode") : mode;
                max = options.containsKey("max")? (int)options.get("max") : max;
                autoincrement = options.containsKey("autoincrement")? (boolean)options.get("autoincrement") : false;
                pivot = options.containsKey("pivot")? (int)options.get("pivot") : 0;
            }
            
            for(int i=0; i<count; i++){
                if(!autoincrement){
                    seed = rand.nextInt(max);
                    switch(mode){
                        case 0:
                            seed = Math.random()>0.5f? seed*-1: seed;
                        break;
                    }
                }else{
                    pivot++;
                    seed = pivot;
                }
                response.add(String.valueOf(seed));
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    /**
     * 
     * @param mask Mascara a usar
     * @param count Cantidad de elementos a generar
     * @param options Opciones de generacion
     * @return Listado de cadenas generadas
     */
    private ArrayList<String> generateDouble(String mask, int count, HashMap<String, Object> options){
        ArrayList<String> response;
        byte mode;
        int max;
        Random rand;
        double seed;
        try{
            response = new ArrayList<>();
            rand = new Random();
            
            mode = 0;
            max = Integer.MAX_VALUE;
            
            if(options != null){
                mode = options.containsKey("mode")? (byte)options.get("mode") : mode;
                max = options.containsKey("max")? (int)options.get("max") : max;
            }
            
            for(int i=0; i<count; i++){
                seed = rand.nextDouble() * max;
                switch(mode){
                    case 0:
                        seed = Math.random()>0.5f? seed*-1: seed;
                    break;
                }
                response.add(String.valueOf(seed));
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    private ArrayList<String> generateChar(String mask, int count, HashMap<String, Object> options){
        ArrayList<String> response;
        byte mode;
        int max;
        Random rand;
        char seed;
        try{
            response = new ArrayList<>();
            rand = new Random();
            
            mode = 0;
            max = Integer.MAX_VALUE;
            
            if(options != null){
            }
            
            for(int i=0; i<count; i++){
                seed = alphabeth_both.charAt(rand.nextInt(alphabeth_both.length()));
                response.add(String.valueOf(seed));
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    private ArrayList<String> generateDate(String mask, int count, HashMap<String, Object> options){
        ArrayList<String> response;
        byte mode;
        Random rand;
        Calendar date;
        SimpleDateFormat format;
        try{
            response = new ArrayList<>();
            rand = new Random();
            date = Calendar.getInstance();
            format = new SimpleDateFormat("yyyy-MM-dd");
            
            mode = options.containsKey(Column.MODE)? (byte)options.get(Column.MODE) : 0;
            
            for(int i=0; i<count; i++){
                switch(mode){
                    case 0:
                        date.add(Calendar.DAY_OF_MONTH, rand.nextInt(10));
                        break;
                }
                response.add(format.format(date.getTime()));
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    private ArrayList<String> generateDateTime(String mask, int count, HashMap<String, Object> options){
        ArrayList<String> response;
        byte mode;
        Random rand;
        SimpleDateFormat format;
        Calendar date;
        try{
            response = new ArrayList<>();
            rand = new Random();
            date = Calendar.getInstance();
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            mode = options.containsKey(Column.MODE)? (byte)options.get(Column.MODE) : 0;
            
            for(int i=0; i<count; i++){
                switch(mode){
                    case 0:
                        date.add(Calendar.MINUTE, rand.nextInt(1440));
                        break;
                }
                response.add(format.format(date.getTime()));
            }
        }catch(Exception e){
            response = null;
        }
        return response;
    }
    
    public String generateByMask(String mask){
        return "%c%c-%d";
    }
}
