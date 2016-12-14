package com.jeremiahxu.office.excel;

/**
 * @author Jeremiah Xu
 * 
 */
public class ExcelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExcelException() {
        super();
    }

    /**
     * @param message
     */
    public ExcelException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ExcelException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

}
