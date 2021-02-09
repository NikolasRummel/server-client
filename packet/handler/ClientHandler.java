/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 21:07
 * File: ServerPacketInHandler.java
 */

package de.nikolas.rpg.networking.packet.handler;

import de.nikolas.rpg.networking.Server;
import de.nikolas.rpg.networking.packet.Packet;
import de.nikolas.rpg.networking.packet.packets.LoginPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Server server;
    private int id;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;

    private String clientName;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.id = server.uniqueId++;

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            loginUser();

        } catch (IOException | ClassNotFoundException e) {
            server.info("Error creating input & output steams or doing new login! " + e);
        }
    }

    @Override
    public void run() {
        boolean keepGoing = true;

        while (keepGoing) {
            try {

                Packet packet = (Packet) inputStream.readObject();
                packet.handle();

            } catch (IOException | ClassNotFoundException e) {
                server.info("Error while reading " + clientName + "'s packets! " + e);
                break;
            }
        }
    }

    public void loginUser() throws IOException, ClassNotFoundException {
        LoginPacket login = (LoginPacket) inputStream.readObject();
        clientName = login.value();
        server.info(clientName + " has joined the server.");
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getUniqueId() {
        return id;
    }
}
