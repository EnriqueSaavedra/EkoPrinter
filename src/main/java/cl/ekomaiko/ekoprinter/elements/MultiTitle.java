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
public class MultiTitle extends EkoTitle{
    private String[] fieldName = null;
    private Character glue = null; 

    public MultiTitle(String title, int position) {
        super(title, position);
    }

    public MultiTitle(String title,int position,DisplayTypes type) {
        super(title, position,type);
    }
    
    public MultiTitle setFieldName(String...fieldName){
        if(this.fieldName == null)
            this.fieldName = fieldName;
        
        return this;
    }
    
    public MultiTitle setGlue(Character glue){
        if(this.glue == null)
            this.glue = glue;
        
        return this;
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Title: "+super.getTitle());
        for (String field : fieldName) {
            retorno.append("\n FieldName: "+field);
        }
        retorno.append("\n Position: "+super.getPosition()+"\n");
        return retorno.toString();
    }
    
    @Override
    public boolean validate(){
        if(fieldName == null || fieldName.length <= 1)
            return false;
        if(glue == null || glue.equals(""))
            return false;
        return true;
    }
    
    public String[] getFieldName(){
        return this.fieldName;
    }
    
    public Character getGlue(){
        return this.glue;
    }
}
