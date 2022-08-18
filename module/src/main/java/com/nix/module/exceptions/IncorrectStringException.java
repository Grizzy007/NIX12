package com.nix.module.exceptions;

public class IncorrectStringException extends Exception {
    public IncorrectStringException(String str) {
        System.out.println("Read from file string was incorrect! Please correct it and try  again! " +
                "Exception in string:\n"+str);
    }

    public IncorrectStringException() {
        super();
    }
}
