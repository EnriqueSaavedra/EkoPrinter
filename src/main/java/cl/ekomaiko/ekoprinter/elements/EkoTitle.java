/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.elements;

import cl.ekomaiko.ekoprinter.enums.DisplayTypes;

/**
 *
 * @author enrique
 */
public class EkoTitle implements Comparable<EkoTitle>{
    private final String title;
    private final String fieldName;
    private final int position;
    private DisplayTypes type;
    
    
     public EkoTitle(String title,String fieldName, int position){
        this.title = title;
        this.fieldName = fieldName;
        this.position = position;
        type = DisplayTypes.PLAIN_TEXT;
     }
    
    public EkoTitle(String title,String fieldName, int position,DisplayTypes type){
        this(title, fieldName, position);
        this.type = type;
    }

    @Override
    public int compareTo(EkoTitle o) { 
        if(this.position > o.getPosition()){
            return 1;
        }else if(this.position < o.getPosition()){
            return -1;
        }else{
            return 0;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getPosition() {
        return position;
    }

    public DisplayTypes getType() {
        return type;
    }

    public void setType(DisplayTypes type) {
        this.type = type;
    }    

    @Override
    public String toString() {
        return "Title: "+title +
               "\n FieldName: "+fieldName+
               "\n Position: "+position+"\n";
    }
    
    
}
