package Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import DataAccess.AuthTokenDao;
import DataAccess.DatabaseException;
import DataAccess.Transaction;
import Model.AuthToken;
import RequestResult.JsonConverter;
import Services.PersonService;

/**
 * Created by GrantRowberry on 3/7/17.
 */

public class PersonHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JsonConverter jsonConverter = new JsonConverter();
        boolean success = false;
        String[] pathStrings = exchange.getRequestURI().getPath().split("/");

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {

                    AuthToken a = new AuthToken();

                    String authToken = reqHeaders.getFirst("Authorization");
                    a.setAuthToken(authToken);



                    try{
                        String username = authTokenExists(authToken);
                        System.out.println(username);

                        if (username != null) {
                         //   AuthTokenTimeManager timeManager = new AuthTokenTimeManager();
                          //  timeManager.run(a);

                            PersonService personService = new PersonService();
                            String personResult;

                            if(pathStrings.length > 2){

                                personResult = jsonConverter.getPersonResultJson(personService.getPerson(pathStrings[2],username));

                            } else {

                                personResult = jsonConverter.getPersonsResultJson(personService.getPersons(a));

                            }


                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(personResult, respBody);
                            respBody.close();
                            success = true;
                        } else {
                            throw new DatabaseException("Invalid AuthToken");
                        }
                    } catch (DatabaseException de) {

                        String error = "{ \"message\": \"" + de.getMessage() + "\"}";
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(error, respBody);
                        respBody.close();




                    }


                    if (!success) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

        }

    }


    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


    private String authTokenExists(String authToken) throws DatabaseException {
        Transaction t = new Transaction();
        AuthTokenDao ad;
        try {
            t.openConnection();
            ad = t.getAd();
            AuthToken a = ad.existsAuthToken(authToken);
            if (a != null) {
                t.closeConnection(true);
                return a.getUsername();
            }
            t.closeConnection(true);

        } catch (DatabaseException e) {
            e.printStackTrace();
            t.closeConnection(false);
            throw new DatabaseException("Bad Authorization Token");
        }
        return null;

    }

}

