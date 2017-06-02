/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter;

import cl.ekomaiko.ekoprinter.elements.ConfPrinter;
import cl.ekomaiko.ekoprinter.elements.EkoPrinter;
import cl.ekomaiko.ekoprinter.interfaces.DTO;
import cl.ekomaiko.ekoprinter.test.DiasTrabajadosPlanillasDTO;
import cl.ekomaiko.ekoprinter.test.PlanillaDetalleDTO;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author enrique
 */
public class Test {
    
    
    public static List<DiasTrabajadosPlanillasDTO> simularDatos(){
        List<DiasTrabajadosPlanillasDTO> lst = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DiasTrabajadosPlanillasDTO planilla = new DiasTrabajadosPlanillasDTO();
            planilla.setCupoMaquina("cupo "+i);
            planilla.setIdMaquina("idMaquina "+i);
            planilla.setPatenteMaquina("detalle: "+i);
            for (int j = 0; j < 2; j++) {
                PlanillaDetalleDTO det = new PlanillaDetalleDTO();
                det.setFecha(LocalTime.now().toString());
                det.setPlanillasPagadasDia("planillas "+j);
                det.setTrabajo("trabajo "+j);
                planilla.addDetalle(det);
            }
            lst.add(planilla);
        }
        return lst;
    }
    
    
    
    
    public static void main(String...args) throws Exception{
        List<DiasTrabajadosPlanillasDTO> lst = simularDatos();
        ConfPrinter conf = new ConfPrinter.ConfPrinterBuilder()
                                .addTitle("Titulo1", "idMaquina", 0)
                                .addTitle("Titulo2", "cupoMaquina", 1)
                                .addTitle("Titulo3", "patenteMaquina", 2)
                                .build(
                                        new ConfPrinter.ConfPrinterBuilder()
                                            .addTitle("SubTitu1", "fecha", 0)
                                            .addTitle("SubTitu2", "trabajo", 1)
                                            .addTitle("SubTitu3", "planillasPagadasDia", 2)
                                            .build()
                                );
        
        EkoPrinter printer = new EkoPrinter(lst, conf);
        
        
    }
    
    
    public static void getListData(List<? extends DTO> lst) throws Exception{
        if(lst.size() <= 0)
            throw new Exception("Lista vacia");
        
        Object obj = lst.get(0);
        Class<?> clase = lst.get(0).getClass();
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
                    System.out.println("List!!!");
                    String name = campo.getName();
                    Object value = campo.get(obj);
                    List<? extends DTO> subLst = (List) value;
                    
                    getListData(subLst);
                }
            }
        }
        
        
    }
    
}
