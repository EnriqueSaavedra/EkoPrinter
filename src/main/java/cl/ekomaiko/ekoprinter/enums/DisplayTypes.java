/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.enums;

import java.text.DecimalFormat;

/**
 *  Falta agregar los dormatos de fecha y cualquier otro que recuerde
 * @author enrique
 */
public enum DisplayTypes {
    MONEY_CL(1),
    DATE_CL(2),
    DATETIME_CL(3),
    PLAIN_TEXT(4),
    THOUSAND(5);
    
    
    private int type;
    
    private DisplayTypes(int type){
        this.type = type;
    }
    
    public int getTypes(){
        return this.type;
    }
    
    private static String thousandFormat(String value){
        DecimalFormat format = new DecimalFormat("#,###");
        if(value.equalsIgnoreCase("k")){ //pillería para el rut
            return value;
        }
        if(value != null && !"".equals(value)){
            Long longValue = 0L;
            try{
                longValue = Long.valueOf(value);
                value = format.format(longValue).toLowerCase().replace("ch", "");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            value = "";
        }
        return value;
    }
    
    private static String moneyFormatCL(String value){
        DecimalFormat format = new DecimalFormat("¤ #,###");
        if(value != null && !"".equals(value)){
            Long longValue = 0L;
            try{
                longValue = Long.valueOf(value);
                value = format.format(longValue).toLowerCase().replace("ch", "");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            value = "";
        }
        return value;
    }
    
    
    public static String applyFormat(String value,DisplayTypes type){
        String formattedValue = "";
        switch(type){
            case MONEY_CL:
                formattedValue =  moneyFormatCL(value);
                break;
            case THOUSAND:
                formattedValue =  thousandFormat(value);
                break;
            case DATE_CL:
                formattedValue = value;
                break;
            case DATETIME_CL:
                formattedValue = value;
                break;
            default:
                return value;
        }
        return formattedValue;
    }
    
}
