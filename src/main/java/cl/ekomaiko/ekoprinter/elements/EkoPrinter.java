/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.elements;

import cl.ekomaiko.ekoprinter.enums.DisplayTypes;
import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.exceptions.EkoPrinterException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import static java.lang.System.exit;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
public final class EkoPrinter{
    
    
    
    /**
     * El constructor solo debería verificar que todo está en orden
     * @param lst
     * @param conf
     * @throws DTOException
     * @throws EkoPrinterException 
     */
    public EkoPrinter(List<? extends DTO> lst, ConfPrinter conf) throws DTOException, EkoPrinterException{
        //verifica que todos los datos que tengan los DTOs sean los aceptados
        this.verifyAllDTO(lst);
        this.verifyAllConfFieldsNames(lst,conf);
        
        
        
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
    
    private void verifyAllDTO(List<? extends DTO> listDTO) throws DTOException, EkoPrinterException{
            try{
                for (DTO dto : listDTO) {
                    if(!dto.verify())
                        throw new EkoPrinterException("Uno de los dtos ha tenido un error al verificar");
                    
                    if(dto.getDTOList() != null){
                        List<? extends DTO> subListDTO = dto.getDTOList();
                        verifyAllDTO(subListDTO);
                    }
                } 
            }catch(DTOException|EkoPrinterException e){
                // lo mismo que el dto, lo importante es que sean diferentes para identificar de que tipo es el error
                //quizas lanzar exception para afuera sería bueno
                System.out.println(e.getMessage());
                throw e;
            }catch(Exception e){
                e.printStackTrace();
                //mandar todo a la mierda, este es error de programacion
                System.out.println("Error: "+e.getMessage());
                exit(0);
            }
    }
    
    private void verifyAllConfFieldsNames(List<? extends DTO> listDTO,ConfPrinter conf){
        try {
            if(listDTO.size() <= 0 )
                throw new EkoPrinterException("La lista DTO es vacía");
            
            DTO dto = listDTO.get(0);
            List<EkoTitle> titles = conf.getTitles();
            for (EkoTitle title : titles) {
                if(!listDTO.contains(title.getFieldName()))
                    throw new EkoPrinterException("La configuracion de: "+title.getFieldName()+" no existe");
                
            }
            if(conf.getSubConf() != null){
                verifyAllConfFieldsNames(dto.getDTOList(),conf.getSubConf());
            }
        } catch (Exception e) {
            Class clase = listDTO.get(0).getClass();
            Field[] campos = clase.getFields();
            System.out.println("Exe");
            for (Field campo : campos) {
                System.out.println(campo.getName());
            }
        }
    }
    
    private void getDTOElements(List<? extends DTO> list,ConfPrinter conf) throws EkoPrinterException{
        
    }
    
    
//    private 
    
//    protected abstract 
}
