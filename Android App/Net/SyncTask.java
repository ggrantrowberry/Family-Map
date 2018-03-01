package com.example.grantrowberry.fmclient.Net;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;

import com.example.grantrowberry.fmclient.Models.Model.AuthToken;
import com.example.grantrowberry.fmclient.Models.Model.Person;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.Models.RequestResult.EventsResult;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginResult;
import com.example.grantrowberry.fmclient.Models.RequestResult.PersonsResult;
import com.example.grantrowberry.fmclient.Models.RequestResult.RegisterRequest;
import com.example.grantrowberry.fmclient.Models.ServerProxy.ServerProxy;

/**
 * Created by GrantRowberry on 3/23/17.
 */

public class SyncTask extends AsyncTask<Void, Void, Void> {
    private Context c;

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    //The first of strings[] should be the serverHost then serverPort
    protected Void doInBackground(Void... params) {

        ServerProxy serverProxy = new ServerProxy();
        serverProxy.setServerHost(ModelContainer.getInstance().getServerHost());
        serverProxy.setServerPort(ModelContainer.getInstance().getServerPort());

        AuthToken tempAuthToken = ModelContainer.getInstance().getAuthToken();

        EventsResult eventsResult = serverProxy.getEvents(tempAuthToken);
        ModelContainer.getInstance().setEvents(eventsResult.getEvents());
        PersonsResult personsResult = serverProxy.getPersons(tempAuthToken);
        ModelContainer.getInstance().setPersons(personsResult.getData());
        Log.d("UserID", ModelContainer.getInstance().getUserId());
        ModelContainer.getInstance().setUserPerson(serverProxy.getPerson(ModelContainer.getInstance().getUserId(),tempAuthToken).makePersonObject());
        Person user = ModelContainer.getInstance().getUserPerson();
        ModelContainer.getInstance().initializeData();

        return null;
    }
    protected void onPostExecute(Void result) {
        if (ModelContainer.getInstance().getEvents() == null) {
            c.onError("Error retrieving events");
        } else if (ModelContainer.getInstance().getPersons() == null){
            c.onError("Error retrieving persons");
        } else if (ModelContainer.getInstance().getUserPerson() == null){
            c.onError("Error retrieving user");
        } else {
                c.onSuccess("Welcome " + ModelContainer.getInstance().getUserPerson().getFirstName() + " " + ModelContainer.getInstance().getUserPerson().getLastName());
                ModelContainer.getInstance().setLoggedIn(true);
        }
        c.goToMap();
    }

}

