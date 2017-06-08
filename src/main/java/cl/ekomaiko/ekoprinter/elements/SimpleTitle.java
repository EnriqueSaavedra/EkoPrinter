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
public class SimpleTitle extends EkoTitle{
    private String fieldName = null;

    public SimpleTitle(String title,String fieldName, int position) {
        super(title, position);
        this.fieldName = fieldName;
    }

    public SimpleTitle(String title,String fieldName, int position,DisplayTypes type) {
        super(title, position,type);
        this.fieldName = fieldName;
    }
    
    public String getFieldName(){
        return this.fieldName;
    }

    @Override
    public String toString() {
        return "Title: "+super.getTitle() +
               "\n FieldName: "+fieldName+
               "\n Position: "+super.getPosition()+"\n";
    }

    @Override
    public boolean validate() {
        if(this.fieldName == null)
            return false;
        if(this.fieldName.trim().equals(""))
            return false;
        return true;
    }
    
}
