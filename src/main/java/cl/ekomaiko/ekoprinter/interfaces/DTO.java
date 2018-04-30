/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.interfaces;

import cl.ekomaiko.ekoprinter.enums.AllowedTypes;
import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import java.util.List;

/**
 * Esta interfaz se usa para agrupar todos los DTO y ademas para implementar un metodo
 * de verificacion por el cual puedo saber si es seguro recorrer el DTO
 * @author enrique
 */
public interface DTO {
    /**
     * Validar que todas los atributos esten dentro de los campos permitidos
     * usar allowedType
     * @return
     * @throws DTOException 
     */
    boolean verify() throws DTOException;
    
    /**
     * verifica que todos los tipos de datos de los campos agregados al despliege 
     * sean reconocidos por la aplciacion usando el enum AllowedTypes
     * @param elemento
     * @return 
     */
    default boolean allowedType(Object elemento){
        boolean result = false;
        if(elemento == null)
            return true;
        
        result = AllowedTypes.searchAllowed(elemento.getClass().toString());
        return result;
    }
    
    /**
     * se usa para que el objeto que implemente DTO entrege a su subelemento para
     * el motor
     * @return 
     */
    List<? extends DTO> getDTOList();
}
