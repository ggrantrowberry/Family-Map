package Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.*;

import java.io.IOException;

/**
 * Created by GrantRowberry on 3/7/17.
 */

public class FileHandler implements HttpHandler {


        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String fileId = exchange.getRequestURI().getPath();
            File file = getFile(fileId);

            if (file == null) {

                String response = "Error 404 File not found.";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
                output.close();
            } else if(fileId.equals("/")) {
                file = getFile("index.html");
                exchange.sendResponseHeaders(200, 0);
                OutputStream output = exchange.getResponseBody();
                FileInputStream fs = new FileInputStream(file);
                final byte[] buffer = new byte[0x10000];
                int count = 0;
                while ((count = fs.read(buffer)) >= 0) {
                    output.write(buffer, 0, count);
                }
                output.flush();
                output.close();
                fs.close();

            } else{
                    exchange.sendResponseHeaders(200, 0);
                    OutputStream output = exchange.getResponseBody();
                    FileInputStream fs = new FileInputStream(file);
                    final byte[] buffer = new byte[0x10000];
                    int count = 0;
                    while ((count = fs.read(buffer)) >= 0) {
                        output.write(buffer, 0, count);
                    }
                    output.flush();
                    output.close();
                    fs.close();
                }
            }

        private File getFile(String fileId) {
            String rootPath = "HTML/";
             File f = new File(rootPath+fileId);
            return f;
        }
    }


