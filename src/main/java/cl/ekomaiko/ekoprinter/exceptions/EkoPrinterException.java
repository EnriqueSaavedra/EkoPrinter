/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.ekomaiko.ekoprinter.exceptions;

/**
 *
 * @author enrique
 */
public class EkoPrinterException extends Exception{

    public EkoPrinterException() {
    }

    public EkoPrinterException(String message) {
        super(message);
    }

    public EkoPrinterException(String message, Throwable cause) {
        super(message, cause);
    }

    public EkoPrinterException(Throwable cause) {
        super(cause);
    }
    
    
    
}
