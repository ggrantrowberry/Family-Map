package com.example.grantrowberry.fmclient.Models.RequestResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class FillResult {
    /**
     * Holds a message about the success of a fill.
     * Message can either be from an error or from a successful filling indicating that x persons and y events were added.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
