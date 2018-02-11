package com.xiaogch.common.http.exception;

public class HttpRequestException extends Exception {

	private static final long serialVersionUID = -8276447232195112507L;
	
	public HttpRequestException() {
		super();
	}
	
	public HttpRequestException(String message) {
		super(message);
	}
	
	public HttpRequestException(String message, Throwable cause) {
		super(message , cause);
	}

}
