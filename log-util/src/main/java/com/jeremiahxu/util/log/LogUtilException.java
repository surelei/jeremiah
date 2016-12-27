package com.jeremiahxu.util.log;

public class LogUtilException extends Exception {

	private static final long serialVersionUID = 1L;

	public LogUtilException() {
	}

	public LogUtilException(String message) {
		super(message);
	}

	public LogUtilException(Throwable cause) {
		super(cause);
	}

	public LogUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogUtilException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
