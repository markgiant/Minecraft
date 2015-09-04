package me.giantcrack.gge.exceptions;

/**
 * Created by markvolkov on 8/6/15.
 */
public class ArenaException extends Exception {

    private String exception;

    public ArenaException(String exception) {
        this.exception = exception;
    }


}
