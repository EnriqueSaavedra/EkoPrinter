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
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
        for (int i = 0; i < 2; i++) {
            DiasTrabajadosPlanillasDTO planilla = new DiasTrabajadosPlanillasDTO();
            planilla.setCupoMaquina("cupo "+i);
            planilla.setIdMaquina("idMaquina "+i);
            planilla.setPatenteMaquina("patente: "+i);
            for (int j = 0; j < 5; j++) {
                PlanillaDetalleDTO det = new PlanillaDetalleDTO();
                det.setFecha(DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime()));
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
                                            .addTitle("SubTitu1", "planillasPagadasDia", 0)
                                            .addTitle("SubTitu2", "trabajo", 1)
                                            .addTitle("SubTitu3", "fecha", 2)
                                            .build()
                                );
        
        EkoPrinter printer = new EkoPrinter(lst, conf);
        ByteArrayOutputStream baos = printer.toPDF("Este es un doc de prueba");
        baos.writeTo(new FileOutputStream("/home/enrique/testPdf.pdf"));
        List<? extends Number> lista = new ArrayList<Integer>();
        
//        getListData();
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
