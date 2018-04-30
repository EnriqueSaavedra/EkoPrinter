/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.test;

import cl.ekomaiko.ekoprinter.elements.EkoPrinter;
import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;

/**
 *  
 * @author enrique
 */
public class PlanillaDetalleDTO implements DTO{

    private final String TRABAJADO = "1";
    private final String NO_TRABAJADO = "0";

    public String fecha;
    public String trabajo;
    public String noTrabajo;
    public String montoTotal;
    public String tiempoTotal;
    public String empresario;
    public String fechaSalida;
    public String planillasPagadasDia;
    public List<SubDetalleDTO> subDetalle = new ArrayList<SubDetalleDTO>();

    public String getTextTrabajo(){
        if(this.trabajo.equals(TRABAJADO))
            return "Trabajado";
        else if(this.trabajo.equals(NO_TRABAJADO))
            return "No trabajado";
        else
            return "-";
    }

    public void addSubDetalle(SubDetalleDTO subDetalle){
        this.subDetalle.add(subDetalle);
    }
    
    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }
    
    @Override
    public boolean verify() throws DTOException {
        return (
                allowedType(this.fecha) &&
                allowedType(this.trabajo) &&
                allowedType(this.noTrabajo) &&
                allowedType(this.montoTotal) &&
                allowedType(this.tiempoTotal) &&
                allowedType(this.empresario) &&
                allowedType(this.fechaSalida) &&
                allowedType(this.planillasPagadasDia)
            );
    }

    @Override
    public List<? extends DTO> getDTOList() {
        return subDetalle;
    }
    
}
