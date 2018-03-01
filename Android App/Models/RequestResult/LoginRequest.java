package com.example.grantrowberry.fmclient.Models.RequestResult;

import java.net.URL;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class LoginRequest {
    /**
     * Holds all the necessary information to log a user in.
     */
    private String userName;
    private String password;
    transient String serverHost;
    transient String serverPort;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
