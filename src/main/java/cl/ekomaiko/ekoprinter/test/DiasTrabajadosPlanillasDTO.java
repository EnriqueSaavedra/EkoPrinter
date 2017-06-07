/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.test;

import cl.ekomaiko.ekoprinter.elements.EkoPrinter;
import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author enrique
 */
public class DiasTrabajadosPlanillasDTO implements DTO{
    public String idMaquina;
    public String cupoMaquina;
    public String patenteMaquina;
    public String chofer;
    public String total;
    public List<PlanillaDetalleDTO> detalle = new ArrayList<PlanillaDetalleDTO>();


    public void addDetalle(PlanillaDetalleDTO detalle){
        this.detalle.add(detalle);
    }

    @Override
    public boolean verify() throws DTOException {
        return (
                allowedType(this.idMaquina) &&
                allowedType(this.cupoMaquina) &&
                allowedType(this.patenteMaquina) &&
                allowedType(this.detalle) 
            );
    }

    @Override
    public List<? extends DTO> getDTOList() {
        return this.detalle;
    }
    
}
