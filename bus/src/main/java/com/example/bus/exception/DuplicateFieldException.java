package com.example.bus.exception;

public class DuplicateFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;
	
	public DuplicateFieldException(String message) {
        super(message);
    }
	
	public DuplicateFieldException(String message,String field) {
        super(message);
        this.message = message;
        this.fieldName=field;
    }
	
	public String getFieldName()
	{
		return fieldName;
	}
	
	public String getMessage()
	{
		return message;
	}
	
}
