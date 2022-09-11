package com.bridgelabz.fundoonotesservice.exception;

import com.bridgelabz.fundoonotesservice.util.Response;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus
public class NotesNotFoundException extends RuntimeException{
    public Response getErrorResponse;
    private int statusCode;
    private String statusMessage;
    public NotesNotFoundException(int statusCode, String statusMessage) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}

