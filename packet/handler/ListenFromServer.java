/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 20:50
 * File: ClientPacketInHandler.java
 */

package de.nikolas.rpg.networking.packet.handler;

import de.nikolas.rpg.networking.Client;
import de.nikolas.rpg.networking.packet.Packet;

import java.io.IOException;

public class ListenFromServer extends Thread {

    private Client client;

    public ListenFromServer(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object inputObject = client.getInputStream().readObject();
                if(inputObject instanceof Packet) {

                    Packet packet = (Packet) inputObject;
                    packet.handle();

                }else System.out.println("Got an object, which wasn't a Packet. Ignoring it..");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Server has closed the connection! ");
                break;
            }
        }
    }
}
