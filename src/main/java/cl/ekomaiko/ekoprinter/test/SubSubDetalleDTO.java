/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.test;

import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import java.util.List;

/**
 *
 * @author enrique
 */
public class SubSubDetalleDTO implements DTO{
    
    public String subSubSubNombre;
    public String subSubSubFecha;
    public String subSubSubChofer;
    public String subSubSubRuta;
    public String subSubSubRut;
    public String subSubSubDv;
    public String subSubSubSalida;
    public String subSubSubArticulo;
    public String subSubSubMulta;
    public String subSubSubBeneficio;
    public String subSubSubBalance;

    @Override
    public boolean verify() throws DTOException {
        return (
                allowedType(this.subSubSubNombre) &&
                allowedType(this.subSubSubFecha) &&
                allowedType(this.subSubSubChofer) &&
                allowedType(this.subSubSubRuta) &&
                allowedType(this.subSubSubRut) &&
                allowedType(this.subSubSubDv) &&
                allowedType(this.subSubSubSalida) &&
                allowedType(this.subSubSubArticulo) &&
                allowedType(this.subSubSubMulta) &&
                allowedType(this.subSubSubBeneficio) &&
                allowedType(this.subSubSubBalance)
                );
    }

    @Override
    public List<? extends DTO> getDTOList() {
        return null;
    }
    
}
