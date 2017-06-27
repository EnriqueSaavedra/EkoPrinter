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
public class EkoTotal {
    private final String title;
    private final String[] fields;
    private final String formula;
    private DisplayTypes type;

    public EkoTotal(String title,String[] fields, String formula) {
        this.title = title;
        this.formula = formula;
        this.fields = fields;
    }
    
    public EkoTotal(String title,String[] fields,String formula,DisplayTypes type){
        this(title,fields,formula);
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getFormula() {
        return formula;
    }

    public DisplayTypes getType() {
        return type;
    }

    public String[] getFields() {
        return fields;
    }
    
    
    
    
    
}
