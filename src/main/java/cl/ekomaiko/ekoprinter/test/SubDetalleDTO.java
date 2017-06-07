/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.test;

import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author enrique
 */
public class SubDetalleDTO implements DTO{
    public String subFecha;
    public String subNombre;
    public List<SubSubDetalleDTO> subSubDetalle = new ArrayList<SubSubDetalleDTO>();


    public void addSubSubDetalle(SubSubDetalleDTO subSubDetalle){
        this.subSubDetalle.add(subSubDetalle);
    }
    
    @Override
    public boolean verify() throws DTOException {
        return (
                allowedType(this.subFecha) &&
                allowedType(this.subNombre)
            );
    }

    @Override
    public List<? extends DTO> getDTOList() {
        return subSubDetalle;
    }
    
}
