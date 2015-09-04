package me.giantcrack.gge.exceptions;

/**
 * Created by markvolkov on 8/6/15.
 */
public class EngineException extends Exception{

    private String exception;

    public EngineException(String exception) {
        this.exception = exception;
    }

}
