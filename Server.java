/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 20:33
 * File: Server.java
 */

package de.nikolas.rpg.networking;

import de.nikolas.rpg.networking.packet.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {

    private ArrayList<ClientHandler> clientHandlers;
    private SimpleDateFormat dateFormat;
    private int port;
    private boolean keepGoing;

    public int uniqueId;

    public Server(int port) {
        this.port = port;
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.clientHandlers = new ArrayList<>();
    }

    public void start() {
        keepGoing = true;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (keepGoing) {
                info("Waiting for new Clients on port " + port + ".");

                Socket socket = serverSocket.accept();
                if(!keepGoing) break;

                ClientHandler handler = new ClientHandler(this, socket);
                clientHandlers.add(handler);
                handler.start();

            }
            System.out.println("closing server");

                serverSocket.close();
                clientHandlers.forEach(handler -> {
                    try {
                        handler.getInputStream().close();
                        handler.getOutputStream().close();
                        handler.getSocket().close();
                    }catch (Exception e) {
                        info("Error while closing server and clients steams!");
                    }
                });
        } catch (IOException e) {
            System.out.println(getDate() + "Error creating serversocket! " + e);
        }
    }

    public void info(String message) {
        System.out.println("[" + getDate() + "] " + message);
    }

    public void stop() {
        keepGoing = false;
        System.exit(0);
    }

    public static void main(String[] args) {
        Server server = new Server(25585);
        server.start();
    }

    private String getDate() {
        return dateFormat.format(new Date());
    }
}
