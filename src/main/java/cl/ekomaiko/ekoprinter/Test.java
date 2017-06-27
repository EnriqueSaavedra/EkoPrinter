/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter;

import cl.ekomaiko.ekoprinter.elements.ConfPrinter;
import cl.ekomaiko.ekoprinter.elements.EkoPrinter;
import cl.ekomaiko.ekoprinter.elements.MultiTitle;
import cl.ekomaiko.ekoprinter.elements.SimpleTitle;
import cl.ekomaiko.ekoprinter.enums.DisplayTypes;
import cl.ekomaiko.ekoprinter.exceptions.ConfPrinterException;
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
            planilla.total =  String.valueOf(Math.round(Math.random()*100_000));
            for (int j = 0; j < 5; j++) {
                PlanillaDetalleDTO det = new PlanillaDetalleDTO();
                det.fecha = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                det.empresario = "empresario "+j;
                det.fechaSalida = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                det.noTrabajo = "numero Random "+j;
                det.montoTotal = String.valueOf(Math.round(Math.random()*10_000));
                det.planillasPagadasDia = "planillas "+j;
                det.setTrabajo("trabajo "+j);
                for(int k = 0; k < 3 ; k++){
                    SubDetalleDTO subDet = new SubDetalleDTO();
                    subDet.subFecha = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                    subDet.subNombre = "Rando nombre: "+k;
                    for(int l = 0; l < 5 ; l++){
                        int dv = (int) Math.round(Math.random()*10);
                        SubSubDetalleDTO subSubDet = new SubSubDetalleDTO();
                        subSubDet.subSubSubNombre = "nombre "+l;
                        subSubDet.subSubSubArticulo = "articulo "+l;
                        subSubDet.subSubSubBalance =  String.valueOf(Math.round(Math.random()*1_000));
                        subSubDet.subSubSubBeneficio =  String.valueOf(Math.round(Math.random()*100));
                        subSubDet.subSubSubChofer = "chofer "+l;
                        subSubDet.subSubSubDv = String.valueOf((dv >= 10 ) ? "k" : dv);
                        subSubDet.subSubSubFecha =  DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime());
                        subSubDet.subSubSubRut = String.valueOf(Math.round(Math.random()*100_000_000));
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
                    .addTitle(new MultiTitle("Máquina", 0).setFieldName("cupoMaquina","patenteMaquina").setGlue('/'))
                    .addTitle(new SimpleTitle("Cupo Máquina", "cupoMaquina", 1))
                    .addTitle(new SimpleTitle("Patente Máquina", "patenteMaquina", 2))
                    .addTitle(new SimpleTitle("Conductor", "chofer", 3))
                    .addTitle(new SimpleTitle("Total", "total", 4, DisplayTypes.MONEY_CL))
                    .generateTotal("Total final", new String[]{"total"}, "", DisplayTypes.MONEY_CL)
                    .build(
                        new ConfPrinter.ConfPrinterBuilder()
                            .addTitle(new SimpleTitle("Empresario", "empresario", 0))
                            .addTitle(new SimpleTitle("Fecha Salida", "fechaSalida", 1))
                            .addTitle(new SimpleTitle("No Trabajado", "noTrabajo", 2))
                            .addTitle(new SimpleTitle("Trabajo", "trabajo", 3))
                            .addTitle(new SimpleTitle("Fecha", "fecha", 4))
                            .addTitle(new SimpleTitle("Planillas/Dia", "planillasPagadasDia", 5))
                            .addTitle(new SimpleTitle("SubTotal", "montoTotal", 6, DisplayTypes.MONEY_CL))
                            .build(
                                new ConfPrinter.ConfPrinterBuilder()
                                    .addTitle(new SimpleTitle("Fecha", "subFecha", 0))
                                    .addTitle(new SimpleTitle("Nombre", "subNombre", 1))
                                    .build(
                                        new ConfPrinter.ConfPrinterBuilder()
                                            .addTitle(new SimpleTitle("Nombre", "subSubSubNombre", 0))
                                            .addTitle(new SimpleTitle("Articulo", "subSubSubArticulo", 0))
                                            .addTitle(new SimpleTitle("Balance", "subSubSubBalance", 0, DisplayTypes.MONEY_CL))
                                            .addTitle(new SimpleTitle("Beneficio", "subSubSubBeneficio", 0, DisplayTypes.MONEY_CL))
                                            .addTitle(new MultiTitle("Rut", 0,DisplayTypes.THOUSAND).setFieldName("subSubSubRut","subSubSubDv").setGlue('-'))
//                                            .addTitle(new SimpleTitle("Dv", 0))
                                            .addTitle(new SimpleTitle("Chofer", "subSubSubChofer", 0))
                                            .addTitle(new SimpleTitle("Fecha", "subSubSubFecha", 0))
                                            .addTitle(new SimpleTitle("Multa", "subSubSubMulta", 0))
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
        } catch (ConfPrinterException ex) {
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
