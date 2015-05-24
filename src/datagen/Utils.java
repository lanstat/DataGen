/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datagen;

/**
 *
 * @author juan.garson
 */
public class Utils {
    public static boolean arrayContains(String[] array, String data){
        for(String item: array){
            if(item.equals(data)){
                return true;
            }
        }
        return false;
    }
}
