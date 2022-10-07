package com.hashedin.currencyconversionservice.exceptionHandler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class,
            ResourceNotFoundException.class, IllegalArgumentException.class
    })
    @ResponseBody
    public ResponseEntity<ErrorResponseEntity> processUnmergeException(final MethodArgumentNotValidException ex) {

        List<String> list = ex.getBindingResult().getAllErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponseEntity(list), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponseEntity> processRuntimeException(final RuntimeException ex) {

        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getMessage());

        return new ResponseEntity<>(new ErrorResponseEntity(errorList), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
