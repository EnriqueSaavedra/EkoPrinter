/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author enrique
 */
public enum AllowedTypes {
    STRING(String.class.toString()),
    INTEGER(Integer.class.toString()),
    LONG(Long.class.toString()),
    SHORT(Short.class.toString()),
    DOUBLE(Double.class.toString()),
    CHAR(Character.class.toString()),
    BOOLEAN(Boolean.class.toString()),
    LIST(List.class.toString()),
    ARRAYLIST(ArrayList.class.toString());
    
    private String type;
    
    private AllowedTypes(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }
    
    public static boolean searchAllowed(String type){
        return (
                type.equals(STRING.getType())   ||
                type.equals(LONG.getType())     || 
                type.equals(INTEGER.getType())  || 
                type.equals(SHORT.getType())    || 
                type.equals(DOUBLE.getType())   || 
                type.equals(CHAR.getType())     ||
                type.equals(BOOLEAN.getType())  ||
                type.equals(LIST.getType())     ||
                type.equals(ARRAYLIST.getType())
            );
    }
    
    
}
