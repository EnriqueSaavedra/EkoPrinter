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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import static java.lang.System.exit;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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
    
    private final List<? extends DTO> currentList;
    private final ConfPrinter currentConf;
    private Font fuenteDatos = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    
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
        this.getTotalCells(conf);
        
        this.currentList = lst;
        this.currentConf = conf;
        
        
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
    
    private int getTotalCells(ConfPrinter conf){
        int countCells = 0;
        if(conf.getSubConf() != null){
            countCells = this.getTotalCells(conf.getSubConf()) * 2;
            
        }else{
            countCells = conf.getTitles().size();
        }
        conf.totalCells = countCells;
        conf.mergerCells = (countCells/conf.getTitles().size());
        return countCells;
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
    
    private void verifyAllConfFieldsNames(List<? extends DTO> listDTO,ConfPrinter conf) throws EkoPrinterException{
        try {
            if(listDTO.size() <= 0 )
                throw new EkoPrinterException("La lista DTO es vacía");
            
            DTO dto = listDTO.get(0);
            List<EkoTitle> titles = conf.getTitles();
            Class claseDTO =  dto.getClass();
            for (EkoTitle title : titles) {
                claseDTO.getField(title.getFieldName());
            }
            if(conf.getSubConf() != null){
                verifyAllConfFieldsNames(dto.getDTOList(),conf.getSubConf());
            }
        } catch(NoSuchFieldException e){
            System.out.println(e.getMessage());
            throw new EkoPrinterException("La configuracion de:  no existe"); 
        }catch (Exception e) {
            Class clase = listDTO.get(0).getClass();
            Field[] campos = clase.getFields();
            System.out.println("Exe");
            for (Field campo : campos) {
                System.out.println(campo.getName());
            }
        }
    }
    
    /**
     * motor pdf
     */
    public ByteArrayOutputStream toPDF(String docTitle){
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            String solicitud = "Solicitado el día: ";
            solicitud += DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").print(new DateTime());
            PdfWriter.getInstance(document, baos);
            document.open();
            
            /*****TITULO******/
            document.add(new Paragraph());
            document.add(new Chunk(docTitle,FontFactory.getFont(FontFactory.COURIER,13, Font.NORMAL,BaseColor.BLACK)));
            document.add(Chunk.NEWLINE);
            document.add(new Chunk(solicitud,FontFactory.getFont(FontFactory.COURIER,13, Font.NORMAL,BaseColor.BLACK)));
            
            int totalCabeceras = this.currentConf.totalCells;
            PdfPTable tabla = new PdfPTable(totalCabeceras);
            float[] medidasCeldas = new float[totalCabeceras];
            for (int i = 0; i < totalCabeceras; i++) {
                medidasCeldas[i] = 100/totalCabeceras; //indica que el 100% de la tabla se dividirá entre los elementos
            }
            /*****FIN TITULO******/
            //verificar si existe subtabla
            //de ser así verificar si estas son mas que las tablas y en ese caso agrupar las padres en pares
            // colspan de 2 para que así las subtablas usen colspan de 1
            
            tabla.setWidthPercentage(100);
            tabla.setWidths(medidasCeldas);
            
            PdfPCell cellNull = new PdfPCell(new Paragraph("", fuenteDatos));
            cellNull.setBackgroundColor(BaseColor.LIGHT_GRAY);
            this.iterateListConf(tabla, currentList, currentConf); //itera
            document.add(tabla);
            document.close();
            
            return baos;
            
            //generar titulo doc
            
        } catch (IllegalAccessException|IllegalArgumentException|SecurityException|NoSuchFieldException|DocumentException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(EkoPrinter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void mergeCellsByTitleCapacity(){
        
    }
    
    private void iterateListConf(PdfPTable table, List<? extends DTO> actualList, ConfPrinter actualConf) throws NoSuchFieldException, IllegalArgumentException, IllegalArgumentException, IllegalAccessException{
        for (EkoTitle title : actualConf.getTitles()) {
            PdfPCell cell = new PdfPCell((new Paragraph(title.getTitle(),fuenteDatos)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(actualConf.mergerCells);
            table.addCell(cell);
        }
        for (DTO dto : actualList) {
            EkoTitle dtoTitle;        
            Iterator titlePos = actualConf.getTitles().iterator();
            while(titlePos.hasNext()){
                dtoTitle = (EkoTitle) titlePos.next();
                Field field = dto.getClass().getField(dtoTitle.getFieldName());
                Object value = field.get(dto);
                PdfPCell cellCurrentField =new PdfPCell(new Paragraph(String.valueOf(value), fuenteDatos));
                cellCurrentField.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellCurrentField.setColspan(actualConf.mergerCells);
                table.addCell(cellCurrentField);
            }
            if(dto.getDTOList() != null && this.currentConf.getSubConf() != null){
                iterateListConf(table, dto.getDTOList(), this.currentConf.getSubConf());
                if( actualList.indexOf(dto) < (actualList.size()-1) ){
                    for (EkoTitle title : actualConf.getTitles()) {
                        PdfPCell cell = new PdfPCell((new Paragraph(title.getTitle(),fuenteDatos)));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setColspan(actualConf.mergerCells);
                        table.addCell(cell);
                    }
                }
            }
        }
    }
    
//    private 
    
//    protected abstract 
}
