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
public class ConfPrinterException extends Exception{

    public ConfPrinterException() {
    }

    public ConfPrinterException(String message) {
        super(message);
    }

    public ConfPrinterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfPrinterException(Throwable cause) {
        super(cause);
    }
    
    
}
