package Handlers;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import DataAccess.DatabaseException;
import RequestResult.JsonConverter;
import RequestResult.RegisterRequest;
import Services.RegisterService;

/**
 * Created by GrantRowberry on 3/6/17.
 */

public class RegisterHandler implements HttpHandler {

//Todo: Make sure the error where not all fields are filled in is handled

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JsonConverter jsonConverter = new JsonConverter();
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);


                RegisterRequest req = jsonConverter.getRegisterRequest(reqData);
                System.out.println(reqData);


                try {


                    RegisterService registerService = new RegisterService();
                    String loginResult = jsonConverter.getLoginResultJson(registerService.register(req));
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(loginResult, respBody);
                    respBody.close();
                    success = true;

                } catch (DatabaseException de) {

                    String error = "{ \"message\": \"" + de.getMessage() + "\"}";
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(error, respBody);
                    respBody.close();

                }



            }


            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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

