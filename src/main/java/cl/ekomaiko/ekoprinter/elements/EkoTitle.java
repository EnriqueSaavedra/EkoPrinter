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
public abstract class EkoTitle implements Comparable<EkoTitle>{
    private final String title;
    private final int position;
    private DisplayTypes type;
    public int mergerCells = 1;
    
    public abstract String toString();
    public abstract boolean validate();
    
    EkoTitle(String title, int position){
        this.title = title;
        this.position = position;
        type = DisplayTypes.PLAIN_TEXT;
     }
    
    EkoTitle(String title, int position,DisplayTypes type){
        this(title, position);
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

    public int getPosition() {
        return position;
    }

    public DisplayTypes getType() {
        return type;
    }

    public void setType(DisplayTypes type) {
        this.type = type;
    }     
}
