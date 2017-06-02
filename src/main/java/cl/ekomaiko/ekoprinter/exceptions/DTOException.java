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
public class DTOException extends Exception{

    public DTOException() {
    }

    public DTOException(String message) {
        super(message);
    }

    public DTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DTOException(Throwable cause) {
        super(cause);
    }
    
    
    
    
}
