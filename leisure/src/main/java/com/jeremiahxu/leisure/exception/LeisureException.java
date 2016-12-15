package com.jeremiahxu.leisure.exception;

/**
 * @author Jeremiah Xu
 * 
 */
public class LeisureException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LeisureException() {
        super();
    }

    /**
     * @param message
     */
    public LeisureException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public LeisureException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public LeisureException(String message, Throwable cause) {
        super(message, cause);
    }

}
