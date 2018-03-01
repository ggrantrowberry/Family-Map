package com.example.grantrowberry.fmclient.Net;

import android.os.AsyncTask;

import com.example.grantrowberry.fmclient.Models.Model.AuthToken;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginRequest;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginResult;
import com.example.grantrowberry.fmclient.Models.RequestResult.RegisterRequest;
import com.example.grantrowberry.fmclient.Models.RequestResult.RegisterResult;
import com.example.grantrowberry.fmclient.Models.ServerProxy.ServerProxy;

/**
 * Created by GrantRowberry on 3/23/17.
 */

public class RegisterTask extends AsyncTask<RegisterRequest, Void, LoginResult> {
    private Context c;

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    protected LoginResult doInBackground(RegisterRequest... registerRequests) {
        RegisterRequest registerRequest = registerRequests[0];
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.setServerHost(registerRequest.getServerHost());
        serverProxy.setServerPort(registerRequest.getServerPort());
        ModelContainer.getInstance().setServerHost(registerRequest.getServerHost());
        ModelContainer.getInstance().setServerPort(registerRequest.getServerPort());

        LoginResult loginResult = serverProxy.register(registerRequest);
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
                c.goToMap();
            }
        }

    }
}
