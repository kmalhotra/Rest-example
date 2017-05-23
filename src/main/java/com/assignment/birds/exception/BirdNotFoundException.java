package com.assignment.birds.exception;

import java.io.Serializable;

/**
 * Exception class to Handle scenarios where  we are trying to fetch a bird which is missing in the repository.
 * Created by Karan Malhotra on 26/4/17.
 */
public class BirdNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public BirdNotFoundException() {
        super();
    }
    public BirdNotFoundException(String msg)   {
        super(msg);
    }
    public BirdNotFoundException(String msg, Exception e)  {
        super(msg, e);
    }

}
