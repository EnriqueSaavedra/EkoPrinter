/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.enums;

/**
 *
 * @author enrique
 */
public enum DisplayTypes {
    MONEY_CL(1),
    DATE_CL(2),
    DATETIME_CL(3),
    PLAIN_TEXT(4);
    
    
    private int type;
    
    private DisplayTypes(int type){
        this.type = type;
    }
    
    public int getTypes(){
        return this.type;
    }
    
}
