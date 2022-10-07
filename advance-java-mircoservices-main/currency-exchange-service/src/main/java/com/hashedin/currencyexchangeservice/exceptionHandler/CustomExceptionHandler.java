package com.hashedin.currencyexchangeservice.exceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,
            ResourceNotFoundException.class,
            JsonParseException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorResponseEntity> processUnmergeException(final Exception ex) {

        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getMessage());

        return new ResponseEntity<>(new ErrorResponseEntity(errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponseEntity> processRuntimeException(final RuntimeException ex) {

        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getMessage());

        return new ResponseEntity<>(new ErrorResponseEntity(errorList), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
