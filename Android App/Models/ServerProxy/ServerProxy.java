package com.example.grantrowberry.fmclient.Models.ServerProxy;

import android.util.Log;

import com.example.grantrowberry.fmclient.Models.RequestResult.*;
import com.example.grantrowberry.fmclient.Models.Model.*;


import java.io.*;
import java.net.*;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class ServerProxy {
    String serverHost;
    String serverPort;

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

    public static void main(String[] args) {

        String serverHost = args[0];
        String serverPort = args[1];


    }



    /**
     * Creates a new user
     * @param r RegisterRequest filled with needed information.
     * @return LoginResult
     */

    public LoginResult register(RegisterRequest r) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            JsonConverter jsonConverter = new JsonConverter();

            http.connect();

            String reqData = jsonConverter.getRegisterRequestJson(r);


            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("User succesfully registered.");

                InputStream httpResponse = http.getInputStream();
                String response = readString(httpResponse);
                LoginResult result = jsonConverter.getLoginResult(response);
                return result;

            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Logs in the user.
     * @param r Login Request
     * @return LoginResult
     */

    public LoginResult login(LoginRequest r){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            JsonConverter jsonConverter = new JsonConverter();

            http.connect();

            String reqData = jsonConverter.getLoginRequestJson(r);


            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("User successfully logged in.");

                InputStream httpResponse = http.getInputStream();
                String response = readString(httpResponse);
                LoginResult result = jsonConverter.getLoginResult(response);
                return result;

            }
            else {
                LoginResult result = new LoginResult();
                result.setMessage("ERROR:" + http.getResponseMessage());
                System.out.println("ERROR: " + http.getResponseMessage());
                return result;
            }
        }
        catch (IOException e) {
            Log.e("ServerProxy", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;    }

    /**
     * This clears the database
     * @return ClearResult
     */

    public ClearResult clear(){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/clear");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            JsonConverter jsonConverter = new JsonConverter();

            http.connect();



//            OutputStream reqBody = http.getOutputStream();
//            writeString(reqData, reqBody);
//            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Clear succeeded.");

                InputStream httpResponse = http.getInputStream();
                String response = readString(httpResponse);
                ClearResult result = jsonConverter.getClearResult(response);
                return result;

            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Populates the server's database.
     * @param r FillRequest
     *
     * @return FillResult
     */

    public FillResult fill(FillRequest r){
        try {
            URL url;
            if(r.getGenerations() > 0) {
                url = new URL("http://" + serverHost + ":" + serverPort + "/fill/" + r.getUsername() + "/");
            } else {
                url = new URL("http://" + serverHost + ":" + serverPort + "/fill/" + r.getUsername() + "/" + r.getGenerations());

            }
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            JsonConverter jsonConverter = new JsonConverter();

            http.connect();



//            OutputStream reqBody = http.getOutputStream();
//            writeString(reqData, reqBody);
//            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Fill successful.");

                InputStream httpResponse = http.getInputStream();
                String response = readString(httpResponse);
                System.out.println(response);
                FillResult result = jsonConverter.getFillResult(response);
                return result;

            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads people, users and events into the database.
     * @param r
     * @return LoadResult
     */
    public LoadResult load(LoadRequest r){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/load");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            JsonConverter jsonConverter = new JsonConverter();

            http.connect();

            String reqData = jsonConverter.getLoadRequestJson(r);


            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Load successful.");

                InputStream httpResponse = http.getInputStream();
                String response = readString(httpResponse);
                LoadResult result = jsonConverter.getLoadResult(response);
                return result;

            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  Returns the single Person object with the specified ID.
     * @param personID
     * @return PersonResult
     */
    public PersonResult getPerson(String personID, AuthToken authToken) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/" + personID);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);    // There is no request body

            http.addRequestProperty("Authorization", authToken.getAuthToken());

            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();

                String respData = readString(respBody);
                JsonConverter converter = new JsonConverter();
                System.out.println(respData);

                return converter.getPersonResult(respData);

            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Returns ALL family members of the current user. The current user is determined from the provided auth token.
     * @return PersonsResult
     */
    public PersonsResult getPersons(AuthToken token){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);    // There is no request body

            http.addRequestProperty("Authorization", token.getAuthToken());

            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();

                String respData = readString(respBody);
                JsonConverter converter = new JsonConverter();
                System.out.println(respData);

                return converter.getPersonsResult(respData);

            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the single Event object with the specified ID.
     * @param eventID
     * @return EventResult
     */
    public EventResult getEvent(String eventID, AuthToken authToken){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event/" + eventID);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);    // There is no request body

            http.addRequestProperty("Authorization", authToken.getAuthToken());

            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();

                String respData = readString(respBody);
                JsonConverter converter = new JsonConverter();
                System.out.println(respData);

                return converter.getEventResult(respData);

            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
     * @return EventsResult
     */
    public EventsResult getEvents(AuthToken token){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);    // There is no request body

            http.addRequestProperty("Authorization", token.getAuthToken());

            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();

                String respData = readString(respBody);
                JsonConverter converter = new JsonConverter();
                System.out.println(respData);

                return converter.getEventsResult(respData);

            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
