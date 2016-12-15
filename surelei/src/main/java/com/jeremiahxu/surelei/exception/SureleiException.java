package com.jeremiahxu.surelei.exception;

/**
 * @author Jeremiah Xu
 * 
 */
public class SureleiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SureleiException() {
        super();
    }

    /**
     * @param message
     */
    public SureleiException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SureleiException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SureleiException(String message, Throwable cause) {
        super(message, cause);
    }

}
