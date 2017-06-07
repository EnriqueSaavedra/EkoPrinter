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
    private final String[] fieldName;

    public MultiTitle(String title, int position,String...fieldName) {
        super(title, position);
        this.fieldName = fieldName;
    }

    public MultiTitle(String title,int position,DisplayTypes type,String...fieldName) {
        super(title, position,type);
        this.fieldName = fieldName;
    }
    
    public String[] getFieldName(){
        return this.fieldName;
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
}
