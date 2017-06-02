/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.elements;

import cl.ekomaiko.ekoprinter.enums.DisplayTypes;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LLeva la representación final de la tabla, este es el "motor" que debería
 * pintar las tablas en los 3 formatos que se usan en ekomaiko:
 *    - HTML
 *    - PDF
 *    - EXCEL
 * @author enrique
 * @param <T> cualquier objeto que implemente DTO
 */
public final class EkoPrinter<T extends DTO>{
    
    
    
    
    public EkoPrinter(List<T> lst, ConfPrinter conf){
        /**
         * 1- Recorrer DTO
         * 2- Armar un objeto solo con los campos del DTO
         * 3- Verificar que todos los fieldName de conf existan en DTO
         *   3.1- Cuando encuentras un list<? extends DTO> debes bajar un nivel de conf tambien
         * 4- Crear exceptions personalizadas para indicar los siguientes errores:
         *   4.1- Cuando un campo conf no está en lst
         *   4.2- Cuando un DTO no se puede recorrer con reflection (dado que sus campos son privados)
         *   4.3- Cuando un campo lst no está en conf, simplemente debería dar un aviso minimo (Info)
         *   4.4- pendientes.... 
         */
         
    }
    
    
//    private 
    
//    protected abstract 
}
