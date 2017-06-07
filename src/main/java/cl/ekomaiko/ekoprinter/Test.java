/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter;

import cl.ekomaiko.ekoprinter.elements.ConfPrinter;
import cl.ekomaiko.ekoprinter.elements.EkoPrinter;
import cl.ekomaiko.ekoprinter.elements.MultiTitle;
import cl.ekomaiko.ekoprinter.exceptions.DTOException;
import cl.ekomaiko.ekoprinter.exceptions.EkoPrinterException;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import cl.ekomaiko.ekoprinter.test.DiasTrabajadosPlanillasDTO;
import cl.ekomaiko.ekoprinter.test.PlanillaDetalleDTO;
import cl.ekomaiko.ekoprinter.test.SubDetalleDTO;
import cl.ekomaiko.ekoprinter.test.SubSubDetalleDTO;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author enrique
 */
public class Test {    
    public static List<DiasTrabajadosPlanillasDTO> simularDatos(){
        List<DiasTrabajadosPlanillasDTO> lst = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DiasTrabajadosPlanillasDTO planilla = new DiasTrabajadosPlanillasDTO();
            planilla.cupoMaquina = "cupo "+i;
            planilla.idMaquina = "idMaquina "+i;
            planilla.patenteMaquina = "patente: "+i;
            planilla.chofer = "chofer: "+i;
            planilla.total = "Random: "+i;
            for (int j = 0; j < 5; j++) {
                PlanillaDetalleDTO det = new PlanillaDetalleDTO();
                det.fecha = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                det.empresario = "empresario "+j;
                det.fechaSalida = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                det.noTrabajo = "numero Random "+j;
                det.montoTotal = "monto Random "+j;
                det.planillasPagadasDia = "planillas "+j;
                det.setTrabajo("trabajo "+j);
                for(int k = 0; k < 3 ; k++){
                    SubDetalleDTO subDet = new SubDetalleDTO();
                    subDet.subFecha = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                    subDet.subNombre = "Rando nombre: "+k;
                    for(int l = 0; l < 5 ; l++){
                        SubSubDetalleDTO subSubDet = new SubSubDetalleDTO();
                        subSubDet.subSubSubNombre = "nombre "+l;
                        subSubDet.subSubSubArticulo = "articulo "+l;
                        subSubDet.subSubSubBalance = "balance "+l;
                        subSubDet.subSubSubBeneficio = "beneficio "+l;
                        subSubDet.subSubSubChofer = "chofer "+l;
                        subSubDet.subSubSubDv = "digito "+l;
                        subSubDet.subSubSubFecha =  DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                        subSubDet.subSubSubRut = "rut "+l;
                        subSubDet.subSubSubRuta = "ruta "+l;
                        subDet.addSubSubDetalle(subSubDet);
                    }
                    det.addSubDetalle(subDet);
                }
                planilla.addDetalle(det);
            }
            lst.add(planilla);
        }
        return lst;
    }
    
    
    
    
    public static void main(String...args){
        try {
            List<DiasTrabajadosPlanillasDTO> lst = simularDatos();
            ConfPrinter conf = new ConfPrinter.ConfPrinterBuilder()
                    .addTitle("Máquina",["idMaquina",""], 0)
                    .addTitle("Cupo Máquina", "cupoMaquina", 1)
                    .addTitle("Patente Máquina", "patenteMaquina", 2)
                    .addTitle("Conductor", "chofer", 3)
                    .addTitle("Total", "total", 4)
                    .build(
                        new ConfPrinter.ConfPrinterBuilder()
                            .addTitle("Empresario", "empresario", 0)
                            .addTitle("Fecha Salida", "fechaSalida", 1)
                            .addTitle("No Trabajado", "noTrabajo", 2)
                            .addTitle("Trabajo", "trabajo", 3)
                            .addTitle("Fecha", "fecha", 4)
                            .addTitle("Planillas/Dia", "planillasPagadasDia", 5)
                            .addTitle("SubTotal", "montoTotal", 6)
                            .build(
                                new ConfPrinter.ConfPrinterBuilder()
                                    .addTitle("Fecha", "subFecha", 0)
                                    .addTitle("Nombre", "subNombre", 1)
                                    .build(
                                        new ConfPrinter.ConfPrinterBuilder()
                                            .addTitle("Nombre", "subSubSubNombre", 0)
                                            .addTitle("Articulo", "subSubSubArticulo", 0)
                                            .addTitle("Balance", "subSubSubBalance", 0)
                                            .addTitle("Beneficio", "subSubSubBeneficio", 0)
                                            .addTitle("Rut", "subSubSubRut", 0)
                                            .addTitle("Dv", "subSubSubDv", 0)
                                            .addTitle("Chofer", "subSubSubChofer", 0)
                                            .addTitle("Fecha", "subSubSubFecha", 0)
                                            .addTitle("Multa", "subSubSubMulta", 0)
                                            .build()
                                    )
                            )
                    );
            
            EkoPrinter printer = new EkoPrinter(lst, conf);
            ByteArrayOutputStream baos = printer.toPDF("Este es un doc de prueba");
            baos.writeTo(new FileOutputStream("/home/enrique/testPdf.pdf"));
            List<? extends Number> lista = new ArrayList<Integer>();
            
//        getListData();
        } catch (DTOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (EkoPrinterException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    public static void getListData(List<? extends DTO> lst) throws Exception{
        if(lst.size() <= 0)
            throw new Exception("Lista vacia");
        
        Object obj = lst.get(0);
        Class clase = lst.get(0).getClass();
        Field[] campos = clase.getFields();
        System.out.println("campos de "+clase.getName()+": ");
        for (Field campo : campos) {
            String fieldName = String.valueOf(campo.getName());
            String fieldType = String.valueOf(campo.getType());
            int fieldModifier = campo.getModifiers();
            System.out.println("");
            
            //Evita constantes
            if(fieldModifier == Modifier.PUBLIC){
                System.out.println("campo "+fieldType+" "+fieldName);
                //si tiene un list
                if(fieldType.equals("interface java.util.List")){
                    String name = campo.getName();
                    Object value = campo.get(obj);
                    List<? extends DTO> subLst = (List) value;
                    
                    getListData(subLst);
                }
            }
        }
        
        
    }
    
}
