/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.elements;

import cl.ekomaiko.ekoprinter.enums.DisplayTypes;
import cl.ekomaiko.ekoprinter.exceptions.ConfPrinterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * LLeva la representacion de la configuracion, es un espejado de los DTOs
 * El cual presenta todos los campos a printear con su posicion
 * @author enrique
 */
public final class ConfPrinter {
    private final List<? extends EkoTitle> titles;
    int totalCells = 0;
    private final ConfPrinter subConf;
    
    private ConfPrinter(ConfPrinterBuilder builder){
        this.titles = builder.titles;
        this.subConf = builder.subConf;
        Collections.sort(titles);
    }
    
    public String toString(){
        StringBuilder strBuilder = new StringBuilder();
        for(EkoTitle element : titles){
            strBuilder.append(element.toString());
        }
        if(this.subConf != null){
            strBuilder.append("\n\nSub Element ----> \n");
            strBuilder.append(this.subConf.toString());
        }
        return strBuilder.toString();
    }

    public List<? extends EkoTitle> getTitles() {
        return titles;
    }

    public ConfPrinter getSubConf() {
        return subConf;
    }
    
    
    /**
     * El builder no se genera porque tengas muchos campos,
     * es para asegurar que los campos serán llenados una vez y el objeto
     * quede completamente inmutable una vez finalizada la creación, tambien
     * es una forma muy facil de leer para un programador
     */
    public static class ConfPrinterBuilder{
        
        private List<EkoTitle> titles = new ArrayList<EkoTitle>();
        private ConfPrinter subConf;
        
    
        public void ConfPrinterBuilder(){

        }
   
        public ConfPrinterBuilder addTitle(MultiTitle multi) throws ConfPrinterException{
            this.titles.add(multi);
            return this;
        }
        
        public ConfPrinterBuilder addTitle(SimpleTitle simple) throws ConfPrinterException{
            this.titles.add(simple);
            return this;
        }
        
        // necesario?
//        public ConfPrinterBuilder addTitle(String title, int position,DisplayTypes type,char glue,String...fieldName) throws ConfPrinterException{
//            if(fieldName.length == 0)
//                throw new ConfPrinterException("Titulo no entregado.");
//            
//            if(fieldName.length > 1){
//                MultiTitle titleObj = new MultiTitle(title, position,type,glue, fieldName);
//                titles.add(titleObj);
//            }else{
//                SimpleTitle titleObj = new SimpleTitle(title, position,type, fieldName[0]);
//                titles.add(titleObj);
//            }
//            return this;
//        }

        // necesario?
//        public ConfPrinterBuilder addTitle(String title, int position,String...fieldName) throws ConfPrinterException{
//            if(fieldName.length == 0)
//                throw new ConfPrinterException("Titulo no entregado.");
//            
//            if(fieldName.length > 1){
//                MultiTitle titleObj = new MultiTitle(title, position, fieldName);
//                titles.add(titleObj);
//            }else{
//                SimpleTitle titleObj = new SimpleTitle(title, position, fieldName[0]);
//                titles.add(titleObj);
//            }
//            return this;
////            EkoTitle titleObj = new SimpleTitle(title, position, fieldName);
////            titles.add(titleObj);
////            return this;
//        }
        
        public ConfPrinter build(){
            return new ConfPrinter(this);
        }
        
        public ConfPrinter build(ConfPrinter conf){
            this.subConf = conf;
            return new ConfPrinter(this);
        }
    
    }
    
    
    
}
