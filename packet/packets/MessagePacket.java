/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 22:06
 * File: LoginPacket.java
 */

package de.nikolas.rpg.networking.packet.packets;

import de.nikolas.rpg.networking.packet.Packet;

import java.io.Serializable;

public class MessagePacket extends Packet<String> implements Serializable {

    public MessagePacket(String sender, String message) {
        super(sender);
        set(message);
    }

    @Override
    public void handle() {
        System.out.println("[Packet] Message receaved from " + getSender() + ": " + value());
    }
}
