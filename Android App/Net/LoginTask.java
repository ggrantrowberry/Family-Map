package com.example.grantrowberry.fmclient.Net;

import android.os.AsyncTask;

import com.example.grantrowberry.fmclient.Models.Model.AuthToken;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginRequest;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginResult;
import com.example.grantrowberry.fmclient.Models.ServerProxy.ServerProxy;
import com.example.grantrowberry.fmclient.UI.MainActivity;

import java.net.URL;

/**
 * Created by GrantRowberry on 3/23/17.
 */

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResult> {
    private Context c;

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    protected LoginResult doInBackground(LoginRequest ... loginRequests) {
        LoginRequest loginRequest = loginRequests[0];
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.setServerHost(loginRequest.getServerHost());
        serverProxy.setServerPort(loginRequest.getServerPort());
        ModelContainer.getInstance().setServerHost(loginRequest.getServerHost());
        ModelContainer.getInstance().setServerPort(loginRequest.getServerPort());

        LoginResult loginResult = serverProxy.login(loginRequest);
        return loginResult;
    }


    protected void onPostExecute(LoginResult result) {
        if(result == null){
            c.onError("Could not connect to server");
        } else {
            if(result.getUsername() == null){
                c.onSuccess(result.getMessage());
            } else {
                ModelContainer.getInstance().setUserId(result.getPersonID());
                AuthToken authToken = new AuthToken();
                authToken.setAuthToken(result.getAuthToken());
                ModelContainer.getInstance().setAuthToken(authToken);
                ModelContainer.getInstance().setLoggedIn(true);
                SyncTask syncTask = new SyncTask();
                syncTask.setC(c);
                syncTask.execute();

            }
        }

    }

}
