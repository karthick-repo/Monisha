package com.example.ImmunizationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private final String resourceName;
    private final String fieldName;
    private final transient Object fieldValue;

    public DuplicateException( String resourceName, String fieldName, int fieldValue) {
        super(String.format("%s Duplicate account with %s : '%d'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
