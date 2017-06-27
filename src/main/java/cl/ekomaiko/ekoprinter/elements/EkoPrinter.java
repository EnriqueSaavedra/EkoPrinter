/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.elements;

import cl.ekomaiko.ekoprinter.enums.DisplayTypes;
import cl.ekomaiko.ekoprinter.exceptions.ConfPrinterException;
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
import com.udojava.evalex.Expression;
import java.io.ByteArrayOutputStream;
import static java.lang.System.exit;
import java.lang.reflect.Field;
import java.util.Iterator;
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
 *    - CHARTS ?
 * @author enrique
 * @param <T> cualquier objeto que implemente DTO
 */
public final class EkoPrinter{
    
    private final List<? extends DTO> currentList;
    private final ConfPrinter currentConf;
    private int maxCellSize = 0;
    
    
    private static Font fuenteDatos = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    private static PdfPCell CELL_NULL = new PdfPCell(new Paragraph("", fuenteDatos));
    
    static{
        //init de datos
        CELL_NULL.setBackgroundColor(new BaseColor(249, 249, 249));
    }
    /**
     * El constructor solo debería verificar que todo está en orden
     * @param lst
     * @param conf
     * @throws DTOException
     * @throws EkoPrinterException 
     * @throws cl.ekomaiko.ekoprinter.exceptions.ConfPrinterException 
     */
    public EkoPrinter(List<? extends DTO> lst, ConfPrinter conf) throws DTOException, EkoPrinterException, ConfPrinterException{
        //verifica que todos los datos que tengan los DTOs sean los aceptados
        this.verifyAllDTO(lst);
        this.verifyAllConfFieldsNames(lst,conf);
        this.searchMaximunCellSize(conf,0);
        this.getTotalCells(conf,this.maxCellSize);
        this.currentList = lst;
        this.currentConf = conf;
        this.validateTotalize(this.currentConf);
        
        Expression expresion = new Expression("1+1+1+1");
        System.out.println(expresion.eval());
        
        /**
         * Pendientes...
         * 1-hacer calculos para funciones
         * 2-generador excel
         * 3-generador html
         * 4-generador charts
         */
    }
    
    private void validateTotalize(ConfPrinter conf) throws ConfPrinterException{
        if(conf.getTotal() != null)
            conf.validateTotalFields();
        if(conf.getSubConf() != null)
            this.validateTotalize(conf.getSubConf());
    }
    
    private void searchMaximunCellSize(ConfPrinter conf,int nivel){
        int titleSize = conf.getTitles().size();
        this.maxCellSize = (this.maxCellSize < (titleSize+nivel)) ? (titleSize+nivel) : this.maxCellSize;
        if(conf.getSubConf() != null){
            this.searchMaximunCellSize(conf.getSubConf(),++nivel);
        }
    }
    
