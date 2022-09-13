package com.bridgelabz.fundoonotesservice.exception;

import com.bridgelabz.fundoonotesservice.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LabelExceptionHandler {
        @ExceptionHandler(LabelNotFoundException.class)
        public ResponseEntity<Response> handleHiringException(LabelNotFoundException he) {
            Response response = new Response();
            response.setErrorCode(400L);
            response.setMessage(he.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

