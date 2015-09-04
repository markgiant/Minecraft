package me.giantcrack.gge.exceptions;

/**
 * Created by markvolkov on 8/6/15.
 */
public class KitException extends Exception{

    private String exception;

    public KitException(String exception) {
        this.exception = exception;
    }

}