    //se deberia llamar conf
    private void getTotalCells(ConfPrinter conf, int maxCell){
        int titleSize = conf.getTitles().size();
        int merge = maxCell / titleSize;
        conf.totalCells = maxCell;
        int mod = maxCell % titleSize;
        if(mod != 0){
            for (int i = 0; i < titleSize;i++) {
                EkoTitle title = conf.getTitles().get(i);
                title.mergerCells = merge;
            }
            int cont = 0;
            while(mod-- > 0) conf.getTitles().get(titleSize-(++cont)).mergerCells++;
        }else{
            for (EkoTitle title : conf.getTitles()) {
                title.mergerCells = merge;
            }
        }
        if(conf.getSubConf() != null){
            this.getTotalCells(conf.getSubConf(),--maxCell);
        }
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
    
    private void verifyAllConfFieldsNames(List<? extends DTO> listDTO,ConfPrinter conf) throws EkoPrinterException, ConfPrinterException{
        try {
            if(listDTO.size() <= 0 )
                throw new EkoPrinterException("La lista DTO es vacía");
            DTO dto = listDTO.get(0);
            List<? extends EkoTitle> titles = conf.getTitles();
            Class claseDTO =  dto.getClass();
            for (EkoTitle title : titles) {
                if(!title.validate())
                    throw new ConfPrinterException("Un titulo multiple no ha sido bien configurado.");
                if(title instanceof SimpleTitle){
                    claseDTO.getField(((SimpleTitle)title).getFieldName());
                }else if(title instanceof MultiTitle){
                    MultiTitle multiTitle = (MultiTitle) title;
                    for(String field : multiTitle.getFieldName()){
                        claseDTO.getField(field);
                    }
                }
            }
            if(conf.getSubConf() != null)
                verifyAllConfFieldsNames(dto.getDTOList(),conf.getSubConf());
        } catch(NoSuchFieldException e){
            System.out.println(e.getMessage());
            throw new EkoPrinterException("La configuracion de:  no existe"); 
        } catch(ConfPrinterException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            Class clase = listDTO.get(0).getClass();
            Field[] campos = clase.getFields();
            System.out.println("Exception");
            for (Field campo : campos) {
                System.out.println(campo.getName());
            }
            e.printStackTrace();
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
        
        this.rellenarEspacios(table, this.maxCellSize, actualConf.totalCells);
        for (EkoTitle title : actualConf.getTitles()) {
            PdfPCell cell = new PdfPCell((new Paragraph(title.getTitle(),fuenteDatos)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //rellenar con nulos
            cell.setColspan(title.mergerCells);
            table.addCell(cell);
        }
        for (DTO dto : actualList) {
            this.rellenarEspacios(table, this.maxCellSize, actualConf.totalCells);
            
            /**
             * agregar totalizador y subtotalizador
             */  
            Iterator titlePos = actualConf.getTitles().iterator();
            while(titlePos.hasNext()){
                Object nextE = titlePos.next();
                if(nextE instanceof SimpleTitle){
                    SimpleTitle dtoTitle = (SimpleTitle) nextE;
                    Field field = dto.getClass().getField(dtoTitle.getFieldName());
                    Object value = field.get(dto);
                    PdfPCell cellCurrentField =new PdfPCell(new Paragraph(DisplayTypes.applyFormat(String.valueOf(value), dtoTitle.getType()), fuenteDatos));
                    cellCurrentField.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellCurrentField.setColspan(dtoTitle.mergerCells);
                    table.addCell(cellCurrentField);
                }else if(nextE instanceof MultiTitle){
                    MultiTitle dtoTitle = (MultiTitle) nextE;
                    StringBuilder fieldValue = new StringBuilder();
                    for(String dtoField : dtoTitle.getFieldName()){
                        Field field = dto.getClass().getField(dtoField);
                        Object value = field.get(dto);
                        fieldValue.append(DisplayTypes.applyFormat(String.valueOf(value), dtoTitle.getType()));
                        if(!dtoField.equals(dtoTitle.getFieldName()[dtoTitle.getFieldName().length-1])){
                            fieldValue.append(dtoTitle.getGlue());
                        }
                    }
                    PdfPCell cellCurrentField = new PdfPCell(new Paragraph(fieldValue.toString(), fuenteDatos));
                    cellCurrentField.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellCurrentField.setColspan(dtoTitle.mergerCells);
                    table.addCell(cellCurrentField);
                    
                }
            }
            if(dto.getDTOList() != null && actualConf.getSubConf() != null){
                iterateListConf(table, dto.getDTOList(), actualConf.getSubConf());
                if( actualList.indexOf(dto) < (actualList.size()-1) ){
                this.rellenarEspacios(table, this.maxCellSize, actualConf.totalCells);
                    for (EkoTitle title : actualConf.getTitles()) {
                        PdfPCell cell = new PdfPCell((new Paragraph(title.getTitle(),fuenteDatos)));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setColspan(title.mergerCells);
                        table.addCell(cell);
                    }
                }
            }
        }
    }
    
    private void rellenarEspacios(PdfPTable table,int totalCellsPadre, int totalCurrentCells){
        for (; totalCurrentCells < totalCellsPadre; totalCurrentCells++) {
            table.addCell(CELL_NULL);
        }
    }
    
//    private 
    
//    protected abstract 
}
