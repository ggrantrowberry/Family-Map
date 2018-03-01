package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import DataAccess.DatabaseException;
import RequestResult.JsonConverter;
import RequestResult.LoginRequest;
import Services.LoginService;

/**
 * Created by GrantRowberry on 3/7/17.
 */

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JsonConverter jsonConverter = new JsonConverter();
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);


                LoginRequest req = jsonConverter.getLoginRequest(reqData);
                System.out.println(reqData);


                try {


                    LoginService loginService = new LoginService();
                    String loginResult = jsonConverter.getLoginResultJson(loginService.login(req));
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(loginResult, respBody);

                    respBody.flush();
                    respBody.close();

                    success = true;

                } catch (DatabaseException de) {

                    String error = "{ \"message\": \"" + de.getMessage() + "\"}";
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(error, respBody);
                    respBody.flush();
                    respBody.close();

                }



            }

        }
        catch(IOException e) {
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

}


