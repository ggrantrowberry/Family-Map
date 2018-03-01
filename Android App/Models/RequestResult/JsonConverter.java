package com.example.grantrowberry.fmclient.Models.RequestResult;
import com.google.gson.*;
import com.example.grantrowberry.fmclient.Models.Model.*;




/**
 * Created by GrantRowberry on 3/7/17.
 */

public class JsonConverter {

    public RegisterRequest getRegisterRequest(String r){
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(r,RegisterRequest.class);
         return request;
    }

    public String getLoginResultJson(LoginResult lr){
        Gson gson = new Gson();
        String lrJson = gson.toJson(lr);
        return lrJson;

    }

    public LoginRequest getLoginRequest(String r){
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(r, LoginRequest.class);
        return request;
    }

    public LoadRequest getLoadRequest(String r){
        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(r, LoadRequest.class);
        return request;
    }

    public String getLoadResultJson(LoadResult lr){
        Gson gson = new Gson();
        String lrJson = gson.toJson(lr);
        return lrJson;
    }

    public String getEventResultJson(Event er){
        Gson gson = new Gson();
        String erJson = gson.toJson(er);
        return erJson;
    }

    public String getEventsResultJson(EventsResult er){
        //Event[] events = el.toArray(new Event[el.size()]);
        Gson gson = new Gson();
        String elJson = gson.toJson(er);
        return elJson;
    }
    public String getPersonResultJson(Person pr){
        Gson gson = new Gson();
        String prJson = gson.toJson(pr);
        return prJson;
    }

    public String getPersonsResultJson(PersonsResult pr){
        //Person[] persons = pl.toArray(new Person[pl.size()]);
        Gson gson = new Gson();
        String plJson = gson.toJson(pr);
        return plJson;
    }

    public String getRegisterRequestJson(RegisterRequest r){
        Gson gson = new Gson();
        String rrJson = gson.toJson(r);
        return rrJson;
    }

    public LoginResult getLoginResult(String json){
        Gson gson = new Gson();
        LoginResult lr = gson.fromJson(json, LoginResult.class);
        return lr;
    }

    public String getLoginRequestJson(LoginRequest r){
        Gson gson = new Gson();
        String lrJson = gson.toJson(r);
        return lrJson;
    }
    public ClearResult getClearResult(String json){
        Gson gson = new Gson();
        ClearResult cr = gson.fromJson(json, ClearResult.class);
        return cr;
    }
    public FillResult getFillResult(String json){
        Gson gson = new Gson();
        FillResult fr = gson.fromJson(json, FillResult.class);
        return fr;
    }
    public String getLoadRequestJson(LoadRequest r){
        Gson gson = new Gson();
        String lrJson = gson.toJson(r);
        return lrJson;
    }
    public LoadResult getLoadResult(String json){
        Gson gson = new Gson();
        LoadResult lr = gson.fromJson(json, LoadResult.class);
        return lr;
    }
    public PersonResult getPersonResult(String json){
        Gson gson = new Gson();
        PersonResult pr = gson.fromJson(json, PersonResult.class);
        return pr;
    }
    public PersonsResult getPersonsResult(String json){
        Gson gson = new Gson();
        PersonsResult pr = gson.fromJson(json, PersonsResult.class);
        return pr;
    }
    public EventResult getEventResult(String json){
        Gson gson = new Gson();
        EventResult er = gson.fromJson(json, EventResult.class);
        return er;
    }
    public EventsResult getEventsResult(String json){
        Gson gson = new Gson();
        EventsResult er = gson.fromJson(json, EventsResult.class);
        return er;
    }
    public String getFillResultJson(FillResult r){
        Gson gson = new Gson();
        String frJson = gson.toJson(r);
        return frJson;
    }


}
