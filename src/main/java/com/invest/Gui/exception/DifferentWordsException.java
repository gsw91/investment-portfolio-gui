package com.invest.Gui.exception;

public class DifferentWordsException extends Exception {

    private final static String DIFFERENT_LOGINS = "Inserted logins are different";
    private final static String DIFFERENT_PASSWORDS = "Inserted passwords are different";
    private final static String DIFFERENT_MAILS = "Inserted mails are different";
    private String text;

    public DifferentWordsException(String text) {
        this.text = text;
    }

    @Override
    public String getMessage() {
        switch (text) {
            case "login":
                return DIFFERENT_LOGINS;
            case "password":
                return DIFFERENT_PASSWORDS;
            case "email":
                return DIFFERENT_MAILS;
        }
        return "";
    }

}
