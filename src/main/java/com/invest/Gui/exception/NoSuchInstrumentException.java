package com.invest.Gui.exception;

public class NoSuchInstrumentException extends Exception {

    public NoSuchInstrumentException() {
    }

    @Override
    public String getMessage() {
        return "There is no such instrument!";
    }

}
