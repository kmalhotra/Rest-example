package com.assignment.birds.controller;

import com.assignment.birds.exception.BirdNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Base Controller for all exception Handling.
 * Created by Karan Malhotra on 26/4/17.
 */
@RestController
public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseController.class);

    /**
     * This is a handler method to handle scenarios where we are trying to fetch a bird which is missing in the repository.
     *
     * @param exception
     * @return Http status 404
     */
    @ExceptionHandler(BirdNotFoundException.class)
    public ResponseEntity handle(BirdNotFoundException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * This is a handler method to handle scenarios incoming request fail the required validations.
     *
     * @param exception
     * @return Http status 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle(MethodArgumentNotValidException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * This is a handler method to handle scenarios something broke during the execution of REST webservice.
     *
     * @param exception
     * @return Http status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handle(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
