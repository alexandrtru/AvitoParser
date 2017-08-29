package com.siteMonitor.Exceptions;

/**
 * Created by user on 13.05.2017.
 */
public class FilterCreatingException extends Exception {
    public FilterCreatingException(String message) {
        super("Filter creating error: " + message);
    }
}
