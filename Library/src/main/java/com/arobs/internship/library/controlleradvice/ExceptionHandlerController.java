package com.arobs.internship.library.controlleradvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleResponseError(Exception exception) {
        logger.error("Server has an internal error: " + exception.toString());
        exception.printStackTrace();
        return new ResponseEntity<>("Server has an internal error: " + exception.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


