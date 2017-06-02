/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.test;

import cl.ekomaiko.ekoprinter.elements.EkoPrinter;
import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import java.util.List;

/**
 *  
 * @author enrique
 */
public class PlanillaDetalleDTO implements DTO{

    public static final String TRABAJADO = "1";
    public static final String NO_TRABAJADO = "0";

    public String fecha;
    public String trabajo;
    public String planillasPagadasDia;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public String getTextTrabajo(){
        if(this.trabajo.equals(TRABAJADO))
            return "Trabajado";
        else if(this.trabajo.equals(NO_TRABAJADO))
            return "No trabajado";
        else
            return "-";
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getPlanillasPagadasDia() {
        return (planillasPagadasDia.equals("0") ? "-" : planillasPagadasDia);
    }

    public void setPlanillasPagadasDia(String planillasPagadasDia) {
        this.planillasPagadasDia = planillasPagadasDia;
    }

    @Override
    public boolean verify() throws DTOException {
        return (
                allowedType(this.fecha) &&
                allowedType(this.trabajo) &&
                allowedType(this.planillasPagadasDia)
            );
    }

    @Override
    public List<? extends DTO> getDTOList() {
        return null;
    }
    
}
