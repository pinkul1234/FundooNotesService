package com.bridgelabz.fundoonotesservice.exception;

import com.bridgelabz.fundoonotesservice.util.Response;

public class LabelNotFoundException extends RuntimeException{
    public Response getErrorResponse;
    private int statusCode;
    private String statusMessage;
    public LabelNotFoundException(int statusCode, String statusMessage) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
