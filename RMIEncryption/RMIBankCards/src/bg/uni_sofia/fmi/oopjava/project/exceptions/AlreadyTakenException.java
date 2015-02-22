/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.uni_sofia.fmi.oopjava.project.exceptions;

/**
 *
 * @author Dimitar Panayotov
 */
public class AlreadyTakenException extends Exception{

    public AlreadyTakenException() {
        this("Username is already taken!");
    }

    public AlreadyTakenException(String message) {
        super(message);
    }

    public AlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyTakenException(Throwable cause) {
        super(cause);
    }

    public AlreadyTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
