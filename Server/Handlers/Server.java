package Handlers;
import java.io.*;
import java.net.*;
import java.util.Timer;

import com.sun.net.httpserver.*;

import javax.xml.crypto.Data;

import DataAccess.DatabaseException;
import DataAccess.Transaction;

/**
 * Created by GrantRowberry on 3/7/17.
 */

//ifconfig | grep "inet " |grep -Fv 127.0.0.1 |awk '{print $2}' How to get IP address

public class Server {


        private static final int MAX_WAITING_CONNECTIONS = 12;

        private HttpServer server;

        private void run(String portNumber) {
            System.out.println("Initializing HTTP Server");
            try {
                server = HttpServer.create(
                        new InetSocketAddress(Integer.parseInt(portNumber)),
                        MAX_WAITING_CONNECTIONS);
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }

            server.setExecutor(null); // use the default executor

            System.out.println("Creating contexts");

            server.createContext("/user/register", new RegisterHandler());
            server.createContext("/user/login", new LoginHandler());
            server.createContext("/clear", new ClearHandler());
            server.createContext("/", new FileHandler());
            server.createContext("/fill", new FillHandler());
            server.createContext("/load", new LoadHandler());
            server.createContext("/person", new PersonHandler());
            server.createContext("/event", new EventHandler());
            System.out.println("Starting server");
            server.start();

//            try {
//                Transaction transaction = new Transaction();
//                transaction.openConnection();
//                transaction.createTables();
//                transaction.closeConnection(true);
//
//            } catch(DatabaseException de) {
//                de.printStackTrace();
//                System.out.println("Error initializing Database.");
//            }



        }

        public static void main(String[] args) {
            String portNumber = args[0];
            new Server().run(portNumber);
        }
    }

