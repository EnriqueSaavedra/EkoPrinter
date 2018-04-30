/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.elements;

import cl.ekomaiko.ekoprinter.enums.DisplayTypes;
import cl.ekomaiko.ekoprinter.exceptions.ConfPrinterException;
import com.sun.org.apache.xalan.internal.utils.ConfigurationError;
import java.util.regex.Pattern;

/**
 *  
 * @author enrique
 */
public class EkoTotal {
    private final String title;
    private final String[] fields;
    private final String formula;
    private DisplayTypes type;
    
    /**
     * La validación queda en este constructor, para que siempre se ejecute
     * @param title
     * @param fields
     * @param formula
     * @throws ConfPrinterException 
     */
    public EkoTotal(String title,String[] fields, String formula) throws ConfPrinterException {
        this.title = title;
        this.formula = formula;
        this.fields = fields;
        this.validateTotal();
    }
    
    public EkoTotal(String title,String[] fields,String formula,DisplayTypes type) throws ConfPrinterException{
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
    
    /**
     * Verificamos que la cantidad de datos entregadas en el arreglo sea correcta 
     * con las que se entregan en la ecuacion ej: fields = {"Camp1","Camp2"} formula = "val1 + val2"
     * @throws ConfPrinterException 
     */
    private void validateTotal() throws ConfPrinterException{
        String fieldName = "val";
        int totalField = 0;
        if((totalField = this.fields.length) == 0)
            throw new ConfPrinterException("no puedes crear un totalizador sin campos");
        if(this.formula.equals("") || this.formula.length() == 0)
            throw new ConfPrinterException("La formula debe tener datos");
        if(!this.formula.matches("(val)[0-9]+"))
            throw new ConfPrinterException("La formula debe usar valores.");
        
        //validar que todos los campos sean instancias de SimpleTitle
        for (int i = 0; i < fields.length; i++) {
            if(!this.formula.contains(fieldName.concat(String.valueOf(i + 1))))
                throw new ConfPrinterException("El campo: "+fields[i]+" no es usado en la formula.");           
        }        
    }
    
    
    
}
