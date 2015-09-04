package me.giantcrack.gge.exceptions;

/**
 * Created by markvolkov on 8/6/15.
 */
public class GameException extends Exception{

    private String exception;

    public GameException(String exception) {
        this.exception = exception;
    }

}
