package com.jeremiahxu.util.sqlbuilder;

/**
 * 
 * @author Jeremiah Xu
 *
 */
public class SQLBuilderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SQLBuilderException() {
		super();
	}

	public SQLBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SQLBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLBuilderException(String message) {
		super(message);
	}

	public SQLBuilderException(Throwable cause) {
		super(cause);
	}

	
}
